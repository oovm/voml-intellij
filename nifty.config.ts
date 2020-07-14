// WAE workspace Nifty configuration (format + hygiene checks).
import { defineConfig } from '@doki-land/nifty';

export default defineConfig({
    format: {
        // `@doki-land/nifty@0.0.6` preset id is `npm-tools` (renamed to `nifty` in unreleased upstream).
        preset: 'npm-tools',
        includes: ['scripts/**', 'projects/packages/**', 'projects/examples/**', 'package.json', 'nifty.config.ts'],
        excludes: ['**/dist/**', '**/fixtures/**'],
        rust: true,
        javascript: true,
        // Inline `style` lands in a future nifty release — 0.0.6 uses oxc defaults (4-space, single quotes, width 144).
    },
    changelog: {
        repo: 'oovm/wae',
    },
});
