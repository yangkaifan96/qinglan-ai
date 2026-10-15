import { fileURLToPath, URL } from 'node:url'

import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, fileURLToPath(new URL('.', import.meta.url)), 'QL_')

  return {
    plugins: [vue()],
    envPrefix: 'QL_PUBLIC_',
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url)),
      },
    },
    server: {
      proxy: {
        [env.QL_PUBLIC_API_BASE_URL || '/api']: {
          target: env.QL_API_PROXY_TARGET || 'http://localhost:8176',
          changeOrigin: true,
        },
      },
    },
  }
})
