<template>
  <div class="box" ref="box">
    <el-row style="width: 100%; height:85vh; background-color: rgba(200, 200, 200, 0.2)" class="docxbox">
      <vue-office-docx
        :src="wordpath"
        style="width: 100%; height: 100%;"
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
// import MyEditor from '../../com/MyEditor.vue'
//引入VueOfficeDocx组件
import VueOfficeDocx from "@vue-office/docx";
//引入相关样式
import "@vue-office/docx/lib/index.css";
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
      wordpath:'',
      //配置项
      docx: "http://static.shanhuxueyuan.com/test6.docx",
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
            // console.log('avatar has been download')
            // console.log(res)
            var reader = new FileReader()
            reader.onload = (e) => {
               
                _this.wordpath=e.target.result
         
                
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
    VueOfficeDocx,
  },
});
</script>
<style scoped>
@import url("./folderword.css");
</style>
