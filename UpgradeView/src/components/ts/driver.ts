// @ts-nocheck
import { defineComponent } from "vue";
import uploaddrivervue from "../uploaddrivervue.vue"
import { ElMessageBox, ElMessage } from 'element-plus'
//@ts-ignore
import axios from 'axios'
export default defineComponent({
    name: "vote",
    props: {

    },
    data() {
        return {
            activeName:'filelist',
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
                url: '/driver/driverlist',
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
                this.getTop();
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
                '是否删除文件' + row.drivername + '?',
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
                        url: '/driver/del',
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
         handleClick(tab: TabsPaneContext, event: Event){
            if(tab.props.name=='filelist'){
                    this.inittable();
            }
        },
        download(row){
            // console.log(row);
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
            // console.log(111)
            axios({
                method: 'put',
                url: '/driver/setMaxPriority',
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
                url: '/driver/maxPriority',
             
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
        uploaddrivervue,
        ElMessageBox
    }
});


