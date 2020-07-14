/** Vite resolution shared by `wae run` and `wae build`. */

import fs from 'node:fs';
import path from 'node:path';
import { pathToFileURL } from 'node:url';
import type { FrontendFramework } from '../index.js';

export function hasViteConfig(cwd: string): boolean {
    return ['vite.config.ts', 'vite.config.mts', 'vite.config.js', 'vite.config.mjs'].some((n) => fs.existsSync(path.join(cwd, n)));
}

export async function resolveVite(cwd: string) {
    try {
        return await import('vite');
    } catch {
        const fromCwd = pathToFileURL(path.join(cwd, 'node_modules', 'vite', 'dist', 'node', 'index.js')).href;
        try {
            return await import(fromCwd);
        } catch {
            throw new Error('未找到 `vite`。请在工程中安装：pnpm add -D vite@^7，或在仓库根保证 workspace 已安装。');
        }
    }
}

export async function loadFrameworkPlugins(framework: FrontendFramework, cwd: string): Promise<unknown[]> {
    if (framework === 'none') return [];

    const tryImport = async (spec: string) => {
        try {
            return await import(spec);
        } catch {
            const local = path.join(cwd, 'node_modules', spec);
            if (fs.existsSync(local)) {
                return await import(pathToFileURL(path.join(local, 'dist', 'index.js')).href);
            }
            const { createRequire } = await import('node:module');
            const req = createRequire(path.join(cwd, 'package.json'));
            const resolved = req.resolve(spec);
            return await import(pathToFileURL(resolved).href);
        }
    };

    if (framework === 'vue') {
        const mod = await tryImport('@vitejs/plugin-vue');
        const plugin = mod.default ?? mod;
        return [typeof plugin === 'function' ? plugin() : plugin];
    }
    if (framework === 'react') {
        const mod = await tryImport('@vitejs/plugin-react');
        const plugin = mod.default ?? mod;
        return [typeof plugin === 'function' ? plugin() : plugin];
    }
    if (framework === 'svelte') {
        const mod = await tryImport('@sveltejs/vite-plugin-svelte');
        const plugin = mod.svelte ?? mod.default ?? mod;
        return [typeof plugin === 'function' ? plugin() : plugin];
    }
    if (framework === 'solid') {
        const mod = await tryImport('vite-plugin-solid');
        const plugin = mod.default ?? mod;
        return [typeof plugin === 'function' ? plugin() : plugin];
    }
    return [];
}
