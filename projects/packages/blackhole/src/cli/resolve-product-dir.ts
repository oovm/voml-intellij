/** Resolve `wae build` product output directory for the active platform. */

import path from 'node:path';
import type { WaePublishOptions } from '@wae/commander';
import type { WaeConfig } from '../index.js';
import { resolvePlatformId } from './platform.js';

export function resolveProductDir(cwd: string, options: Pick<WaePublishOptions, 'platform' | 'outDir'>, config: WaeConfig): string {
    const platformId = resolvePlatformId(options, config);
    const baseOut = options.outDir ?? config.product?.outDir ?? 'dist';
    return path.resolve(cwd, baseOut, platformId);
}
