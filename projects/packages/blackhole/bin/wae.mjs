#!/usr/bin/env node
/**
 * @wae/wae — 唯一项目 CLI。编排前端工具链（常用 Vite，可换）/ Cargo / 平台包；不内置 TSX 编译器或 Rust runtime。
 */
import { runCli } from '../dist/cli.js';

runCli(process.argv.slice(2)).catch((err) => {
    console.error('[wae]', err instanceof Error ? err.message : err);
    process.exitCode = 1;
});
