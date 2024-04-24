<template>
    <div class="box" ref="box" style="width: 100%;">
      <el-row style=" height:85vh; background-color: rgba(200, 200, 200, 0.2)" >
        <vue-office-excel
        :src="excelpath"
        style="width:100%;height: 100vh;"
        @rendered="renderedHandler"
        @error="errorHandler"
    />
      </el-row>
    </div>
  </template>
  <script lang="ts">
  // @ts-nocheck
  import { defineComponent } from "vue";
  import { defaultOptions, renderAsync } from "docx-preview";
  import VueOfficeExcel from '@vue-office/excel'
//引入相关样式
import '@vue-office/excel/lib/index.css'
  //注意引入的写法
  import axios from 'axios'
  export default defineComponent({
    name: "FolderWord",
    props: {
     
    },
    created() {},
    data() {
      return {
        fileobj:'',
          // Excel文档数据
          excelpath:'',
        //配置项
      };
    },
    watch: {},
    mounted() {
     
    },
    methods: {
      getImageUrl(url: string | URL) {
        return new URL(url, import.meta.url).href;
      },
      async  setwordpath(src :string){
             this.srcList=[];
          
          var _this=this; 
         await  axios.get(src, {
              responseType: 'blob'
              }).then(res => {
            //   console.log('avatar has been download')
            //   console.log(res)
              var reader = new FileReader()
              reader.onload = (e) => {
                 
                  _this.excelpath=e.target.result
           
                  
              }
              reader.readAsDataURL(res.data)
              })
              this.srcList.push(src);
          },
  
      renderedHandler() {
        console.log("渲染完成");
      },
      errorHandler(e) {
        console.log(e);
        console.log("渲染失败");
      },
    },
    beforeDestroy() {},

    components: {
        VueOfficeExcel
    },
  });
  </script>
  <style scoped>
@import url("./folderxlsx.css");
  </style>
  