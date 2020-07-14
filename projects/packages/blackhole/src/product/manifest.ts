/** `wae-product.json` helpers (build + runtime). */

import fs from 'node:fs';
import path from 'node:path';
import type { ClientPlatformId, WaeProductManifest, WaeProductServerBundle } from '@wae/types';
import { isNativeShellPlatform, platformNativeLibFile, WAE_PRODUCT_MANIFEST } from '@wae/types';
import type { WaeConfig } from '../index.js';

export { WAE_PRODUCT_MANIFEST };

export type ResolvedProductMeta = {
    name: string;
    version: string;
    nativeRelativePath: string;
    frontendDir: string;
};

export function readPackageJson(cwd: string): { name?: string; version?: string } {
    const pkgPath = path.join(cwd, 'package.json');
    if (!fs.existsSync(pkgPath)) return {};
    return JSON.parse(fs.readFileSync(pkgPath, 'utf8')) as { name?: string; version?: string };
}

export function resolveProductMeta(cwd: string, config: WaeConfig, platform?: ClientPlatformId): ResolvedProductMeta {
    const pkg = readPackageJson(cwd);
    const name = config.product?.name ?? pkg.name;
    const version = config.product?.version ?? pkg.version;
    if (!name) {
        throw new Error('product.name 未设置，且 package.json 缺少 name');
    }
    const resolvedVersion = version ?? '0.0.0';
    const nativeDir = config.product?.native?.dir ?? 'lib';
    const nativeFile =
        config.product?.native?.fileName ?? (platform && isNativeShellPlatform(platform) ? platformNativeLibFile(platform) : 'wae-napi.node');
    return {
        name,
        version: resolvedVersion,
        nativeRelativePath: path.join(nativeDir, nativeFile).replaceAll('\\', '/'),
        frontendDir: config.product?.frontendDir ?? 'frontend',
    };
}

export function writeProductManifest(
    outDir: string,
    platform: ClientPlatformId,
    meta: ResolvedProductMeta,
    config: WaeConfig,
    options?: { includeNative?: boolean; server?: WaeProductServerBundle },
): string {
    const manifest: WaeProductManifest = {
        schemaVersion: 1,
        name: meta.name,
        version: meta.version,
        platform,
        frontendDir: meta.frontendDir,
        update: config.product?.update,
    };
    if (options?.includeNative) {
        manifest.nativePath = meta.nativeRelativePath;
    }
    if (options?.server) {
        manifest.server = options.server;
    }
    const manifestPath = path.join(outDir, WAE_PRODUCT_MANIFEST);
    fs.writeFileSync(manifestPath, `${JSON.stringify(manifest, null, 4)}\n`, 'utf8');
    return manifestPath;
}

export function loadProductManifest(productRoot: string): WaeProductManifest {
    const manifestPath = path.join(productRoot, WAE_PRODUCT_MANIFEST);
    if (!fs.existsSync(manifestPath)) {
        throw new Error(`missing ${WAE_PRODUCT_MANIFEST} under ${productRoot}`);
    }
    const raw = JSON.parse(fs.readFileSync(manifestPath, 'utf8')) as WaeProductManifest;
    if (raw.schemaVersion !== 1) {
        throw new Error(`unsupported ${WAE_PRODUCT_MANIFEST} schemaVersion ${raw.schemaVersion}`);
    }
    return raw;
}

export function resolveNativeAbsolutePath(manifest: WaeProductManifest, productRoot: string): string {
    if (!manifest.nativePath) {
        throw new Error(`${WAE_PRODUCT_MANIFEST} has no nativePath (not a native product build)`);
    }
    return path.resolve(productRoot, manifest.nativePath);
}
