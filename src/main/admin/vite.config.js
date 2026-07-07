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
        target: 'http://3.34.10.120/',
        changeOrigin: true,
         rewrite: path => path.replace(/^\/react/, '')
      }
      }
    }
  })