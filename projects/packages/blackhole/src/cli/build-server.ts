/** Bundle the deploy-target host entry (`node` / `deno` / `worker`) for `wae build`. */

import fs from 'node:fs';
import path from 'node:path';
import * as esbuild from 'esbuild';
import type { WaeConfig } from '../index.js';
import { HOST_ENTRY_BY_TARGET, resolveDeployTarget, type DeployTarget } from './deploy-target.js';

export type ServerBundleResult = {
    deployTarget: DeployTarget;
    outDir: string;
    entryFile: string;
    manifestFile: string;
};

function outputFileName(target: DeployTarget): string {
    if (target === 'cloudflare') return 'worker.mjs';
    return `${target}.mjs`;
}

function esbuildPlatform(target: DeployTarget): esbuild.Platform {
    return target === 'cloudflare' || target === 'deno' ? 'neutral' : 'node';
}

/**
 * When `deployTarget` is set, bundle `src/server/{node,deno,worker}.ts` into `outDir/server/`.
 * Returns `null` when no deploy target is configured.
 */
export type BundleServerOptions = {
    /** Conformance-only: resolve workspace packages when the project has no local node_modules. */
    alias?: Record<string, string>;
};

export async function bundleServerEntry(
    cwd: string,
    config: WaeConfig,
    outDir: string,
    options: BundleServerOptions = {},
): Promise<ServerBundleResult | null> {
    const deployTarget = resolveDeployTarget(config);
    if (!deployTarget) {
        console.log('[wae build] no deployTarget — skip server bundle');
        return null;
    }

    const entryRelative = HOST_ENTRY_BY_TARGET[deployTarget];
    const entryPath = path.join(cwd, entryRelative);
    if (!fs.existsSync(entryPath)) {
        throw new Error(`deployTarget "${deployTarget}" requires ${entryRelative}`);
    }

    const serverOut = path.join(outDir, 'server');
    fs.mkdirSync(serverOut, { recursive: true });
    const entryFile = path.join(serverOut, outputFileName(deployTarget));

    await esbuild.build({
        entryPoints: [entryPath],
        outfile: entryFile,
        bundle: true,
        platform: esbuildPlatform(deployTarget),
        format: 'esm',
        target: deployTarget === 'cloudflare' ? 'es2022' : 'node20',
        packages: options.alias ? undefined : 'external',
        external: deployTarget === 'node' ? ['ws'] : undefined,
        alias: options.alias,
        absWorkingDir: cwd,
        logLevel: 'silent',
    });

    const manifestFile = path.join(serverOut, 'server-bundle.json');
    fs.writeFileSync(
        manifestFile,
        `${JSON.stringify({ deployTarget, entry: path.basename(entryFile) }, null, 2)}\n`,
        'utf8',
    );

    console.log(`[wae build] server (${deployTarget}) → ${path.relative(cwd, entryFile) || entryFile}`);
    return { deployTarget, outDir: serverOut, entryFile, manifestFile };
}
