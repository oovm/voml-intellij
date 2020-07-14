import { serve } from '@wae/server/deno';
import { app } from './app.js';

export default { fetch: serve(app) };
