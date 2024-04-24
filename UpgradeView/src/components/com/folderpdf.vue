<template>
  <div class="box" ref="box">
    <el-row style="width: 100%; height: 100%; background:gray;">
      <!-- <iframe :src="url" width="100%" height="97%" frameborder="0"></iframe> -->
      <!-- <div class="pdf-container">
          <canvas id="pdf-canvas"></canvas>
      </div> -->
       <vue-office-pdf :src="url" class="pdf-calss"  />
    </el-row>
  </div>
</template>
<script lang="ts">
// @ts-nocheck
import { defineComponent } from "vue";
import axios from 'axios'
//注意引入的写法
import VueOfficePdf from '@vue-office/pdf'
// import '@vue-office/pdf/lib/index.css'
export default defineComponent({
  name: "Folderpdf",
  props: {
    componentmessage: Object,
  },
  created() {},
  data() {
    return {
      //配置项
      viwerurl: "/static/pdfjs/web/viewer2.html",
      fileobj:{

      },
      filename:"js.pdf",
      pdfPages: 0, // pdf文件的页数
      //pdf文件的链接
      url: "",
      pdfScale: 1.0, // 缩放比例
    };
  },

  mounted() {
  },
  methods: {
    getImageUrl(url: string | URL) {
      return new URL(url, import.meta.url).href;
    },
    //
  async  setwordpath(src :string){
           this.srcList=[];
        
        var _this=this; 
         
         this.srcList.push(src);
    
          //   //  
       await  axios.get(src, {
            responseType: 'blob'
            }).then(res => {
            // console.log('avatar has been download')
            // console.log(res)
             let blob = new Blob([res.data], {
                    type: 'application/pdf;chartset=UTF-8'
                })
                let fileURL= URL.createObjectURL(blob)
          _this.url=fileURL;
            })
            
        },
  },
  beforeDestroy() {},
  watch: {},
  components: {
     VueOfficePdf
  },
});
</script>
<style scoped>
@import url("./folderpdf.css");
:deep(.vue-office-pdf-wrapper){
  height:200px;
  background:rgba(0,0,0,0)!important;
}
</style>
