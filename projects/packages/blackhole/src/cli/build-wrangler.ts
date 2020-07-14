/** Optional `wrangler.toml` for local wrangler dev interop — not the WAE publish contract. */

import fs from 'node:fs';
import path from 'node:path';

export type WranglerConfigInput = {
    name: string;
    main: string;
    compatibilityDate?: string;
};

const DEFAULT_COMPATIBILITY_DATE = '2024-09-01';

export function renderWranglerToml(input: WranglerConfigInput): string {
    const compatibilityDate = input.compatibilityDate ?? DEFAULT_COMPATIBILITY_DATE;
    const main = input.main.replaceAll('\\', '/');
    return `name = "${input.name}"\nmain = "${main}"\ncompatibility_date = "${compatibilityDate}"\n`;
}

export function writeWranglerToml(outDir: string, input: WranglerConfigInput): string {
    const wranglerPath = path.join(outDir, 'wrangler.toml');
    fs.writeFileSync(wranglerPath, renderWranglerToml(input), 'utf8');
    return wranglerPath;
}
