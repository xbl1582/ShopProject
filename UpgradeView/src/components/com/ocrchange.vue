
  <template>
    <div style="height:100%; text-align:left;">
        <el-row style="height:100%;">
        <el-col :span="12" style="border-right:solid 1px rgba(200,200,200,0.8);padding :10px;height:90%;">
            <el-upload action="#" list-type="picture-card" :auto-upload="false" >
                    <el-icon><Plus /></el-icon>

                    <template #file="{ file }">
                    <div>
                        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
                        <span class="el-upload-list__item-actions">
                        <span
                            class="el-upload-list__item-preview"
                            @click="handlePictureCardPreview(file)"
                        >
                            <el-icon><zoom-in /></el-icon>
                        </span>
                        <span
                            v-if="!disabled"
                            class="el-upload-list__item-delete"
                            @click="handleDownload(file)"
                        >
                            <el-icon><Download /></el-icon>
                        </span>
                        <span
                            v-if="!disabled"
                            class="el-upload-list__item-delete"
                            @click="handleRemove(file)"
                        >
                            <el-icon><Delete /></el-icon>
                        </span>
                        </span>
                    </div>
                    </template>
                </el-upload>
          </el-col>
        <el-col :span="12" style="padding :10px;height:90%; overflow-x:hidden;overflow-y:auto;" v-loading="isloading">
            <div v-if="results.length>0">
           
            <el-card v-for="(item,index) in results" style="height:200px;margin-bottom:5px;">
                 <template #header>
                    <div class="card-header">
                        <span>识别结果{{index+1}}</span>
                    </div>
                    </template>
            </el-card>
            </div>
            <div v-else >
                <my404vue ></my404vue>
            
            </div>
        </el-col>
        <el-col :span="24" style="margin-top:40px; text-align:center;">
           
            <el-button type="danger" style="margin-left:20px;width:100px;height:50px;" @click="shibie()">识别</el-button>
           
        </el-col>
        </el-row>

    </div>
</template>
  
<script lang="ts" >
// @ts-nocheck
import { defineComponent } from "vue";
import my404vue from "./404vue.vue"
//@ts-ignore
import axios from 'axios'
export default defineComponent({
    name: "filechange",
    props: {

    },
    data() {
        return {
            dialogImageUrl:'',
             dialogVisible:false,
             isloading:false,
            fileList: [
                 

            ],
            value:'',
            results:[
               
            ]
        
        };
    },
    mounted() {


    },
    methods: {
        getImageUrl(url: string | URL) {
            return new URL(url, import.meta.url).href;
        
        },
        handleRemove(uploadFile, uploadFiles){
            console.log(uploadFile, uploadFiles)
        },
        handlePictureCardPreview(uploadFile) {
            this.dialogImageUrl=uploadFile.url
            this.dialogVisible= true
        },
        shibie(){
             this.isloading=true;
             clearTimeout(this.noloading(),1000)


        },
        noloading(){
            this.isloading=false;
        }
    
    },
    watch: {


    },
    created() {

    },
    components: {
        my404vue
    }
});




</script>
  
  
  
  
<style scoped></style>