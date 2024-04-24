// @ts-nocheck
//@ts-ignore
import { defineComponent, ref, version } from "vue";
import axios from "axios";
import my404vue from "../com/404vue.vue";
import { ElMessageBox, ElMessage } from "element-plus";
import uploadvue from "../uploadvue.vue";
import myimage from "../com/Image.vue";
export default defineComponent({
    name: "filelist",
    props: {},
    data() {
        return {
            showlabel:true,
            showmoretable: false,
            activeName: "first",
            path: "KSTPXQ",
            tagname : "其他程序软件",
            tableData: [],
            groudbox: [],
            versions: [],
            versions2: [],
            versionpageobj: {
                page: 0,
                total: 0,
                pagesize: 10,
            },
            searchobj: {
                searchvalue: "",
                page: 1,
                pagesize: 20,
                type: null,
                versiontype: '',
                path: "KSTPXQ",
                myos: "arm"
            },
            maxversionlist:[
                {
                    path:'',
                    version:0,
                    myos:'arm'
                }
            ],
            elcards: [
                {
                    title: "完整包",

                    type: "rtm",
                    oslist: [],
                    currentos: "arm",
                    showmorelist: false,
                    content: [],
                    allloading: true,
                    currentPage: 1,
                    pageSize: 10,
                    total: 100,
                },

                {
                    title: "更新包",

                    type: "updated",
                    showmorelist: false,
                    content: [],
                    oslist: [],
                    currentos: "arm",
                    allloading: true,
                    currentPage: 1,
                    pageSize: 10,
                    total: 100,
                },
                {
                    title: "安装包",

                    type: "deb",
                    showmorelist: false,
                    content: [],
                    oslist: [],
                    currentos: "arm",
                    allloading: true,
                    currentPage: 1,
                    pageSize: 10,
                    total: 100,
                },
            ],

            showsettingsoftnameobj:{
                isshow:false
            },
            oslist:[],
            imagepaths: [],
            pathclasslist:[],
            currentPage: 1,
            pageSize: 20,
            total: 100,
            loading: true,
            currentactive: "KSTPXQ",
            currentos: "arm"

        };
    },
    mounted() {
        // console.log(this.tableData.length);
        this.initgroud();
    },
    methods: {

        getImageUrl(url: string | URL) {
            return new URL(url, import.meta.url).href;
        },
        deleteRow(index: number, row: any) {
            ElMessageBox.confirm("是否删除文件" + row.zipname + "?", "提示", {
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
                            // console.log(res.data);
                            //@ts-ignore
                            if (res.data.code == 1) {
                                ElMessage({
                                    type: "success",
                                    message: "删除成功",
                                });
                                //@ts-ignore
                                // this.tableData.splice(index, 1)
                                // this.inittable();
                               this.initgroud();
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
                .catch(() => {
                    // ElMessage({
                    //     type: 'info',
                    //     message: 'Delete canceled',
                    // })
                });
        },
        initgetmaxversion(   
            myversiontype: string,
            mypath: string,
            myos: string,
            mypage: number,
            mypagesize: number,
            index: number){
                axios({
                    method: "get",
                    url: "/zip/getMaxVersions",
                    params: {
                        //@ts-ignore
                        page: mypage,
                        //@ts-ignore
                        pagesize: mypagesize,
                        path: mypath,
                        os: myos,
                        versiontype: myversiontype,
                    },
                }).then(
                    (res: { data: { data: any; count: any } }) => {
                        // console.log(res.data);
                        if (res.data.count != 0) {
                            var resultobj=res.data.data;
                            // console.log(resultobj)
                            for(var i=0;i<this.maxversionlist.length;i++){
                               
                                if(resultobj!=undefined){
                                    if(resultobj.path==this.maxversionlist[i].path&&resultobj.myos==this.maxversionlist[i].myos){
                                        if(resultobj.version>=this.maxversionlist[i].version){
                                            this.maxversionlist[i].version=resultobj.version;
                                        }
                                    }else{
                                        if(i==this.maxversionlist.length-1){
                                            this.maxversionlist.push(resultobj);
                                        }
                                      
                                    }
                                }
                            }
                        }
                    },
                    (err: any) => {
                        console.log(err);
                    }
                );
        },
        ismaxversion(item){
            for(var i=0;i<this.maxversionlist.length;i++){
                    if(item.path==this.maxversionlist[i].path){
                        if(item.version==this.maxversionlist[i].version){
                          return true;
                        }
                }
            }
            return false;
        },
        initgroud() {
            axios({
                method: "get",
                url: "/zip/getPaths",
            }).then(
                (res: { data: { data: any; count: any } }) => {
                    // console.log(res.data);

                    this.groudbox = res.data.data;
                    // this.total = res.data.count;
                    for (var i = 0; i < res.data.count; i++) {
                        this.setimagepath("/image?path=" + this.groudbox[i] + ".png", i);
                    }
                },
                (err: any) => {
                    console.log(err);
                }
            );
            //获取最新版本
            this.initos(this.searchobj.path);
            for (var i = 0; i < this.elcards.length; i++) {
                this.initVersions(
                    this.elcards[i].type,
                    this.searchobj.path,
                    "arm",
                    this.currentPage,
                    5,
                    i
                );
                this.initgetmaxversion(
                    null,
                    this.searchobj.path,
                    "arm",
                    this.currentPage,
                    5,
                    i
                );
    
            }
            this. getpathclass();
            this.getAppName( this.currentactive);

        },
        initos(
            mypath: string
        ) {
            axios({
                method: "get",
                url: "/zip/getOs",
                params: {
                    path: mypath
                }

            }).then(
                (res: { data: { data: any; count: any } }) => {
                    // console.log(res.data.data)
                    this.oslist = res.data.data;
                },
                (err: any) => {
                    console.log(err);
                }
            );
        },
        initVersions(
            myversiontype: string,
            mypath: string,
            myos: string,
            mypage: number,
            mypagesize: number,
            index: number
        ) {
            axios({
                method: "get",
                url: "/zip/getVersions",
                params: {
                    //@ts-ignore
                    page: mypage,
                    //@ts-ignore
                    pagesize: mypagesize,
                    path: mypath,
                    os: myos,
                    versiontype: myversiontype,
                },
            }).then(
                (res: { data: { data: any; count: any } }) => {
                    console.log(res.data);
                    if (res.data.count != 0) {
                        this.elcards[index].content = res.data.data;
                    } else {
                        this.elcards[index].content = [];
                    }

                    this.elcards[index].total = res.data.count;
                    this.elcards[index].allloading = false;
                },
                (err: any) => {
                    console.log(err);
                }
            );
        },
        initVersions2() {
            axios({
                method: "get",
                url: "/zip/getVersions",
                params: {
                    page: this.searchobj.page,
                    pagesize: this.searchobj.pagesize,
                    path: this.searchobj.path,
                    versiontype: this.searchobj.versiontype,
                    os: this.searchobj.myos
                },
            }).then(
                (res: { data: { data: any; count: any } }) => {
                    // console.log(res.data);
                    this.versions2 = res.data.data;
                },
                (err: any) => {
                    console.log(err);
                }
            );
        },
        showtabledata(row) {
            // console.log(row);
            this.searchobj.versionchar = row.versionchar;
            this.inittable();
        },
        showtable(item) {
            this.searchobj.path = item;
            this.currentactive = item;
            this.initos(this.searchobj.path);
            this.currentos="arm"
            for (var i = 0; i < this.elcards.length; i++) {
                this.initVersions(
                    this.elcards[i].type,
                    this.searchobj.path,
                   "arm",
                    this.currentPage,
                    5,
                    i
                );
                this.initgetmaxversion(
                    null,
                    this.searchobj.path,
                    "arm",
                    this.currentPage,
                    5,
                    i
                );
            }
            this.getAppName( this.currentactive);
            // this.initVersions();
            // this.inittable();
        },
        inittable() {
            //初始化数据
            axios({
                method: "get",
                url: "/zip/zipslist",
                params: {
                    //@ts-ignore
                    searchvalue: this.searchobj.searchvalue,
                    //@ts-ignore
                    page: this.searchobj.page,
                    //@ts-ignore
                    pagesize: this.searchobj.pagesize,
                    path: this.searchobj.path,
                    versiontype: this.searchobj.versiontype,
                    os: this.searchobj.myos
                },
            }).then(
                (res: { data: { data: any; count: any } }) => {
                    // console.log(res.data);
                    //@ts-ignore
                    this.tableData = res.data.data;
                    //@ts-ignore
                    this.total = res.data.count;
                },
                (err: any) => {
                    console.log(err);
                }
            );
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
            this.this.inittable();
        },
        handleCurrentChange(val: number) {
            //@ts-ignore
            this.currentPage = val;
            //@ts-ignore
            this.searchobj.page = this.currentPage;
            this.inittable();
            // console.log(`current page: ${val}`)
        },

        clearFilter() {
            //@ts-ignore
            this.$refs.filterTable.clearFilter();
        },
        filterTag(value: any, row: { type: any }) {
            this.searchobj.type = value;
            this.inittable();
            return row.type === value;
        },
        filterHandler(value: any, row: { [x: string]: any }, column: { [x: string]: any }) {
            const property = column["property"];
            return row[property] === value;
        },
        //日期
        dateFormat(data: number) {
            var str = data.toString();
            return str.slice(0, 4) + "-" + str.slice(4, 6) + "-" + str.slice(6, 8);
        },
     async   getAppName(name: string) {
        var tag = name.split("_")[0];
        await  axios({
                method: "get",
                url: "/pathclass/path",
                params:{
                    path:tag
                }
            }).then(
                (res: { data: { data: any; count: any } }) => {
                    // console.log(res.data)
                    if(res.data.code==1){
                        this.tagname=res.data.data.pathname;
                        // console.log(res.data.data)
                    }else{
                        this.tagname = "其他程序软件";
                    }
                    
                },
                (err: any) => {
                    console.log(err);
                }
            );  
           
        },
        getAppName2(name: string){
            var tag = name.split("_")[0];
            for(var i=0;i<this.pathclasslist.length;i++){

                if(tag==this.pathclasslist[i].path){
                    return this.pathclasslist[i].pathname;
                }
            }
            return "其他程序软件";


        },
        handleClick(tab: TabsPaneContext, event: Event) {
            if (tab.props.name == "first") {
                this.initgroud();
                this. showlabel=true;
            }else{
                this. showlabel=false;
            }
        },
        changeTagName(name: string) {
            switch (name) {
                case "KSTPXQ":
                    return name;
                case "KSTPXX":
                    return name;
                case "KSTSCAN":
                    return name;
                case "KSTMAIN":
                    return name;
                case "WTKP":
                    return name;
                case "KST":
                    return name;
                case "KSTLX":
                    return name;
                case "APPQ":
                    return name;
                case "APPX":
                    return name;
                default:
                    return name;
            }
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
        async showmorelistfun(item, index) {
            // console.log(item);
            this.showmoretable = true;
            this.searchobj.searchvalue = '';
            this.searchobj.versiontype = item.type;
            this.searchobj.path = this.currentactive;
            this.searchobj.myos = this.currentos;
            // console.log(this.searchobj.myos)
            this.initVersions2();
            this.inittable();

        },
        searchosfun(item: string) {
            this.currentos = item;
            this.searchobj.myos = item;
            for(var i=0;i<this.elcards.length;i++){
                this.initVersions(
                    this.elcards[i].type,
                    this.searchobj.path,
                    item,
                    this.currentPage,
                    5,
                    i
                );
             
            }
            this.initgetmaxversion(
                null,
                this.searchobj.path,
                this.searchobj.myos,
                this.currentPage,
                5,
                i
            );
 
           
           
        },
        showsettingsoftname(){
            this.showsettingsoftnameobj.isshow=true;


        },
        savesettingsoftname(item){
            // console.log(item);
            axios({
                method: "put",
                url: "/pathclass",
                data:{
                    uuid:item.uuid,
                    path:item.path,
                    pathname: item.pathname,
                    type:item.type
                }
            }).then(
                (res: { data: { data: any; count: any } }) => {
                    // console.log(res.data);
                    if(res.data.code==1){
                        ElMessage({
                            type: "success",
                            message: "修改成功",
                        });
                    }
                },
                (err: any) => {
                    console.log(err);
                }
            );     
        },
        showsettingsoftnameobjhandleClose(){
            this.showsettingsoftnameobj.isshow=false;

        },
        getpathclass(){
             //初始化数据
             axios({
                method: "get",
                url: "/pathclass"
            }).then(
                (res: { data: { data: any; count: any } }) => {
                    // console.log(res.data);
                    this.pathclasslist = res.data.data;
                },
                (err: any) => {
                    console.log(err);
                }
            );     


        },

        backindex() {
            this.showmoretable = !this.showmoretable;
        },
        myversioncharchange(value: any) {
            // console.log(value)

            this.searchobj.versionchar = value;
            this.inittable();
        },
        mypathchange(value: any) {
            this.searchobj.path = value;
            this.versions2.splice(0);
            this.initVersions2();
            this.inittable();
        },
        myversiontypechange(value: any) {
            if (value == "默认") {
                this.searchobj.versiontype = null;
            } else {
                this.searchobj.versiontype = value;
            }
            this.versions2.splice(0);
            this.initVersions2();
            this.inittable();
        },
        myoschange(value: any) {
            this.searchobj.myos = value;
            this.versions2.splice(0);
            this.initVersions2();
            this.inittable();
        },
        mysearch() {

            this.inittable();
        },
        settipvalue(item: any, path) {
            var name="";
            switch (path) {
                case "KSTPXQ":
                case "KSTPXX":
                case "KSTMAIN":
                case "KSTSCAN":
                case "WTKPX":
                    return  name=(item.type == "updated" ?    '推荐更新': '推荐安装')+(this.searchobj.myos=="win"?"于C:/"+path+"目录":"于/APP/" + path + "目录");
                case "KST":
                case "KSTLX":
                case "WTKP":
                case "YYEditor":
                case "KSTSP":
                    return name=(item.type == "updated" ?   '推荐更新':'推荐安装')+(this.searchobj.myos=="win"?"于C:/"+path+"目录":"于/opt/WT/" + path + "目录");
                  
            }
        },
        dateFormat2(longTypeDate: number) {
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
        }
    },
    watch: {},
    created() { },
    components: {
        //@ts-ignore
        ElMessageBox,
        uploadvue,
        myimage,
        my404vue,
    },
});