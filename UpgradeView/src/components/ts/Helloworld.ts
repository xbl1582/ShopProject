//@ts-nocheck
import { defineComponent } from "vue";
import { ElNotification , ElMessageBox} from 'element-plus';
import uploadvue from "../uploadvue.vue"
import fileList from "../fileList.vue"
import LogAnlay from "../LogAnlay.vue";
import setup from "../setup.vue";
import myhelp from "../help.vue";
import morevue from "../more.vue";
import driver from '../driver.vue';
import vote from '../vote.vue'
import info from '../info.vue'
import empower from '../empower.vue'
import my404vue from '../com/404vue.vue'
import axios from 'axios'
export default defineComponent({
    name: "Titlecontrol",
    data() {
        return {
            activename: 'fileList',
            isCollapse: true,
            searchobj: {
                searchvalue: ''
            },
            filelist: [],
            filelistobj:{
                page:1,
                pageSize:10,
                total:0
            },
            infolist: [],
            infolistobj:{
                page:1,
                pageSize:10,
                total:0
            },
            votelist: [],
            votelistobj:{
                page:1,
                pageSize:10,
                total:0
            },
            empowerlist: [],
            empowerlistobj:{
                page:1,
                pageSize:10,
                total:0
            },
            driverlist: [],
            driverlistobj:{
                page:1,
                pageSize:10,
                total:0
            },
            showsearch: false
        };
    },
    mounted() {


    },
    methods: {
        getImageUrl(url: string | URL) {
            return new URL(url, import.meta.url).href;
        },
        searchfun() {
            this.showsearch = true;
            if(this.searchobj.searchvalue==''){
                this.showsearch = false;
            }else{
            this.inittablefilelist();
            this.inittableinfo();
            this.inittablevote();
            this.inittableempower();
            this.inittabledriver(); 
            }
 
        },
        showhome() {
            this.showsearch = false;
        },
        inittablefilelist() {
            //初始化数据
            axios({
                method: "get",
                url: "/zip/zipslist",
                params: {
                    searchvalue: this.searchobj.searchvalue,
                    page:this.filelistobj.page,
                    pagesize:this.filelistobj.pageSize
                },
            }).then(
                (res: { data: { data: any; count: any } }) => {
                    this.filelist = res.data.data;
                    this. filelistobj.total=res.data.count;
                    // console.log(res.data.data)
                },
                (err: any) => {
                    console.log(err);
                }
            );
        },
        inittableinfo() {
            //初始化数据
            axios({
                method: 'get',
                url: '/info/infoslist',
                params: {
                    searchvalue: this.searchobj.searchvalue,
                     page: -1,
                    pagesize: -1
                }
            }).then((res: { data: { data: any; count: any; }; }) => {
                this.infolist = res.data.data;
                this.infolistobj.total=res.data.count;
                // console.log(res.data.data)
            }, (err: any) => {
                console.log(err);
            })
        },
        inittablevote() {
            //初始化数据
            axios({
                method: 'get',
                url: '/vote/voteslist',
                params: {
                    searchvalue: this.searchobj.searchvalue,
                    page: -1,
                    pagesize: -1
                }
            }).then((res: { data: { data: any; count: any; }; }) => {
                this.votelist = res.data.data;
                this.votelistobj.total=res.data.count;
                // console.log(res.data.data)
            }, (err: any) => {
                console.log(err);
            })

        },
        inittableempower() {
            //初始化数据
            axios({
                method: 'get',
                url: '/empower/empowerlist',
                params: {
                    searchvalue: this.searchobj.searchvalue,
                    page: -1,
                    pagesize: -1
                }
            }).then((res: { data: { data: any; count: any; }; }) => {
                this.empowerlist = res.data.data;
                this.empowerlistobj.total=res.data.count;
                // console.log(res.data.data)
            }, (err: any) => {
                console.log(err);
            })

        },
        inittabledriver() {
            //初始化数据
            axios({
                method: 'get',
                url: '/driver/driverlist',
                params: {
                    searchvalue: this.searchobj.searchvalue,
                    page: -1,
                    pagesize: -1

                }
            }).then((res: { data: { data: any; count: any; }; }) => {
                this.driverlist = res.data.data;
                this.driverlistobj.total=res.data.count;
                //console.log(res.data.data)
            }, (err: any) => {
                console.log(err);
            })
        },
        handlechange(value){
            this. searchobj.searchvalue=value;
            this.searchfun();
        },
        dateFormat(data: number) {
            var str = data.toString();
            return str.slice(0, 4) + "-" + str.slice(4, 6) + "-" + str.slice(6, 8);
        },
        dateFormat2(longTypeDate: number) {
            var dateType = ""; 
            var date = new Date();  
            date.setTime(longTypeDate);  
            dateType += date.getFullYear();   //年  
            dateType += "-" + this.getMonth(date); //月   
            dateType += "-" + this.getDay(date);   //日  
            dateType += " " + this.getHours(date);   //日  
            dateType += ":" + this.getMinutes(date);   //日  
            dateType += ":" + this.getSeconds(date);   //日  
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
        deleteRowfilelist(index2:number, sonitem:any){
            //console.log(sonitem);
            ElMessageBox.confirm("是否删除文件" + sonitem.zipname + "?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
                draggable: true,
            })
            .then(() => {
                    axios({
                        method: "delete",
                        url: "/zip/del",
                        params: {
                            uuid: row.uuid,
                        },
                    }).then(
                        (res: { data: { data: any; count: any } }) => {

                            if (res.data.code == 1) {
                                ElMessage({
                                    type: "success",
                                    message: "删除成功",
                                });
                                this. inittablefilelist();
                            }
                        },
                        (err: any) => {
                            ElMessage({
                                type: "error",
                                message: "删除失败",
                            });
                            //console.log(err);
                        }
                    );
                })
            .catch((e) => {
                //console.log(e);
                    // ElMessage({
                    //     type: 'info',
                    //     message: 'Delete canceled',
                    // })
            });




        },
        deleteRowinfo(index2:number, sonitem:any){
            //console.log(sonitem);
            ElMessageBox.confirm("是否删除文件" + sonitem.infoname + "?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
                draggable: true,
            })
            .then(() => {
                    axios({
                        method: "delete",
                        url: "/info/del",
                        params: {
                            uuid: row.uuid,
                        },
                    }).then(
                        (res: { data: { data: any; count: any } }) => {

                            if (res.data.code == 1) {
                                ElMessage({
                                    type: "success",
                                    message: "删除成功",
                                });
                                this. inittableinfo();
                            }
                        },
                        (err: any) => {
                            ElMessage({
                                type: "error",
                                message: "删除失败",
                            });
                            console.log(err);
                        }
                    );
                })
            .catch((e) => {
                console.log(e);
                    // ElMessage({
                    //     type: 'info',
                    //     message: 'Delete canceled',
                    // })
            });
        },
        deleteRowvote(index2:number, sonitem:any){
            console.log(sonitem);
            ElMessageBox.confirm("是否删除文件" + sonitem.votename + "?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
                draggable: true,
            })
            .then(() => {
                    axios({
                        method: "delete",
                        url: "/vote/del",
                        params: {
                            uuid: row.uuid,
                        },
                    }).then(
                        (res: { data: { data: any; count: any } }) => {

                            if (res.data.code == 1) {
                                ElMessage({
                                    type: "success",
                                    message: "删除成功",
                                });
                                this. inittablevote();
                            }
                        },
                        (err: any) => {
                            ElMessage({
                                type: "error",
                                message: "删除失败",
                            });
                            console.log(err);
                        }
                    );
                })
            .catch((e) => {
                console.log(e);
                    // ElMessage({
                    //     type: 'info',
                    //     message: 'Delete canceled',
                    // })
            });
        },
        deleteRowEmpower(index2:number, sonitem:any){
            //console.log(sonitem);
            ElMessageBox.confirm("是否删除文件" + sonitem.sn+"/"+sonitem.lic + "?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
                draggable: true,
            })
            .then(() => {
                    axios({
                        method: "delete",
                        url: "/empower/del",
                        params: {
                            uuid: row.uuid,
                        },
                    }).then(
                        (res: { data: { data: any; count: any } }) => {

                            if (res.data.code == 1) {
                                ElMessage({
                                    type: "success",
                                    message: "删除成功",
                                });
                                this. inittableempower();
                            }
                        },
                        (err: any) => {
                            ElMessage({
                                type: "error",
                                message: "删除失败",
                            });
                            console.log(err);
                        }
                    );
                })
            .catch((e) => {
                console.log(e);
                    // ElMessage({
                    //     type: 'info',
                    //     message: 'Delete canceled',
                    // })
            });
        },
        deleteRowDriverlist(index2:number, sonitem:any){
            //console.log(sonitem);
            ElMessageBox.confirm("是否删除文件" + sonitem.drivername + "?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
                draggable: true,
            })
            .then(() => {
                    axios({
                        method: "delete",
                        url: "/driver/del",
                        params: {
                            uuid: row.uuid,
                        },
                    }).then(
                        (res: { data: { data: any; count: any } }) => {

                            if (res.data.code == 1) {
                                ElMessage({
                                    type: "success",
                                    message: "删除成功",
                                });
                                this.inittabledriver() ;
                            }
                        },
                        (err: any) => {
                            ElMessage({
                                type: "error",
                                message: "删除失败",
                            });
                            console.log(err);
                        }
                    );
                })
            .catch((e) => {
                console.log(e);
                    // ElMessage({
                    //     type: 'info',
                    //     message: 'Delete canceled',
                    // })
            });
        },
        filelisthandleSizeChange(val: number){
            this.filelistobj.pageSize=val;
            this.inittablefilelist();
        },
        filelisthandleCurrentChange(val: number){
            this.filelistobj.page=val;
            this.inittablefilelist();
        },
        infohandleSizeChange(val: number){
            this.infolistobj.pageSize=val;
            this.inittableinfo();
        },
        infohandleCurrentChange(val: number){
            this.infolistobj.page=val;
            this.inittableinfo();
        },
        votehandleSizeChange(val: number){
            this.votelistobj.pageSize=val;
            this.inittablevote();
        },
        votehandleCurrentChange(val: number){
            this.votelistobj.page=val;
            this.inittablevote();
        },
        empowerhandleSizeChange(val: number){
            this.empowerlistobj.pageSize=val;
            this.inittableempower();
        },
        empowerhandleCurrentChange(val: number){
            this.empowerlistobj.page=val;
            this.inittableempower();
        },
        driverlisthandleSizeChange(val: number){
            this.driverlistobj.page=val;
            this.inittabledriver();
        },
        driverlisthandleCurrentChange(val: number){
            this.driverlistobj.page=val;
            this.inittabledriver();
        },
        async setimagepath(src: string, i: number) {
            var _this = this;
            await axios
                .get(src, {
                    responseType: "blob",
                })
                .then((res) => {
                    // console.log('avatar has been download')
                    // console.log(res)
                    var reader = new FileReader();
                    reader.onload = (e) => {
                        _this.imagepaths[i] = e.target.result;
                    };
                    reader.readAsDataURL(res.data);
                });
        },
        getAppName(name: string) {
            var tag = name.split("_")[0];
            var tagname = "其他程序软件";
            if (tag == "APPQ") {
                return "环境";
            }

            if (tag == "APPX") {
                return "环境";
            }
            //@ts-ignore
            if (tag == "APP") {
                return "环境";
            }
            //@ts-ignore
            if (tag == "KSTPXQ") {
                return "计票";
            }
            //@ts-ignore
            if (tag == "KSTMAIN") {
                return "启动页";
            }
            //@ts-ignore
            if (tag == "KSTPXX") {
                return "计票";
            }
            //@ts-ignore
            if (tag == "WTKPX") {
                return "测评";
            }
            //@ts-ignore
            if (tag == "KSTSCAN") {
                return "扫描端";
            }
            //@ts-ignore
            if (tag == "KST") {
                return "计票后台";
            }
            if (tag == "WTKP") {
                return "测评后台";
            }
            //@ts-ignore
            if (tag == "KSTLX") {
                return "废品另选";
            }

            return tagname;
        },

    },
    watch: {


    },
    created() {

    },
    components: {
        ElNotification,
        uploadvue,
        fileList,
        LogAnlay,
        setup,
        myhelp,
        morevue,
        driver,
        info,
        vote,
        empower,
        my404vue, 
        ElMessageBox
    }
});