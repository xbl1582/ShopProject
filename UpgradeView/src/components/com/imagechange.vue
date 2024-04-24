
  <template>
    <div style="height:100%; text-align:left;">
        <el-row style="height:100%;">
          <el-col :span="24" style=" height:90%;padding :10px;">
                <el-upload
                    v-model:file-list="fileList"
                    action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                    list-type="picture-card"
                    :on-preview="handlePictureCardPreview"
                    :on-remove="handleRemove"
                >
                    <el-icon><Plus /></el-icon>
                </el-upload>

                <el-dialog v-model="dialogVisible">
                    <img w-full :src="dialogImageUrl" alt="Preview Image" />
                </el-dialog>
          </el-col>
           <el-col :span="24" style="margin-top:20px; text-align:center;">
           
            <el-button type="danger" style="margin-left:20px;width:100px;height:50px;">转换</el-button>
           
           </el-col>
        </el-row>

    </div>
</template>
  
<script lang="ts" >
// @ts-nocheck
import { defineComponent } from "vue";
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
            fileList: [
                    

            ],
            value:'',
        
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
        }
    
    },
    watch: {


    },
    created() {

    },
    components: {

    }
});




</script>
  
  
  
  
<style scoped></style>