/** Single-host deploy target materialization for `wae create` and config normalization. */

import fs from 'node:fs';
import path from 'node:path';
import type { ServerAdapterId } from '../index.js';

export const DEPLOY_TARGETS = ['node', 'deno', 'cloudflare'] as const;

export type DeployTarget = (typeof DEPLOY_TARGETS)[number];

export const HOST_ENTRY_BY_TARGET: Record<DeployTarget, string> = {
    node: 'src/server/node.ts',
    deno: 'src/server/deno.ts',
    cloudflare: 'src/server/worker.ts',
};

const ALL_HOST_ENTRIES = Object.values(HOST_ENTRY_BY_TARGET);

export function isDeployTarget(value: string): value is DeployTarget {
    return (DEPLOY_TARGETS as readonly string[]).includes(value);
}

export function parseDeployTarget(value: string): DeployTarget {
    const normalized = value.trim().toLowerCase();
    if (isDeployTarget(normalized)) return normalized;
    throw new Error(`unsupported deploy target "${value}" (expected node, deno, or cloudflare)`);
}

export function resolveDeployTarget(config: {
    deployTarget?: ServerAdapterId;
    server?: { adapter?: ServerAdapterId };
    platform?: { server?: ServerAdapterId };
}): DeployTarget | undefined {
    const candidate = config.deployTarget ?? config.server?.adapter ?? config.platform?.server;
    if (candidate == null) return undefined;
    if (!isDeployTarget(candidate)) {
        throw new Error(`deployTarget "${candidate}" is not a supported single-host target (node, deno, cloudflare)`);
    }
    return candidate;
}

export function hostDependencies(target: DeployTarget): Record<string, string> {
    const base = {
        '@wae/client': 'workspace:*',
        '@wae/core': 'workspace:*',
    };
    if (target === 'cloudflare') {
        return { ...base, '@wae/serverless': 'workspace:*' };
    }
    return { ...base, '@wae/server': 'workspace:*' };
}

export function hostEntriesToExclude(target: DeployTarget): Set<string> {
    const keep = HOST_ENTRY_BY_TARGET[target];
    return new Set(ALL_HOST_ENTRIES.filter((entry) => entry !== keep));
}

export function readPackageDependencies(cwd: string): Record<string, string> {
    const pkgPath = path.join(cwd, 'package.json');
    if (!fs.existsSync(pkgPath)) return {};
    const pkg = JSON.parse(fs.readFileSync(pkgPath, 'utf8')) as {
        dependencies?: Record<string, string>;
        devDependencies?: Record<string, string>;
    };
    return { ...(pkg.dependencies ?? {}), ...(pkg.devDependencies ?? {}) };
}

/** Ensure `package.json` host deps match `deployTarget` when both are present. */
export function validateDeployTargetDependencies(
    cwd: string,
    config: {
        deployTarget?: ServerAdapterId;
        server?: { adapter?: ServerAdapterId };
        platform?: { server?: ServerAdapterId };
    },
): void {
    const target = resolveDeployTarget(config);
    if (!target) return;

    const deps = readPackageDependencies(cwd);
    if (Object.keys(deps).length === 0) return;

    const hasServer = '@wae/server' in deps;
    const hasServerless = '@wae/serverless' in deps;

    if (hasServer && hasServerless) {
        throw new Error('package.json must not depend on both @wae/server and @wae/serverless');
    }
    if (target === 'cloudflare') {
        if (hasServer) {
            throw new Error('deployTarget cloudflare requires @wae/serverless, not @wae/server');
        }
        if (!hasServerless) {
            throw new Error('deployTarget cloudflare requires @wae/serverless in package.json');
        }
        return;
    }
    if (hasServerless) {
        throw new Error(`deployTarget ${target} requires @wae/server, not @wae/serverless`);
    }
    if (!hasServer) {
        throw new Error(`deployTarget ${target} requires @wae/server in package.json`);
    }
}

export function patchWaeConfigSource(source: string, target: DeployTarget): string {
    let next = source;
    if (/deployTarget\s*:/.test(next)) {
        next = next.replace(/deployTarget\s*:\s*['"][^'"]+['"]/, `deployTarget: '${target}'`);
    } else {
        next = next.replace(
            /export default defineConfig\(\{/,
            `export default defineConfig({\n    deployTarget: '${target}',`,
        );
    }
    next = next.replace(/adapter\s*:\s*['"][^'"]+['"]/, `adapter: '${target}'`);
    return next;
}
