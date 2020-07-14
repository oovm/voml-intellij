/** `wae create` — materialize app template with a single deploy target host dependency. */

import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import type { WaeCreateOptions } from '@wae/commander';
import { writeWranglerToml } from './build-wrangler.js';
import {
    hostDependencies,
    hostEntriesToExclude,
    parseDeployTarget,
    patchWaeConfigSource,
    type DeployTarget,
} from './deploy-target.js';

const SKIP_DIRS = new Set(['node_modules', 'dist', '.git']);

function resolveTemplateRoot(): string {
    return path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../../templates/app');
}

function shouldSkipRelative(relativePath: string, excludeHostEntries: Set<string>): boolean {
    const normalized = relativePath.split(path.sep).join('/');
    if (excludeHostEntries.has(normalized)) return true;
    return false;
}

function copyTemplateTree(
    templateRoot: string,
    currentDir: string,
    targetDir: string,
    excludeHostEntries: Set<string>,
): void {
    for (const entry of fs.readdirSync(currentDir, { withFileTypes: true })) {
        if (SKIP_DIRS.has(entry.name)) continue;
        const from = path.join(currentDir, entry.name);
        const to = path.join(targetDir, entry.name);
        const relative = path.relative(templateRoot, from).split(path.sep).join('/');
        if (entry.isDirectory()) {
            fs.mkdirSync(to, { recursive: true });
            copyTemplateTree(templateRoot, from, to, excludeHostEntries);
            continue;
        }
        if (shouldSkipRelative(relative, excludeHostEntries)) continue;
        fs.copyFileSync(from, to);
    }
}

function writePackageJson(targetDir: string, projectName: string, target: DeployTarget): void {
    const pkgPath = path.join(targetDir, 'package.json');
    const raw = JSON.parse(fs.readFileSync(pkgPath, 'utf8')) as {
        name?: string;
        private?: boolean;
        type?: string;
        dependencies?: Record<string, string>;
        devDependencies?: Record<string, string>;
    };
    raw.name = projectName;
    raw.dependencies = hostDependencies(target);
    fs.writeFileSync(`${pkgPath}`, `${JSON.stringify(raw, null, 4)}\n`, 'utf8');
}

function writeWaeConfig(targetDir: string, target: DeployTarget): void {
    const configPath = path.join(targetDir, 'wae.config.ts');
    const source = fs.readFileSync(configPath, 'utf8');
    fs.writeFileSync(configPath, patchWaeConfigSource(source, target), 'utf8');
}

export async function cmdCreate(options: WaeCreateOptions): Promise<void> {
    const name = options.name.trim();
    if (!name) {
        throw new Error('project name is required');
    }
    if (name.includes('/') || name.includes('\\') || name === '.' || name === '..') {
        throw new Error(`invalid project name: ${name}`);
    }

    const target = parseDeployTarget(options.server);
    const targetDir = path.resolve(options.cwd, name);
    if (fs.existsSync(targetDir)) {
        throw new Error(`target already exists: ${targetDir}`);
    }

    const templateRoot = resolveTemplateRoot();
    if (!fs.existsSync(templateRoot)) {
        throw new Error(`template missing: ${templateRoot}`);
    }

    fs.mkdirSync(targetDir, { recursive: true });
    const excludeHostEntries = hostEntriesToExclude(target);
    copyTemplateTree(templateRoot, templateRoot, targetDir, excludeHostEntries);
    writePackageJson(targetDir, name, target);
    writeWaeConfig(targetDir, target);
    if (target === 'cloudflare') {
        writeWranglerToml(targetDir, { name, main: 'src/server/worker.ts' });
    }

    console.log(`[wae] created ${targetDir} (deployTarget=${target})`);
}
