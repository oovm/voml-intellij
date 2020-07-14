/** Materialize Node/Deno server publish manifests from `wae build` artifacts. */

import fs from 'node:fs';
import path from 'node:path';
import type { WaeProductManifest } from '@wae/types';
import { WAE_PRODUCT_MANIFEST } from '@wae/types';
import type { DeployTarget } from './deploy-target.js';

export const SERVER_PUBLISH_MANIFEST = 'server-publish.json';

export type ServerBundleArtifact = {
    manifest: WaeProductManifest;
    bundlePath: string;
    bundleBody: string;
    entryFile: string;
    deployTarget: DeployTarget;
};

export type ServerPublishManifest = {
    schemaVersion: 1;
    deployTarget: 'node' | 'deno';
    entry: string;
    command: string;
    product: string;
};

export function loadServerBundleArtifact(productRoot: string): ServerBundleArtifact {
    const manifestPath = path.join(productRoot, WAE_PRODUCT_MANIFEST);
    if (!fs.existsSync(manifestPath)) {
        throw new Error(`missing ${WAE_PRODUCT_MANIFEST} under ${productRoot}`);
    }

    const manifest = JSON.parse(fs.readFileSync(manifestPath, 'utf8')) as WaeProductManifest;
    if (manifest.schemaVersion !== 1) {
        throw new Error(`unsupported ${WAE_PRODUCT_MANIFEST} schemaVersion ${manifest.schemaVersion}`);
    }
    if (!manifest.server?.deployTarget) {
        throw new Error(`${WAE_PRODUCT_MANIFEST} must include server.deployTarget for publish`);
    }

    const deployTarget = manifest.server.deployTarget;
    if (deployTarget !== 'node' && deployTarget !== 'deno' && deployTarget !== 'cloudflare') {
        throw new Error(`unsupported server.deployTarget ${deployTarget} for publish`);
    }

    const entryFile = manifest.server.entry.replaceAll('\\', '/');
    const bundlePath = path.resolve(productRoot, entryFile);
    if (!fs.existsSync(bundlePath)) {
        throw new Error(`missing server bundle at ${entryFile}`);
    }

    const bundleBody = fs.readFileSync(bundlePath, 'utf8');
    if (bundleBody.trim().length === 0) {
        throw new Error(`server bundle ${entryFile} is empty`);
    }

    return { manifest, bundlePath, bundleBody, entryFile, deployTarget };
}

export function serverPublishCommand(deployTarget: 'node' | 'deno', entryFile: string): string {
    if (deployTarget === 'node') {
        return `node ${entryFile}`;
    }
    return `deno run --allow-net ${entryFile}`;
}

export function materializeServerPublish(productRoot: string, artifact: ServerBundleArtifact): string {
    if (artifact.deployTarget !== 'node' && artifact.deployTarget !== 'deno') {
        throw new Error(`materializeServerPublish requires deployTarget node or deno (got ${artifact.deployTarget})`);
    }

    const publishDir = path.join(productRoot, 'publish');
    fs.mkdirSync(publishDir, { recursive: true });
    const manifestPath = path.join(publishDir, SERVER_PUBLISH_MANIFEST);
    const publishManifest: ServerPublishManifest = {
        schemaVersion: 1,
        deployTarget: artifact.deployTarget,
        entry: artifact.entryFile,
        command: serverPublishCommand(artifact.deployTarget, artifact.entryFile),
        product: WAE_PRODUCT_MANIFEST,
    };
    fs.writeFileSync(manifestPath, `${JSON.stringify(publishManifest, null, 4)}\n`, 'utf8');
    return manifestPath;
}
