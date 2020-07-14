/** Product updater for `wae build` trees — loads the shipped `.node` from `lib/`. */

import { createRequire } from 'node:module';

import type { ProductUpdateOptions, ProductUpdateStatus, WaeNativeAddon, WaeProductManifest } from '@wae/types';

import { WAE_PRODUCT_MANIFEST } from '@wae/types';

import { loadProductManifest, resolveNativeAbsolutePath } from './manifest.js';

function loadNativeAddon(nativePath: string): WaeNativeAddon {
    const req = createRequire(import.meta.url);

    return req(nativePath) as WaeNativeAddon;
}

function toNativeOptions(manifest: WaeProductManifest, productRoot: string): ProductUpdateOptions {
    const update = manifest.update;

    if (!update?.github) {
        throw new Error(`${WAE_PRODUCT_MANIFEST} missing product.update.github`);
    }

    return {
        repo: update.github,

        productName: manifest.name,

        nativePath: resolveNativeAbsolutePath(manifest, productRoot),

        currentVersion: manifest.version,

        channel: update.channel,

        downloadPolicy: update.downloadPolicy,

        allowPrerelease: update.allowPrerelease,

        tag: update.tag,
    };
}

export function checkProductUpdateFromManifest(
    manifest: WaeProductManifest,

    productRoot: string,

    native?: WaeNativeAddon | null,
): ProductUpdateStatus {
    const addon = native ?? loadNativeAddon(resolveNativeAbsolutePath(manifest, productRoot));

    return addon.checkProductUpdate(toNativeOptions(manifest, productRoot));
}

export function downloadProductUpdateFromManifest(
    manifest: WaeProductManifest,

    productRoot: string,

    native?: WaeNativeAddon | null,
): ProductUpdateStatus {
    const addon = native ?? loadNativeAddon(resolveNativeAbsolutePath(manifest, productRoot));

    return addon.downloadProductUpdate(toNativeOptions(manifest, productRoot));
}

export function applyProductUpdateFromManifest(
    manifest: WaeProductManifest,

    productRoot: string,

    native?: WaeNativeAddon | null,

    stagedNativePath?: string,
): ProductUpdateStatus {
    const addon = native ?? loadNativeAddon(resolveNativeAbsolutePath(manifest, productRoot));

    const options = toNativeOptions(manifest, productRoot);

    if (stagedNativePath) {
        options.stagedNativePath = stagedNativePath;
    }

    return addon.applyProductUpdate(options);
}

export { loadProductManifest };
