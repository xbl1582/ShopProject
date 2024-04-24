// vite.config.ts
import { defineConfig } from "file:///D:/MyWorkSpace/KSTUpgrade/KSTUpgradeview/vue/node_modules/.store/vite@4.5.1/node_modules/vite/dist/node/index.js";
import vue from "file:///D:/MyWorkSpace/KSTUpgrade/KSTUpgradeview/vue/node_modules/.store/@vitejs+plugin-vue@4.5.2/node_modules/@vitejs/plugin-vue/dist/index.mjs";
var vite_config_default = defineConfig({
  plugins: [
    vue()
  ],
  base: "/",
  server: {
    proxy: {
      "/api": {
        //target是代理的目标路径192.168.10.22  
        target: "http://localhost:9988/",
        changeOrigin: true,
        //必须要开启跨域
        //pathRewrite重写请求的路径,实际请求的路径没有代理标识douyu,需要把斗鱼重置为空字符串
        rewrite: (path) => path.replace(/\/api/, "")
        // 路径重写
      }
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxNeVdvcmtTcGFjZVxcXFxLU1RVcGdyYWRlXFxcXEtTVFVwZ3JhZGV2aWV3XFxcXHZ1ZVwiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRDpcXFxcTXlXb3JrU3BhY2VcXFxcS1NUVXBncmFkZVxcXFxLU1RVcGdyYWRldmlld1xcXFx2dWVcXFxcdml0ZS5jb25maWcudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0Q6L015V29ya1NwYWNlL0tTVFVwZ3JhZGUvS1NUVXBncmFkZXZpZXcvdnVlL3ZpdGUuY29uZmlnLnRzXCI7aW1wb3J0IHsgZGVmaW5lQ29uZmlnIH0gZnJvbSAndml0ZSdcbmltcG9ydCB2dWUgZnJvbSAnQHZpdGVqcy9wbHVnaW4tdnVlJ1xuLy8gaHR0cHM6Ly92aXRlanMuZGV2L2NvbmZpZy9cbmV4cG9ydCBkZWZhdWx0IGRlZmluZUNvbmZpZyh7XG4gIHBsdWdpbnM6IFt2dWUoKVxuICBdLFxuICBiYXNlOlwiL1wiLFxuICBzZXJ2ZXI6e1xuICAgIHByb3h5OntcbiAgICAgIFwiL2FwaVwiOiB7XG4gICAgICAgIC8vdGFyZ2V0XHU2NjJGXHU0RUUzXHU3NDA2XHU3Njg0XHU3NkVFXHU2ODA3XHU4REVGXHU1Rjg0MTkyLjE2OC4xMC4yMiAgXG4gICAgICAgIHRhcmdldDogXCJodHRwOi8vbG9jYWxob3N0Ojk5ODgvXCIsXG4gICAgICAgIGNoYW5nZU9yaWdpbjogdHJ1ZSwgLy9cdTVGQzVcdTk4N0JcdTg5ODFcdTVGMDBcdTU0MkZcdThERThcdTU3REZcbiAgICAgICAgLy9wYXRoUmV3cml0ZVx1OTFDRFx1NTE5OVx1OEJGN1x1NkM0Mlx1NzY4NFx1OERFRlx1NUY4NCxcdTVCOUVcdTk2NDVcdThCRjdcdTZDNDJcdTc2ODRcdThERUZcdTVGODRcdTZDQTFcdTY3MDlcdTRFRTNcdTc0MDZcdTY4MDdcdThCQzZkb3V5dSxcdTk3MDBcdTg5ODFcdTYyOEFcdTY1OTdcdTlDN0NcdTkxQ0RcdTdGNkVcdTRFM0FcdTdBN0FcdTVCNTdcdTdCMjZcdTRFMzJcbiAgICAgICAgcmV3cml0ZTogKHBhdGgpID0+IHBhdGgucmVwbGFjZSgvXFwvYXBpLywgXCJcIiksIC8vIFx1OERFRlx1NUY4NFx1OTFDRFx1NTE5OVxuICAgICAgfSxcbiAgICB9XG4gIH1cbn0pXG4iXSwKICAibWFwcGluZ3MiOiAiO0FBQWdVLFNBQVMsb0JBQW9CO0FBQzdWLE9BQU8sU0FBUztBQUVoQixJQUFPLHNCQUFRLGFBQWE7QUFBQSxFQUMxQixTQUFTO0FBQUEsSUFBQyxJQUFJO0FBQUEsRUFDZDtBQUFBLEVBQ0EsTUFBSztBQUFBLEVBQ0wsUUFBTztBQUFBLElBQ0wsT0FBTTtBQUFBLE1BQ0osUUFBUTtBQUFBO0FBQUEsUUFFTixRQUFRO0FBQUEsUUFDUixjQUFjO0FBQUE7QUFBQTtBQUFBLFFBRWQsU0FBUyxDQUFDLFNBQVMsS0FBSyxRQUFRLFNBQVMsRUFBRTtBQUFBO0FBQUEsTUFDN0M7QUFBQSxJQUNGO0FBQUEsRUFDRjtBQUNGLENBQUM7IiwKICAibmFtZXMiOiBbXQp9Cg==
