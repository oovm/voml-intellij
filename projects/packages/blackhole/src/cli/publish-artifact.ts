/** Load `wae build` Cloudflare Worker artifacts from a product directory. */

import path from 'node:path';
import type { WaeProductManifest } from '@wae/types';
import { loadServerBundleArtifact } from './publish-server.js';

export type PublishArtifact = {
    manifest: WaeProductManifest;
    scriptPath: string;
    scriptBody: string;
    moduleFile: string;
};

export function loadPublishArtifact(productRoot: string): PublishArtifact {
    const artifact = loadServerBundleArtifact(productRoot);
    if (artifact.deployTarget !== 'cloudflare') {
        throw new Error('loadPublishArtifact requires server.deployTarget=cloudflare');
    }

    const moduleFile = path.basename(artifact.entryFile);
    return {
        manifest: artifact.manifest,
        scriptPath: artifact.bundlePath,
        scriptBody: artifact.bundleBody,
        moduleFile,
    };
}
