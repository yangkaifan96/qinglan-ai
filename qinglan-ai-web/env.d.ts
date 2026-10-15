/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly QL_PUBLIC_APP_TITLE: string
  readonly QL_PUBLIC_API_BASE_URL: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
