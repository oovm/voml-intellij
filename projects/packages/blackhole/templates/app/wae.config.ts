import { defineConfig } from '@wae/wae';

export default defineConfig({
    deployTarget: 'cloudflare',
    frontend: {
        framework: 'none',
        entry: './src/client/main.ts',
    },
    server: {
        entry: './src/server/app.ts',
        adapter: 'cloudflare',
    },
    target: 'web',
    // cloudflare: {
    //     accountId: process.env.CLOUDFLARE_ACCOUNT_ID,
    //     apiToken: process.env.CLOUDFLARE_API_TOKEN,
    //     scriptName: 'my-app',
    //     bindings: [{ kind: 'kv_namespace', name: 'KV', namespaceId: '...' }],
    //     routes: [{ pattern: 'api.example.com/*' }],
    //     customDomains: [{ hostname: 'api.example.com', zoneId: '...' }],
    // },
    // product: {
    //     update: { github: "your-org/your-app" },
    // },
});
