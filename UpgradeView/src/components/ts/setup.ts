//@ts-nocheck
import { defineComponent } from "vue";
//@ts-ignore
import axios from 'axios'
export default defineComponent({
    name: "loganaly",
    props: {

    },
    data() {
        return {
            fileList: []
        };
    },
    mounted() {


    },
    methods: {
        getImageUrl(url: string | URL) {
            return new URL(url, import.meta.url).href;
        
        },
        dowonloadsetup(mytype:string,mypath:string){
  //初始化数据
            window.location.href = '/zip/setup?type='+mytype+"&path="+mypath;
            // axios({
            //     method: 'get',
            //     url: '/api/zip/setup',
            //     params: {
            //     type:mytype,
            //     path:mypath
            //     }
            // }).then((res: { data }) => {
            //     console.log(res.data);
            // }, (err: any) => {
            //     console.log(err);
            // })




        }

    },
    watch: {


    },
    created() {

    },
    components: {

    }
});

