# `@wae/wae`

The sole project CLI and `defineConfig`. Orchestrates frontend toolchain / Cargo / platform packages; does **not** embed
a TSX compiler and does **not** put Rust runtime into the frontend.

CLI parsing lives in [`@wae/commander`](../commander/readme.md) (Commander.js). This package injects `run` / `build`
handlers — no duplicate flag parsing.

## Relationship with Vite

Like common Tauri templates: **Vite is typical but not hard-wired to Vite**.

| Config                                   | Behavior                                                                                 |
|------------------------------------------|------------------------------------------------------------------------------------------|
| `frontend.bundler: "vite"` (**default**) | `wae run` / `wae dev` starts Vite; `vite` is an optional peer                            |
| `frontend.bundler: "custom"`             | Does not start any bundler; use Webpack / Rspack / Parcel etc. and set `frontend.devUrl` |

When swapping toolchains, change project scripts and `bundler` / `devUrl`, not `@wae/client` or adapters.

## Install

```bash
pnpm add -D @wae/wae@0.0.0
# Common path also installs Vite
pnpm add -D vite@^7
```

Installing pulls `@wae/wae-*` by platform (`optionalDependencies`). Binary name: `wae`.

## CLI

```text
wae create <name> [--server node|deno|cloudflare]
wae dev [--platform <id>] [--port <n>] [--host <addr>] [--open|--no-open]
wae build [--platform <id>]
wae publish [--platform <id>] [--out-dir <dir>]
wae preview
wae run [--platform <id>] [--port <n>] [--host <addr>] [--open|--no-open]
wae check
wae test
wae generate [types]
wae help
```

### Wired: `run` / `dev`

```bash
pnpm exec wae run
pnpm exec wae run --platform web --port 5173
```

Behavior:

1. Load `wae.config.*` with esbuild (normalized via `defineConfig`).
2. Resolve `platform` / `target` to client platform id (default `web`).
3. **`web` + `bundler: "vite"`**: Start Vite. Uses existing `vite.config.*` if present; otherwise injects official
   plugins from `frontend.framework`.
4. **`web` + `bundler: "custom"`**: Does not start Vite; prints `devUrl` hint.
5. **Other platforms**: Call `platform.run()` on the matching `@wae/wae-*` (mostly no-op in 0.0.0).

`dev` and `run` share the same implementation today.

### Wired: `build`

Produces the **shipped product** under `dist/<platform>/`:

```text
dist/web/
  frontend/                 # Vite build output
  server/worker.mjs         # when deployTarget=cloudflare (node.mjs / deno.mjs otherwise)
  wrangler.toml             # optional local wrangler dev interop (not WAE publish truth)
  wae-product.json          # name, version, server.entry, update.github, nativePath
```

`preview` / `check` / `test` / `generate` are not wired yet. `create` materializes the app template with a single deploy
target host.

### Wired: `publish`

After `wae build`, `wae publish` consumes `dist/<platform>/wae-product.json` and the bundled `server/*.mjs`:

| `deployTarget`  | Behavior                                                                                                                         |
|-----------------|----------------------------------------------------------------------------------------------------------------------------------|
| `cloudflare`    | Upload via Cloudflare Workers Scripts API (`publishWorkerBundle`), optional routes/domains/bindings from `wae.config#cloudflare` |
| `node` / `deno` | Write `dist/<platform>/publish/server-publish.json` with the process start command (`node server/node.mjs` / `deno run`)         |

Credentials for Cloudflare: `CLOUDFLARE_ACCOUNT_ID` / `CLOUDFLARE_API_TOKEN` or `cloudflare.accountId` /
`cloudflare.apiToken` in config. No `wrangler deploy` wrapper.

### Wired: `create`

```bash
wae create my-app --server node
wae create my-app --server deno
wae create my-app --server cloudflare   # default
```

Writes `deployTarget` and matching `server.adapter` in `wae.config.ts`, sets **one** host dependency (`@wae/server` or
`@wae/serverless`), and includes only the matching entry file (`src/server/node.ts`, `deno.ts`, or `worker.ts`). Shared
app logic stays in `src/server/app.ts`.

Debug in repo:

```bash
pnpm --filter @wae/wae run build
pnpm --filter @wae-example/integration-vue-app exec wae run --port 5173
```

## `defineConfig`

```ts
import { defineConfig } from "@wae/wae";

export default defineConfig({
  deployTarget: "node",
  frontend: {
    framework: "vue", // vue | react | svelte | solid | none
    // adapter: vue(),
    entry: "./src/main.ts",
    bundler: "vite", // default; use "custom" + devUrl when swapping toolchains
    // bundler: "custom",
    // devUrl: "http://127.0.0.1:8080",
  },
  server: {
    entry: "./server/index.ts",
    adapter: "node",
  },
  target: "web",
  platform: {
    client: "web",
    server: "node",
  },
  product: {
    update: { github: "your-org/your-app" },
  },
});
```

### Product updater (built app)

Configure `product.update.github` to your **app** repo. Optional `channel` (`stable` | `beta` | tag) and
`downloadPolicy` (`checkOnly` | `downloadIfAvailable` | `downloadAndApply`). After `wae build`, ship
`wae-product.json` with the tree. Runtime (desktop):

```ts
import {
  loadProductManifest,
  checkProductUpdateFromManifest,
  downloadProductUpdateFromManifest,
  applyProductUpdateFromManifest,
} from "@wae/wae";

const root = "/path/to/dist/win32-x64";
const manifest = loadProductManifest(root);

// Explicit: check → user confirms → download → apply
const check = checkProductUpdateFromManifest(manifest, root);
if (!check.upToDate) {
  const staged = downloadProductUpdateFromManifest(manifest, root);
  applyProductUpdateFromManifest(manifest, root, undefined, staged.stagedNativePath);
}
```

Upgrade `@wae/wae` via npm — that is the toolchain, not the product.

Normalization: `framework` defaults to `"none"`, `bundler` to `"vite"`, `target` to `"web"`.

## When things fail, check

1. Is there a `wae.config.ts` in the current directory?
2. With `bundler: "vite"`, is `vite` installed (and the framework plugin)?
3. Is `index.html` missing (Vite root entry)?
4. With `bundler: "custom"`, did you start the toolchain and is `devUrl` reachable?
5. For non-web platforms, did optional deps fail to install due to `os`/`cpu`?

## Related

- Application area: [`../readme.md`](../readme.md)
- Runnable examples: [`vue-app`](../../examples/integration/vue-app/) · [
  `react-app`](../../examples/integration/react-app/)
- Platforms: [`../readme.md`](../readme.md)
