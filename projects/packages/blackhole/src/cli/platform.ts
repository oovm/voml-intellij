/** Shared platform resolution for `wae run` / `wae build`. */

import type { WaeBuildOptions, WaeRunOptions } from '@wae/commander';
import type { ClientPlatformId, WaeConfig } from '../index.js';

export type PlatformFlags = Pick<WaeRunOptions, 'platform'> | Pick<WaeBuildOptions, 'platform' | 'outDir'>;

export function hostDesktopPlatform(): ClientPlatformId {
    const { platform, arch } = process;
    if (platform === 'win32') return arch === 'arm64' ? 'win32-arm64' : 'win32-x64';
    if (platform === 'darwin') return arch === 'arm64' ? 'darwin-arm64' : 'darwin-x64';
    if (platform === 'linux') return arch === 'arm64' ? 'linux-arm64' : 'linux-x64';
    return 'win32-x64';
}

export function resolvePlatformId(flags: PlatformFlags, config: WaeConfig): ClientPlatformId {
    if (flags.platform) return flags.platform as ClientPlatformId;
    if (config.platform?.client) return config.platform.client;
    if (config.target === 'desktop') return hostDesktopPlatform();
    if (config.target === 'mobile') {
        return process.platform === 'darwin' ? 'ios-arm64' : 'android-arm64';
    }
    return 'web';
}

export function isNativeShellPlatform(id: ClientPlatformId): boolean {
    return id !== 'web' && id !== 'unknown-wasm32';
}

export function platformPackageName(id: ClientPlatformId): string {
    return `@wae/wae-${id}`;
}
