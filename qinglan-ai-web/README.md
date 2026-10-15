# 青岚前端

基于 Vue 3、TypeScript、Vite，保留 Vue Router 和 Pinia；当前仅提供基础首页，未接入业务功能。

## 本地开发

Node.js 版本要求：`^22.18.0 || >=24.12.0`，推荐使用 `.nvmrc` 中的 Node 24.19.0。使用 npm 管理依赖，并提交 `package-lock.json`。

```sh
npm ci
npm run dev
```

`dev` 命令使用 `vite --mode dev`，终端会显示实际访问地址（默认 `http://localhost:5173`）。修改环境文件后需重启开发服务。

## 环境配置

- `.env`：所有模式共用的非敏感配置，包含页面标题和 API 基础路径 `/api`。
- `.env.dev`：仅 dev 模式加载，代理目标为 `http://localhost:8176`。
- 以上两个文件允许提交 Git。个人覆盖配置放入 `.env.local` 或 `.env.dev.local`，由仓库根目录的 `.gitignore` 统一忽略。
- 项目变量统一以 `QL_` 开头；只有 `QL_PUBLIC_` 开头的变量会暴露给浏览器，禁止存放密钥、令牌或密码。

开发时，前端请求应使用 `import.meta.env.QL_PUBLIC_API_BASE_URL` 作为基础路径。Vite 将 `/api` 请求转发到后端并保留路径，例如 `/api/example` 转发为 `http://localhost:8176/api/example`。此路径仅用于说明转发规则，并非已有接口。

后端当前未提供 Controller 或 Actuator 健康检查接口，因此没有可用接口验证业务响应，首页也不会显示虚构的联通状态。待后端提供实际接口并启动后，再通过前端地址验证请求。

`npm run build` 使用 production 模式，读取 `.env`，不加载 `.env.dev`。Vite 的 `server.proxy` 仅用于开发，生产部署需由 Web 服务器配置 `/api` 反向代理；使用 Vue Router 的 history 模式时，也需将页面路由回退到 `index.html`。

## 检查与构建

```sh
npm run type-check
npm run lint
npm run format:check
npm run build
npm run preview
```

`build` 依次执行 TypeScript 检查和生产构建。`lint` 只检查，修复使用 `npm run lint:fix`；格式化使用 `npm run format`。代码规范统一使用 ESLint 和 Prettier，ESLint 通过 `eslint-config-prettier` 关闭格式规则，避免重复检查。

依赖目录、构建产物、检查缓存和本地环境覆盖文件由仓库根目录的 `.gitignore` 统一管理。
