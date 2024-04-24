import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()
  ],
  base:"/",
  server:{
    proxy:{
      "/api": {
        //target是代理的目标路径192.168.10.22  
        target: "http://localhost:9988/",
        changeOrigin: true, //必须要开启跨域
        //pathRewrite重写请求的路径,实际请求的路径没有代理标识douyu,需要把斗鱼重置为空字符串
        rewrite: (path) => path.replace(/\/api/, ""), // 路径重写
      },
    }
  }
})
