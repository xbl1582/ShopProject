//@ts-nocheck
import { defineComponent } from "vue";
//@ts-ignore
import axios from 'axios'
export default defineComponent({
    name: "myhelp",
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
    
    },
    watch: {


    },
    created() {

    },
    components: {

    }
});
