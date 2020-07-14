import { createClient } from '@wae/client';

const client = createClient({
    server: { baseUrl: '/api' },
});

void client;
