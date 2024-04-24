<template>
  <el-input type="textarea" style="height:100%;" placeholder="" v-model="txtPre"> </el-input>
</template>
<script lang="ts">
// @ts-nocheck
import { defineComponent } from "vue";
//注意引入的写法
import axios from "axios";
export default defineComponent({
  name: "FolderWord",
  props: {},
  created() {},
  data() {
    return {
      fileobj: "",
      wordpath: "",
      txtPre: "",
      //配置项
    };
  },
  watch: {},
  mounted() {},
  methods: {
    getImageUrl(url: string | URL) {
      return new URL(url, import.meta.url).href;
    },
    async settxtpath(src: string) {
      console.log(src);
      this.srcList = [];

      var _this = this;

      this.srcList.push(src);

      //   //
      await axios
        .get(src, {
          responseType: "blob",
        })
        .then((res) => {
          var reader = new FileReader();
          reader.onload = (e) => {
            console.log(e.target);
            _this.txtPre = e.target.result;
          };
          reader.readAsText(res.data);
        });
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

  },
});
</script>
<style scoped>
:deep(.el-textarea__inner){
  height:92%;
}
</style>
