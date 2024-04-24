//@ts-nocheck
import { defineComponent } from "vue";
import { ElLoading} from 'element-plus';

import axios from 'axios'
export default defineComponent({
    name: "filelist",
    props: {

    },
    data() {
        return {
            fileList: [],
            successupload:0,
            loading:null,
        };
    },
    watch:{

    },
    mounted() {


    },
    methods: {
        getImageUrl(url: string | URL) {
            return new URL(url, import.meta.url).href;
        },
        submitUpload() {
            if(this.fileList.length==0){
              
                ElMessage({
                    type: "error",
                    message: "文件为空",
                });
               
            }else{
            this.submitLoading = true
            // console.log(this.$refs.upload.fileList);
            //触发 el-upload上传
           
            this.openFullScreen2();
            this.$refs.upload.submit(); //如果:http-request=""指定了回调函数,则会覆盖默认的上传,会流入到http-request的回调中
       
            }
        },
        handleChange(file, fileList){
            this.fileList=fileList;
            //console.log(fileList.length);
        },

        handleRemove(file: any, fileList: any) {
            //console.log(file, fileList);
        },
        handleprogress(file: any){
            //console.log(file);
        },
        handlePreview(file: any) {
            //console.log(file);
        },
        //@ts-ignore
        beforeRemove(file: { name: any; }, fileList: any) {
            //@ts-ignore
            return this.$confirm(`确定移除 ${file.name}？`);
        },
        /**
      * 文件提交回调
      * @param data
      */
        myUploadFile(data: { file: any; }) {
            let file = data.file;
            let formData = new FormData();
            formData.append("file", file);
            // console.log(formData)
            axios.post('/vote/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(response => {
                // console.log('上传成功', response.data);
                if (response.data.code == 1) {
                    //@ts-ignore
                    // this.$message.success("上传成功")
                    //@ts-ignore
                     //@ts-ignore
                     this.successupload+=1;
                     var ul=document.getElementsByClassName("el-upload-list");
                     var linum=ul[0].getElementsByTagName("li").length;
                    if(this.successupload==linum){
                        //@ts-ignore
                        this.loading.close();
                      
                          //@ts-ignore
                        this.$refs.upload.clearFiles();
                            //@ts-ignore
                        this.$message.success("上传成功");
                        this.successupload=0;
                        
                    }
                     
            
                } else {
                    //@ts-ignore
                    this.$message.error(response.data.msg);

                }
            }).catch(error => {
                //@ts-ignore
                this.$message.error("上传失败")
                this.loading.close();
                console.error('上传失败', error);
            });
        },
            openFullScreen2(){
                //@ts-ignore
           this.loading = ElLoading.service({
                lock: true,
                text: '上传中',
                background: 'rgba(0, 0, 0, 0.7)',
            })
        }
    },

    created() {

    },
    components: {
        ElLoading
    }
});

