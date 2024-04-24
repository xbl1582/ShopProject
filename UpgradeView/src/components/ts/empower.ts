// @ts-nocheck
import { defineComponent } from "vue";
import uploadempowervue from "../uploadempower.vue"
import { ElMessageBox, ElMessage } from 'element-plus'
import my404vue from '../com/404vue.vue'
import foldertxt from '../com/foldertxt.vue'
//@ts-ignore
import imagevue from '../com/Image.vue'
import folderword from '../com/folderword.vue'
import folderpdf from '../com/folderpdf.vue'
import folderxlsx from "../com/folderxlsx.vue"
import moment from 'moment';
//@ts-ignore
import axios from 'axios'
export default defineComponent({
    name: "empower",
    props: {

    },
    data() {
        return {
            activeName:'emporty',
            componentis:'my404vue',
            loading:false,
            fileList: [],
            rowcontent:'',
           tableData:[
                ],
            searchobj: {
                searchvalue: "",
                page: 1,
                pagesize: 20
            },
            currentPage: 1,
            pageSize: 20,
            total: 100
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
                url: '/empower/empowerlist',
                params: {
                    //@ts-ignore
                    searchvalue: this.searchobj.searchvalue,
                    //@ts-ignore
                    page: this.searchobj.page,
                   //@ts-ignore
                    pagesize: this.searchobj.pagesize

                }
            }).then((res: { data: { data: any; count: any; }; }) => {
                //@ts-ignore
                this.tableData = res.data.data;
                //@ts-ignore
                this.total = res.data.count;
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
                '是否删除文件:' + row.sn + '与'+row.lic+'?',
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
                        url: '/empower/del',
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
       rowClick(row){
            // console.log(row);
            //判断是那种类型的文件，根据文件类型，选择具体的查看器
            switch (row.type) {
                case "txt":{
                     //@ts-ignore
                    this.componentis='foldertxt';
                    // console.log(row);
                    var _this=this;
                     //@ts-ignore
                    this.loading=true;
                    setTimeout( function(){
                       _this.$refs.child.settxtpath("/empower/file?path="+row.sn);
                        //@ts-ignore
                       _this.loading=false;
                        }, 1500)
                }
                break;
                default:
                    break;
            }

        },




    
    
    
    },
    watch: {


    },
    created() {

    },
    components: {
        uploadempowervue,
        ElMessageBox,
        imagevue,
        my404vue,
        foldertxt
    }
});