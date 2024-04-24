// @ts-nocheck
import { defineComponent } from "vue";
import uploadinfovue from "../uploadinfovue.vue"
import { ElMessageBox, ElMessage } from 'element-plus'
import my404vue from '../com/404vue.vue'
import imagevue from '../com/Image.vue'
import folderword from '../com/folderword.vue'
import folderpdf from '../com/folderpdf.vue'
import foldertxt from '../com/foldertxt.vue'
import folderxlsx from "../com/folderxlsx.vue"
//@ts-ignore
import axios from 'axios'
export default defineComponent({
    name: "vote",
    props: {

    },
    data() {
        return {
            activeName:'filelist',
             componentis:'my404vue',
              loading:false, 
            fileList: [],
           tableData:[
                ],
            searchobj: {
                searchvalue: "",
                page: 1,
                pagesize: 20
            },
            currentPage: 1,
            pageSize: 20,
            total: 100,
            istop:0
        };
    },
    mounted() {
       
            this.inittable();

    },
    methods: {
        getImageUrl(url: string | URL) {
            return new URL(url, import.meta.url).href;
        
        },
         inittable() {
            //初始化数据
            axios({
                method: 'get',
                url: '/info/infoslist',
                params: {
                    //@ts-ignore
                    searchvalue: this.searchobj.searchvalue,
                    //@ts-ignore
                    page: this.searchobj.page,
                   //@ts-ignore
                    pagesize: this.searchobj.pagesize

                }
            }).then((res: { data: { data: any; count: any; }; }) => {
                // console.log(res.data);
                //@ts-ignore
                this.tableData = res.data.data;
                //@ts-ignore
                this.total = res.data.count;
                this. getTop();
            }, (err: any) => {
                console.log(err);
            })
   





        },
        searchfun() {
            //@ts-ignore
            // console.log(this.searchobj.searchvalue)
            this.inittable();
        },
          handleSizeChange(val: number) {
            //@ts-ignore
            this.pagesize = val;
            //@ts-ignore
            this.searchobj.pagesize = this.pagesize;
            this.inittable();
            // console.log(`${val} items per page`)
        },
        handleCurrentChange(val: number) {
            //@ts-ignore
            this.currentPage = val;
            //@ts-ignore
            this.searchobj.page = this.currentPage;
            this.inittable();
            // console.log(`current page: ${val}`)
        },
          deleteRow(index: number, row: any) {

            ElMessageBox.confirm(
                '是否删除文件' + row.infoname + '?',
                '提示',
                {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    draggable: true,
                }
            )
                .then(() => {
                    axios({
                        method: 'delete',
                        url: '/info/del',
                        params: {
                            uuid: row.uuid

                        }
                    }).then((res: { data: { data: any; count: any; }; }) => {
                        // console.log(res.data);
                        //@ts-ignore
                        if (res.data.code == 1) {
                            ElMessage({
                                type: 'success',
                                message: '删除成功',
                            })
                            //@ts-ignore
                            this.tableData.splice(index, 1)
                            this.inittable();
                        }
                    }, (err: any) => {
                        ElMessage({
                            type: 'error',
                            message: '删除失败',
                        })
                        console.log(err);
                    })



                })
                .catch(() => {
                    // ElMessage({
                    //     type: 'info',
                    //     message: 'Delete canceled',
                    // })
                })

        },
        download(row){
            // console.log(row);
        },
        handleClick(tab: TabsPaneContext, event: Event){
            if(tab.props.name=='filelist'){
                    this.inittable();
            }
        // console.log(tab, event)
        },
        rowClick(row){
            // console.log(row);
            //判断是那种类型的文件，根据文件类型，选择具体的查看器
            switch (row.type) {
                case "jpg":{
                     //@ts-ignore
                     this.componentis='imagevue';
                    var _this=this;
                     //@ts-ignore
                    this.loading=true;
                    setTimeout( function(){
                       _this.$refs.child.setimagepath("/info/image?path="+row.infoname);
                        //@ts-ignore
                       _this.loading=false;
                        }, 1500)
        
                }
                
                    break;

                case "jpeg":{
                     //@ts-ignore
                     this.componentis='imagevue';
                    var _this=this;
                     //@ts-ignore
                    this.loading=true;
                    setTimeout( function(){
                       _this.$refs.child.setimagepath("/info/image?path="+row.infoname);
                        //@ts-ignore
                       _this.loading=false;
                        }, 1500)
        
                }
                
                    break;

                    
                case "png":{
                     //@ts-ignore
                     this.componentis='imagevue';
                    var _this=this;
                     //@ts-ignore
                    this.loading=true;
                    setTimeout( function(){
                       _this.$refs.child.setimagepath("/info/image?path="+row.infoname);
                        //@ts-ignore
                       _this.loading=false;
                        }, 1500)
        
                }
                
                    break;
                case "tif":{
                     //@ts-ignore
                     this.componentis='imagevue';
                    var _this=this;
                     //@ts-ignore
                    this.loading=true;
                    setTimeout( function(){
                       _this.$refs.child.setimagepath2("/info/image?path="+row.infoname);
                        //@ts-ignore
                       _this.loading=false;
                        }, 1500)
        
                }
                    break;

                 case "tiff":{
                     //@ts-ignore
                     this.componentis='imagevue';
                    var _this=this;
                     //@ts-ignore
                    this.loading=true;
                    setTimeout( function(){
                       _this.$refs.child.setimagepath2("/info/image?path="+row.infoname);
                        //@ts-ignore
                       _this.loading=false;
                        }, 1500)
        
                }
                break;
                case "doc":{

                }
                break;
                case "docx":{
                     //@ts-ignore
                     this.componentis='folderword';
                    var _this=this;
                     //@ts-ignore
                    this.loading=true;
                    setTimeout( function(){
                       _this.$refs.child.setwordpath("/info/file?path="+row.infoname);
                        //@ts-ignore
                       _this.loading=false;
                        }, 1500)
                        
                }
                break;
                case "pdf":{
                     //@ts-ignore
                    this.componentis='folderpdf';
                    var _this=this;
                     //@ts-ignore
                    this.loading=true;
                    setTimeout( function(){
                       _this.$refs.child.setwordpath("/info/file?path="+row.infoname);
                        //@ts-ignore
                       _this.loading=false;
                        }, 1500)
                }
                break;
                case "xlsx":{
                     //@ts-ignore
                    this.componentis='folderxlsx';
                    var _this=this;
                     //@ts-ignore
                    this.loading=true;
                    setTimeout( function(){
                       _this.$refs.child.setwordpath("/info/file?path="+row.infoname);
                        //@ts-ignore
                       _this.loading=false;
                        }, 1500)
                }
                break;
                case "txt":{
                    //@ts-ignore
                   this.componentis='foldertxt';
                   // console.log(row);
                   var _this=this;
                    //@ts-ignore
                   this.loading=true;
                   setTimeout( function(){
                      _this.$refs.child.settxtpath("/info/file?path="+row.infoname);
                       //@ts-ignore
                      _this.loading=false;
                       }, 1500)
               }
               break;
                case "ofd":{

                }
                break;


                default:
                    break;
            }

        },
        dateFormat(longTypeDate: number) {
            var dateType = ""; 
            var date = new Date();  
            date.setTime(longTypeDate);  
            dateType += date.getFullYear();   //年  
            dateType += "-" + this.getMonth(date); //月   
            dateType += "-" + this.getDay(date);   //日  
            dateType += " " + this.getHours(date);   //时  
            dateType += ":" + this.getMinutes(date);   //分 
            dateType += ":" + this.getSeconds(date);   //秒  
            return dateType;
        },
        
        //返回 01-12 的月份值  
        getMonth(date){  
        var month = "";  
        month = date.getMonth() + 1; //getMonth()得到的月份是0-11  
        if(month<10){  
        month = "0" + month;  
        }  
        return month;  
        } , 
        //返回01-30的日期  
        getDay(date){  
        var day = "";  
        day = date.getDate();  
        if(day<10){  
        day = "0" + day;  
        }  
        return day;  
        } ,
        //小时 
        getHours(date){ 
        var hours = ""; 
        hours = date.getHours(); 
        if(hours<10){  
        hours = "0" + hours;  
        }  
        return hours;  
        } ,
        //分 
        getMinutes(date){ 
        var minute = ""; 
        minute = date.getMinutes(); 
        if(minute<10){  
        minute = "0" + minute;  
        }  
        return minute;  
        } ,
        //秒 
        getSeconds(date){ 
        var second = ""; 
        second = date.getSeconds(); 
        if(second<10){  
        second = "0" + second;  
        }  
        return second;  
        },
        setTop(index:number,item:any){
            
            axios({
                method: 'put',
                url: '/info/setMaxPriority',
                data:item
            }).then((res: { data: { data: any; count: any; }; }) => {
                
                if(res.data.code==1){
                  
                    this.inittable();
                }else{
                    ElMessage({
                        type: 'error',
                        message: '出错了，请稍后重试',
                    })
                }
                
            }, (err: any) => {
                console.log(err);
            })
        },
        getTop(){
            axios({
                method: 'get',
                url: '/info/maxPriority',
             
            }).then((res: { data: { data: any; count: any; }; }) => {
              
                if(res.data.code==1){
                     this.istop = res.data.count;
                }else{
                    ElMessage({
                        type: 'error',
                        message: '出错了，请稍后重试',
                    })
                }
                
            }, (err: any) => {
                console.log(err);
            })




        }

    
    },
    watch: {


    },
    created() {

    },
    components: {
        uploadinfovue,
               ElMessageBox,
                 imagevue,
        my404vue,
        folderword,
        folderpdf,
        folderxlsx,
        foldertxt
    }
});

