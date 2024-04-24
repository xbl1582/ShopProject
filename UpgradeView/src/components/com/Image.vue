<template>
    <div style="height:100%;">
    <div class="demo-image__preview">
    <el-image
        style="width: 100%; height: 100%"
        :src="imagepath"
        :zoom-rate="1.2"
        :max-scale="7"
        :min-scale="0.2"
        :preview-src-list="srcList"
        :initial-index="4"
        fit="cover"
        />
        </div>
    </div>

</template>
  
<script lang="ts" >
// @ts-nocheck
import { defineComponent } from "vue";
//  import Tiff from 'tiff.js'
 import 'tiff.js';
//@ts-ignore
import axios from 'axios'
export default defineComponent({
    name: "myhelp",
    props: {

    },
    data() {
        return {
            fileList: [],
            imagepath:"",
            srcList:[]
        };
    },
    mounted() {


    },
    methods: {
        getImageUrl(url: string | URL) {
            return new URL(url, import.meta.url).href;
        
        },
     async  setimagepath(src :string){
           this.srcList=[];
        
        var _this=this; 
       await  axios.get(src, {
            responseType: 'blob'
            }).then(res => {
            // console.log('avatar has been download')
            // console.log(res)
            var reader = new FileReader()
            reader.onload = (e) => {
               
                _this.imagepath=e.target.result
         
                
            }
            reader.readAsDataURL(res.data)
            })
            this.srcList.push(src);
        },

    async setimagepath2(src:string){
            this.srcList=[];
        var _this=this;
        await  axios.get(src, {
                    responseType: 'blob'
                    }).then(res => {
                    res.data.arrayBuffer().then((arrayBuffer) => {
                        const tiff = new Tiff({
                        buffer: arrayBuffer,
                        });
                        //转成png格式的base64图片，将其用img标签展示即可
                        _this.imagepath=tiff.toDataURL("image/png");
                        _this.srcList.push( _this.imagepath);
                    })
                    })

                    // console.log('avatar has been download')
                    // console.log(res)
                    // var reader = new FileReader()
                    // reader.onload = (e) => {
                    
                    //     // _this.imagepath=e.target.result
                    //     // Tiff.initialize({TOTAL_MEMORY :50 * 1024 * 1024});
                    // let url = new Tiff({ buffer: e.target.result });
                    // _this.imagepath= url.toDataURL();
                        
                    // }
                    // reader.readAsDataURL(res.data)
                    // })

    }
     
    
    },
    watch: {


    },
    created() {

    },
    components: {
        Tiff
    }
});




</script>
  
  
  
  
<style scoped>

.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}
</style>