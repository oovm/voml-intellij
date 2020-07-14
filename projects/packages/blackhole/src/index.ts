/** @wae/wae — Node CLI 与工程编排（不含 Rust runtime；不替代框架 CLI）。 */

import type { CloudflareBindingInput } from '@wae/serverless/cloudflare/bindings';
import type {
    ClientPlatformId,
    WaeProductDownloadPolicy,
    WaeProductManifest,
    WaeProductUpdateChannel,
    WaeProductUpdateConfig,
} from '@wae/types';

export { WAE_PRODUCT_MANIFEST } from '@wae/types';
export { loadProductManifest, type ResolvedProductMeta, resolveNativeAbsolutePath } from './product/manifest.js';
export { applyProductUpdateFromManifest, checkProductUpdateFromManifest, downloadProductUpdateFromManifest } from './product/self-update.js';
export type { ClientPlatformId, WaeProductDownloadPolicy, WaeProductManifest, WaeProductUpdateChannel, WaeProductUpdateConfig };

export type ServerAdapterId = 'node' | 'deno' | 'cloudflare' | 'bun';

export type FrontendFramework = 'vue' | 'react' | 'svelte' | 'solid' | 'none';

/**
 * 前端打包 / 开发服务器工具链。
 * - `vite`：常用默认；`wae run`（web）会代启 Vite。
 * - `custom`：自备 Webpack / Rspack / Parcel 等；`wae` 不代启 bundler。
 */
export type FrontendBundler = 'vite' | 'custom';

export type RuntimeTarget = 'web' | 'desktop' | 'mobile';

export type FrontendAdapterFactory = {
    name: string;
};

export type WaeConfig = {
    frontend?: {
        /** 由 CLI 推断或显式指定；`none` = 纯 TS / 无框架 */
        framework?: FrontendFramework;
        /** 可选：adapter 工厂（如 react()），不把包名写进 API */
        adapter?: FrontendAdapterFactory;
        entry?: string;
        /**
         * 前端工具链。默认 `vite`（与 Tauri 类似：常用 Vite，但可换）。
         * `custom` 时请自行启动 bundler，并用 `devUrl` 告知开发地址。
         */
        bundler?: FrontendBundler;
        /** `bundler: "custom"` 时的开发服务器 URL（例如 http://127.0.0.1:3000） */
        devUrl?: string;
    };
    /** Single backend host selection (node, deno, cloudflare). Mirrors `server.adapter`. */
    deployTarget?: ServerAdapterId;
    /** Cloudflare Workers publish credentials and script options (`wae publish`). */
    cloudflare?: {
        accountId?: string;
        apiToken?: string;
        scriptName?: string;
        compatibilityDate?: string;
        /** Typed binding specs (`kind`) or raw API wire records (`type`). */
        bindings?: CloudflareBindingInput[];
        /** HTTP route patterns bound to the published script (`syncWorkerRoutes`). */
        routes?: Array<{ pattern: string }>;
        /** Custom domains attached to the published script (`syncWorkerCustomDomains`). */
        customDomains?: Array<{
            hostname: string;
            zoneId?: string;
            zoneName?: string;
            overrideExistingOrigin?: boolean;
        }>;
    };
    server?: {
        entry?: string;
        adapter?: ServerAdapterId;
    };
    /** 运行目标：浏览器 / 桌面壳 / 移动壳 */
    target?: RuntimeTarget;
    platform?: {
        client?: ClientPlatformId;
        server?: ServerAdapterId;
    };
    /**
     * Shipped **product** metadata (`wae build` → `wae-product.json`).
     * WAE toolchain itself is not a product — only your built app is.
     */
    product?: {
        /** Defaults to `package.json` `name`. */
        name?: string;
        /** Defaults to `package.json` `version`. */
        version?: string;
        /** Base output directory. Default `dist` (per-platform subdir is added). */
        outDir?: string;
        /** Frontend bundle folder inside the product tree. Default `frontend`. */
        frontendDir?: string;
        native?: {
            /** Default `lib`. */
            dir?: string;
            /** Platform-specific name (e.g. `win32-x64-msvc.node`). */
            fileName?: string;
        };
        /** GitHub Releases self-update for the **built product** (native addon). */
        update?: WaeProductUpdateConfig;
    };
};

/**
 * 配置规范化与类型约束。
 * 文件已是 `wae.config.ts`，API 名无需再带 Wae 前缀。
 */
export function defineConfig(config: WaeConfig): WaeConfig {
    const deployTarget = config.deployTarget ?? config.server?.adapter ?? config.platform?.server;
    return {
        frontend: {
            framework: config.frontend?.framework ?? 'none',
            adapter: config.frontend?.adapter,
            entry: config.frontend?.entry,
            bundler: config.frontend?.bundler ?? 'vite',
            devUrl: config.frontend?.devUrl,
        },
        deployTarget,
        cloudflare: config.cloudflare
            ? {
                  accountId: config.cloudflare.accountId,
                  apiToken: config.cloudflare.apiToken,
                  scriptName: config.cloudflare.scriptName,
                  compatibilityDate: config.cloudflare.compatibilityDate,
                  bindings: config.cloudflare.bindings,
                  routes: config.cloudflare.routes,
                  customDomains: config.cloudflare.customDomains,
              }
            : undefined,
        server: config.server
            ? {
                  entry: config.server.entry,
                  adapter: deployTarget ?? config.server.adapter,
              }
            : deployTarget
              ? { adapter: deployTarget }
              : undefined,
        target: config.target ?? 'web',
        platform: config.platform,
        product: config.product
            ? {
                  name: config.product.name,
                  version: config.product.version,
                  outDir: config.product.outDir,
                  frontendDir: config.product.frontendDir,
                  native: config.product.native,
                  update: config.product.update,
              }
            : undefined,
    };
}
