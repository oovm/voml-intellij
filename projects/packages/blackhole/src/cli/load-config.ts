/** 加载工程根目录的 `wae.config.*`。 */

import fs from 'node:fs';
import { createRequire } from 'node:module';
import path from 'node:path';
import { pathToFileURL } from 'node:url';
import * as esbuild from 'esbuild';
import { defineConfig, type WaeConfig } from '../index.js';
import { validateDeployTargetDependencies } from './deploy-target.js';

const CONFIG_NAMES = ['wae.config.ts', 'wae.config.mts', 'wae.config.js', 'wae.config.mjs', 'wae.config.cjs'];

export type LoadedConfig = {
    path: string;
    config: WaeConfig;
};

export function findConfigPath(cwd: string): string | null {
    for (const name of CONFIG_NAMES) {
        const p = path.join(cwd, name);
        if (fs.existsSync(p)) return p;
    }
    return null;
}

export async function loadWaeConfig(cwd: string): Promise<LoadedConfig> {
    const configPath = findConfigPath(cwd);
    if (!configPath) {
        throw new Error(`未找到 wae.config（尝试过 ${CONFIG_NAMES.join(', ')}）。请在工程根目录添加 wae.config.ts。`);
    }

    const ext = path.extname(configPath);
    if (ext === '.js' || ext === '.mjs') {
        const mod = await import(pathToFileURL(configPath).href);
        return finalize(cwd, configPath, mod);
    }
    if (ext === '.cjs') {
        const require = createRequire(import.meta.url);
        return finalize(cwd, configPath, require(configPath));
    }

    const cacheDir = path.join(cwd, 'node_modules', '.cache', 'wae');
    fs.mkdirSync(cacheDir, { recursive: true });
    const outfile = path.join(cacheDir, `config-${process.pid}-${Date.now()}.mjs`);
    try {
        await esbuild.build({
            entryPoints: [configPath],
            outfile,
            bundle: true,
            platform: 'node',
            format: 'esm',
            target: 'node20',
            packages: 'external',
            absWorkingDir: cwd,
            logLevel: 'silent',
        });
        const mod = await import(pathToFileURL(outfile).href);
        return finalize(cwd, configPath, mod);
    } finally {
        try {
            fs.unlinkSync(outfile);
        } catch {
            /* ignore */
        }
    }
}

function normalize(mod: unknown): WaeConfig {
    const raw = mod && typeof mod === 'object' && 'default' in mod ? (mod as { default: unknown }).default : mod;
    if (!raw || typeof raw !== 'object') {
        throw new Error('wae.config 必须 default export 一个配置对象（建议用 defineConfig）');
    }
    return defineConfig(raw as WaeConfig);
}

function finalize(cwd: string, configPath: string, mod: unknown): LoadedConfig {
    const config = normalize(mod);
    validateDeployTargetDependencies(cwd, config);
    return { path: configPath, config };
}
