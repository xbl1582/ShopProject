//@ts-nocheck
import { defineComponent } from "vue";
import filechange from '../com/filechange.vue'
import imagechange from '../com/imagechange.vue'
import ocrchange from '../com/ocrchange.vue'
// import svgIcon from './com/svgIcon.vue'
//@ts-ignore
import axios from 'axios'
export default defineComponent({
    name: "more",
    props: {

    },
    data() {
        return {
          showifame:true,
            fileList: [],
            activeName:0,
            addcompdialog:false,
            mycompenttype:[
              {
                typechar:'所有',
                type:0,
                mycompentlist:[
                  { logosrc:'',
                   title:'代码生成器',
                   typechar:'开发类',
                   type:1,
                   desc:''
                }
              ]
              },
              {
                typechar:'开发类',
                type:1,
                mycompentlist:[]
              },
              {
                typechar:'文档类',
                type:2,
                mycompentlist:[]
              },
              {
                typechar:'图片类',
                type:3,
                mycompentlist:[]
              },
              {
                typechar:'音频类',
                type:4,
                mycompentlist:[]
              },
              {
                typechar:'视频类',
                type:5,
                mycompentlist:[]
              },
              {
                typechar:'运维类',
                type:6,
                mycompentlist:[]
              },
              {
                typechar:'设计类',
                type:7,
                mycompentlist:[]
              },
              {
                typechar:'经验学术类',
                type:8,
                mycompentlist:[]
              },
              {
                typechar:'其它',
                type:9,
                mycompentlist:[]
              }
            ],
            componentobj:{
              name:"",
              show:false,
              component:""
            },
            form:{
              title:'',
              type:9,
              urlpath:'',
              desc:'',
              typechar:"其它"
            },
       
        };
    },
    mounted() {


    },
    methods: {
        getImageUrl(url: string | URL) {
            return new URL(url, import.meta.url).href;
        
        },
        uploadimagetopdf(){
             this. componentobj.name="图片转pdf";  
             this. componentobj.show=true;  
            this.componentobj.component='imagechange';
        },
        uploadfiletopdf(){
          this. componentobj.name="文件转换";  
          this. componentobj.show=true;  
          this.componentobj.component='filechange';
        },
        handleClick(item){
            console.log(item);
         
        },


        uploadimagetotxt(){
        this.componentobj.name="图片转文字";  
          this.componentobj.show=true;  
           this.componentobj.component='ocrchange';
          
        },
    
        handleClose(){
          this.addcompdialog=false; 
        },
        async setimagepath(src: string,index:number,i:number) {
          var _this = this;
          var imagepath;
          await axios
              .get(src, {
                  responseType: "blob",
              })
              .then((res) => {
                  // console.log('avatar has been download')
                  // console.log(res)
                  var reader = new FileReader();
                  reader.onload = (e) => {
                      this.mycompenttype[index].mycompentlist[i].logosrc= e.target.result;
                  
                  };
                  reader.readAsDataURL(res.data);
              });
      },
      onSubmit(){
        this.addcompdialog=false;
      },
      closeDialog(){
        this.addcompdialog=false;
      }
    
    },
    watch: {


    },
    created() {

    },
    components: {
      // svgIcon
      filechange,
      imagechange,
      ocrchange
    }
});
