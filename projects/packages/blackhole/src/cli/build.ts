/** `wae build` — produce the shipped product tree + `wae-product.json`. */

import { spawn } from 'node:child_process';
import fs from 'node:fs';
import { createRequire } from 'node:module';
import path from 'node:path';
import type { WaeBuildOptions } from '@wae/commander';
import type { ClientPlatformId } from '@wae/types';
import type { WaeConfig } from '../index.js';
import { resolveProductMeta, writeProductManifest } from '../product/manifest.js';
import { bundleServerEntry } from './build-server.js';
import { writeWranglerToml } from './build-wrangler.js';
import { loadWaeConfig } from './load-config.js';
import { isNativeShellPlatform, platformPackageName, resolvePlatformId } from './platform.js';
import { hasViteConfig, loadFrameworkPlugins, resolveVite } from './vite-helpers.js';

export async function cmdBuild(flags: WaeBuildOptions): Promise<void> {
    const cwd = process.cwd();
    const { path: configPath, config } = await loadWaeConfig(cwd);
    const platformId = resolvePlatformId(flags, config);
    const meta = resolveProductMeta(cwd, config, platformId);

    const baseOut = flags.outDir ?? config.product?.outDir ?? 'dist';
    const outDir = path.resolve(cwd, baseOut, platformId);
    fs.mkdirSync(outDir, { recursive: true });

    console.log(`[wae build] cwd=${cwd}`);
    console.log(`[wae build] platform=${platformId}`);
    console.log(`[wae build] out=${path.relative(cwd, outDir) || outDir}`);

    await buildFrontend(cwd, config, path.join(outDir, meta.frontendDir));
    const serverBundle = await bundleServerEntry(cwd, config, outDir);

    if (isNativeShellPlatform(platformId)) {
        const nativeDest = path.join(outDir, meta.nativeRelativePath);
        await ensureNativeAddon(cwd, platformId, nativeDest);
    }

    const manifestPath = writeProductManifest(outDir, platformId, meta, config, {
        includeNative: isNativeShellPlatform(platformId),
        server: serverBundle
            ? {
                  deployTarget: serverBundle.deployTarget,
                  entry: path.relative(outDir, serverBundle.entryFile).replaceAll('\\', '/'),
              }
            : undefined,
    });
    if (serverBundle?.deployTarget === 'cloudflare') {
        const wranglerPath = writeWranglerToml(outDir, {
            name: meta.name,
            main: path.relative(outDir, serverBundle.entryFile).replaceAll('\\', '/'),
        });
        console.log(`[wae build] wrangler → ${path.relative(cwd, wranglerPath) || wranglerPath}`);
    }
    console.log(`[wae build] manifest=${path.relative(cwd, manifestPath) || manifestPath}`);

    if (isNativeShellPlatform(platformId)) {
        await invokePlatformBuild(platformId, outDir, manifestPath, meta);
    }

    console.log(`[wae build] done — product ${meta.name}@${meta.version}`);
    console.log(`[wae build] config=${path.relative(cwd, configPath) || path.basename(configPath)}`);
}

async function buildFrontend(cwd: string, config: WaeConfig, frontendOut: string): Promise<void> {
    const bundler = config.frontend?.bundler ?? 'vite';
    if (bundler === 'custom') {
        console.log('[wae build] frontend.bundler=custom — skip frontend build');
        return;
    }

    const framework = config.frontend?.framework ?? 'none';
    const vite = await resolveVite(cwd);
    const plugins = hasViteConfig(cwd) ? undefined : await loadFrameworkPlugins(framework, cwd);

    fs.mkdirSync(frontendOut, { recursive: true });
    await vite.build({
        root: cwd,
        configFile: hasViteConfig(cwd) ? undefined : false,
        plugins: plugins as never,
        build: {
            outDir: frontendOut,
            emptyOutDir: true,
        },
        logLevel: 'info',
    });
    console.log(`[wae build] frontend → ${path.relative(cwd, frontendOut) || frontendOut}`);
}

async function ensureNativeAddon(cwd: string, platformId: ClientPlatformId, destPath: string): Promise<void> {
    const pkg = platformPackageName(platformId);
    const req = createRequire(path.join(cwd, 'package.json'));
    let platformRoot: string;
    try {
        platformRoot = path.dirname(req.resolve(`${pkg}/package.json`));
    } catch {
        throw new Error(`desktop/mobile build 需要 ${pkg}。请安装 @wae/wae（optionalDependencies 会按 os/cpu 拉取平台包）。`);
    }

    const libName = path.basename(destPath);
    const built = path.join(platformRoot, 'lib', libName);
    if (!fs.existsSync(built)) {
        console.log(`[wae build] ${pkg} lib/${libName} missing — running build:native …`);
        await runPackageScript(platformRoot, 'build:native');
    }
    if (!fs.existsSync(built)) {
        throw new Error(`未找到 lib/${libName}。请在 WAE 仓库执行 pnpm run build:native，或确保 ${pkg} 发布包内含 lib/。`);
    }

    fs.mkdirSync(path.dirname(destPath), { recursive: true });
    fs.copyFileSync(built, destPath);
    console.log(`[wae build] native → ${path.basename(destPath)}`);
}

function runPackageScript(packageRoot: string, script: string): Promise<void> {
    const pkgPath = path.join(packageRoot, 'package.json');
    const pkg = JSON.parse(fs.readFileSync(pkgPath, 'utf8')) as { scripts?: Record<string, string> };
    if (!pkg.scripts?.[script]) {
        return Promise.reject(new Error(`${path.basename(packageRoot)} 缺少 script ${script}`));
    }
    const npmCmd = process.platform === 'win32' ? 'npm.cmd' : 'npm';
    return new Promise((resolve, reject) => {
        const child = spawn(npmCmd, ['run', script], {
            cwd: packageRoot,
            stdio: 'inherit',
            shell: false,
            windowsHide: true,
        });
        child.on('error', reject);
        child.on('exit', (code) => {
            if (code === 0) resolve();
            else reject(new Error(`npm run ${script} exited with ${code ?? 'unknown'}`));
        });
    });
}

async function invokePlatformBuild(
    platformId: ClientPlatformId,
    outDir: string,
    manifestPath: string,
    meta: { nativeRelativePath: string },
): Promise<void> {
    const pkg = platformPackageName(platformId);
    let mod: {
        platform?: {
            build?: (o: { outDir: string; manifestPath: string; nativePath: string }) => Promise<void>;
        };
        default?: {
            build?: (o: { outDir: string; manifestPath: string; nativePath: string }) => Promise<void>;
        };
    };
    try {
        mod = await import(pkg);
    } catch {
        console.log(`[wae build] skip ${pkg}.build (package not installed)`);
        return;
    }
    const platform = mod.platform ?? mod.default;
    if (!platform?.build) return;
    await platform.build({
        outDir,
        manifestPath,
        nativePath: path.join(outDir, meta.nativeRelativePath),
    });
}
