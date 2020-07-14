/** `wae publish` — upload built Worker via Cloudflare Scripts API. */

import type { WaePublishOptions } from '@wae/commander';
import { resolveWorkerBindings } from '@wae/serverless/cloudflare/bindings';
import { publishWorkerBundle } from '@wae/serverless/cloudflare/publish';
import { syncWorkerCustomDomains } from '@wae/serverless/cloudflare/domains';
import { syncWorkerRoutes } from '@wae/serverless/cloudflare/routes';
import { resolveDeployTarget } from './deploy-target.js';
import { loadWaeConfig } from './load-config.js';
import { loadPublishArtifact } from './publish-artifact.js';
import { loadServerBundleArtifact, materializeServerPublish, serverPublishCommand } from './publish-server.js';
import { resolveProductDir } from './resolve-product-dir.js';

export function resolveCloudflareCredentials(config: {
    cloudflare?: { accountId?: string; apiToken?: string };
}): { accountId: string; apiToken: string } {
    const accountId = config.cloudflare?.accountId ?? process.env.CLOUDFLARE_ACCOUNT_ID;
    const apiToken = config.cloudflare?.apiToken ?? process.env.CLOUDFLARE_API_TOKEN;
    if (!accountId) {
        throw new Error('missing Cloudflare account id — set CLOUDFLARE_ACCOUNT_ID or cloudflare.accountId in wae.config');
    }
    if (!apiToken) {
        throw new Error('missing Cloudflare API token — set CLOUDFLARE_API_TOKEN or cloudflare.apiToken in wae.config');
    }
    return { accountId, apiToken };
}

export async function cmdPublish(options: WaePublishOptions): Promise<void> {
    const cwd = process.cwd();
    const { path: configPath, config } = await loadWaeConfig(cwd);
    const deployTarget = resolveDeployTarget(config);
    if (!deployTarget) {
        throw new Error('wae publish requires deployTarget in wae.config');
    }

    const productRoot = resolveProductDir(cwd, options, config);

    if (deployTarget === 'node' || deployTarget === 'deno') {
        const serverArtifact = loadServerBundleArtifact(productRoot);
        if (serverArtifact.deployTarget !== deployTarget) {
            throw new Error(
                `wae publish deployTarget=${deployTarget} but ${serverArtifact.manifest.name ?? 'product'} built for ${serverArtifact.deployTarget}`,
            );
        }
        const manifestPath = materializeServerPublish(productRoot, serverArtifact);
        console.log(`[wae publish] deployTarget=${deployTarget}`);
        console.log(`[wae publish] entry=${serverArtifact.entryFile}`);
        console.log(`[wae publish] command=${serverPublishCommand(deployTarget, serverArtifact.entryFile)}`);
        console.log(`[wae publish] manifest=${manifestPath}`);
        console.log(`[wae publish] product=${productRoot}`);
        console.log(`[wae publish] config=${configPath}`);
        return;
    }

    if (deployTarget !== 'cloudflare') {
        throw new Error(`wae publish does not support deployTarget=${deployTarget}`);
    }

    const artifact = loadPublishArtifact(productRoot);
    const credentials = resolveCloudflareCredentials(config);
    const scriptName = config.cloudflare?.scriptName ?? artifact.manifest.name;
    const bindings = config.cloudflare?.bindings?.length ? resolveWorkerBindings(config.cloudflare.bindings) : undefined;
    const result = await publishWorkerBundle({
        accountId: credentials.accountId,
        apiToken: credentials.apiToken,
        scriptName,
        moduleFile: artifact.moduleFile,
        scriptBody: artifact.scriptBody,
        compatibilityDate: config.cloudflare?.compatibilityDate,
        bindings,
    });

    const customDomains = config.cloudflare?.customDomains;
    if (customDomains?.length) {
        const domainSync = await syncWorkerCustomDomains({
            accountId: credentials.accountId,
            apiToken: credentials.apiToken,
            scriptName,
            domains: customDomains,
        });
        if (domainSync.created.length) {
            console.log(`[wae publish] customDomains created=${domainSync.created.join(',')}`);
        }
        if (domainSync.updated.length) {
            console.log(`[wae publish] customDomains updated=${domainSync.updated.join(',')}`);
        }
        if (domainSync.unchanged.length) {
            console.log(`[wae publish] customDomains unchanged=${domainSync.unchanged.join(',')}`);
        }
    }

    const routes = config.cloudflare?.routes;
    if (routes?.length) {
        const routeSync = await syncWorkerRoutes({
            accountId: credentials.accountId,
            apiToken: credentials.apiToken,
            scriptName,
            routes,
        });
        if (routeSync.created.length) {
            console.log(`[wae publish] routes created=${routeSync.created.join(',')}`);
        }
        if (routeSync.updated.length) {
            console.log(`[wae publish] routes updated=${routeSync.updated.join(',')}`);
        }
        if (routeSync.unchanged.length) {
            console.log(`[wae publish] routes unchanged=${routeSync.unchanged.join(',')}`);
        }
    }

    console.log(`[wae publish] script=${result.scriptName}`);
    console.log(`[wae publish] account=${result.accountId}`);
    console.log(`[wae publish] module=${result.moduleFile}`);
    if (result.etag) {
        console.log(`[wae publish] etag=${result.etag}`);
    }
    console.log(`[wae publish] product=${productRoot}`);
    console.log(`[wae publish] config=${configPath}`);
}
