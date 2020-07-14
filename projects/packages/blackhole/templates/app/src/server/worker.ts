import { worker } from '@wae/serverless/cloudflare';
import { app } from './app';

export default worker(app);
