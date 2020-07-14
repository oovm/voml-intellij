/** `wae run` / `wae dev`：按平台启动应用。web / desktop 默认常用 Vite（可换）。 */

import path from 'node:path';
import type { WaeRunOptions } from '@wae/commander';
import type { ClientPlatformId, FrontendFramework, WaeConfig } from '../index.js';
import { loadWaeConfig } from './load-config.js';
import { isNativeShellPlatform, platformPackageName, resolvePlatformId } from './platform.js';
import { hasViteConfig, loadFrameworkPlugins, resolveVite } from './vite-helpers.js';

export type RunMode = 'run' | 'dev';

type ViteHandle = {
    // Vite types vary with the installed peer version.
    server: any;
    url: string;
    framework: FrontendFramework;
};

function envFlag(name: string): boolean {
    const value = process.env[name];
    return value === '1' || value === 'true' || value === 'TRUE' || value === 'yes';
}

/** WebView2 首屏前预拉 dev server，避免 Vite 首次 optimize 与桌面 Navigate 竞态白屏。 */
async function warmupViteDevServer(url: string): Promise<void> {
    try {
        const entry = url.endsWith('/') ? url : `${url}/`;
        const page = await fetch(entry);
        if (!page.ok) {
            throw new Error(`HTTP ${page.status}`);
        }
        await page.text();
        await fetch(new URL('src/main.ts', entry));
        console.log(`[wae] Vite warmup ok → ${entry}`);
    } catch (err) {
        console.warn(`[wae] Vite warmup skipped: ${err instanceof Error ? err.message : String(err)}`);
    }
}

async function startVite(cwd: string, config: WaeConfig, flags: WaeRunOptions, openBrowser: boolean, strictPort = false): Promise<ViteHandle> {
    const vite = await resolveVite(cwd);
    const framework = config.frontend?.framework ?? 'none';
    const plugins = hasViteConfig(cwd) ? undefined : await loadFrameworkPlugins(framework, cwd);

    const server = await vite.createServer({
        root: cwd,
        configFile: hasViteConfig(cwd) ? undefined : false,
        plugins: plugins as never,
        server: {
            host: flags.host ?? '127.0.0.1',
            port: flags.port ?? 5173,
            strictPort,
            open: openBrowser,
        },
        clearScreen: false,
    });

    await server.listen();
    const local = server.resolvedUrls?.local?.[0];
    if (!local) {
        await server.close();
        throw new Error('Vite 已启动但未得到 Local URL');
    }
    return { server, url: local, framework };
}

async function runWeb(cwd: string, config: WaeConfig, configPath: string, flags: WaeRunOptions): Promise<void> {
    const { server, url, framework } = await startVite(cwd, config, flags, flags.open);
    console.log(`[wae] platform=web framework=${framework}`);
    console.log(`[wae] config=${path.relative(cwd, configPath) || path.basename(configPath)}`);
    console.log(`[wae]  Local:   ${url}`);
    console.log('[wae] 按 Ctrl+C 结束');

    await new Promise<void>((resolve) => {
        const stop = async () => {
            process.off('SIGINT', onSig);
            process.off('SIGTERM', onSig);
            await server.close();
            resolve();
        };
        const onSig = () => {
            void stop();
        };
        process.on('SIGINT', onSig);
        process.on('SIGTERM', onSig);
    });
}

async function runDesktopShell(id: ClientPlatformId, cwd: string, config: WaeConfig, configPath: string, flags: WaeRunOptions): Promise<void> {
    const bundler = config.frontend?.bundler ?? 'vite';
    let vite: ViteHandle | null = null;
    let url = config.frontend?.devUrl;

    if (bundler === 'vite') {
        vite = await startVite(cwd, config, flags, false, true);
        url = vite.url;
        console.log(`[wae] frontend Vite → ${url}`);
    } else if (!url) {
        throw new Error('desktop + bundler:"custom" 需要 frontend.devUrl（例如 http://127.0.0.1:5173/）');
    }

    const pkg = platformPackageName(id);
    let mod: {
        platform?: {
            run: (o: { entry?: string; url?: string; title?: string; undecorated?: boolean }) => Promise<void>;
        };
        default?: {
            run: (o: { entry?: string; url?: string; title?: string; undecorated?: boolean }) => Promise<void>;
        };
    };
    try {
        mod = await import(pkg);
    } catch (e) {
        if (vite) await vite.server.close();
        throw new Error(`无法加载平台包 ${pkg}（${e instanceof Error ? e.message : e}）。请确认已安装 @wae/wae 或其 optionalDependencies。`);
    }
    const platform = mod.platform ?? mod.default;
    if (!platform?.run) {
        if (vite) await vite.server.close();
        throw new Error(`${pkg} 未导出 platform.run`);
    }

    console.log(`[wae] platform=${id} → ${pkg}.run({ url })`);
    console.log(`[wae] config=${path.relative(cwd, configPath) || path.basename(configPath)}`);
    console.log('[wae] 关闭桌面窗口后结束');

    if (vite) {
        await warmupViteDevServer(url);
    }

    try {
        await platform.run({
            entry: config.frontend?.entry,
            url,
            title: process.env.WAE_WINDOW_TITLE ?? `WAE · ${config.frontend?.framework ?? 'app'}`,
            undecorated: envFlag('WAE_UNDECORATED'),
        });
    } finally {
        if (vite) await vite.server.close();
    }
}

export async function cmdRun(flags: WaeRunOptions, _opts: { mode: RunMode }): Promise<void> {
    const cwd = process.cwd();
    const { path: configPath, config } = await loadWaeConfig(cwd);
    const platformId = resolvePlatformId(flags, config);

    console.log(`[wae] cwd=${cwd}`);
    console.log(`[wae] loaded ${path.relative(cwd, configPath) || path.basename(configPath)}`);

    if (platformId === 'web') {
        const bundler = config.frontend?.bundler ?? 'vite';
        if (bundler === 'custom') {
            const devUrl = config.frontend?.devUrl;
            console.log('[wae] frontend.bundler=custom：不代启 Vite（可换 Webpack / Rspack 等）');
            if (devUrl) {
                console.log(`[wae] 请自行启动 bundler，开发地址约定为 ${devUrl}`);
            } else {
                console.log('[wae] 请自行启动 bundler，并在 wae.config 中设置 frontend.devUrl（例如 http://127.0.0.1:3000）');
            }
            return;
        }
        await runWeb(cwd, config, configPath, flags);
        return;
    }

    if (isNativeShellPlatform(platformId)) {
        await runDesktopShell(platformId, cwd, config, configPath, flags);
        return;
    }

    // unknown-wasm32 等：暂只调 platform.run
    const pkg = platformPackageName(platformId);
    const mod = await import(pkg);
    const platform = mod.platform ?? mod.default;
    await platform.run({ entry: config.frontend?.entry });
}
