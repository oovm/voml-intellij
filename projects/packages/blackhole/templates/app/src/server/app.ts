import { createApp, route } from '@wae/core';

export const app = createApp({
    routes: [route('GET', '/api/health', (ctx) => ctx.json({ ok: true }))],
});
