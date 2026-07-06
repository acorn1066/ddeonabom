import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
	base: '/admin/',
	plugins: [
    react(),
    tailwindcss(),
  ],
  server: {
    proxy: {
      '/react': {
        target: 'http://localhost:8080',
        changeOrigin: true,
         rewrite: path => path.replace(/^\/react/, '')
      }
      }
    }
  })