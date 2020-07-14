import { serve } from '@wae/server/node';
import { app } from './app.js';

await serve(app);
