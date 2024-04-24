//@ts-nocheck
import { defineComponent } from "vue";
import * as echarts from 'echarts';
//@ts-ignore
import axios from 'axios'
export default defineComponent({
    name: "loganaly",
    props: {

    },
    data() {
        return {
            fileList: [],
            count:0,
            xiton:true,
            ruanjianobj:{
                ruanjian:true,
                 option:{
                       tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                        type: 'shadow'
                        }
                    },
                   xAxis: {
                        type: 'category',
                        data: ['票箱-首页', '票箱-计票', '扫描仪-首页', '扫描仪-计票', '扫描仪-测评', '扫描仪-扫描', '后台-KST','后台-废票另选']
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                        data: [120, 200, 150, 80, 70, 110, 130,100,120],
                        type: 'bar'
                        }
                    ]
                }
            },
            xuanpiaoobj:{
            xuanpiao:true,
                option:{
                     dataset: [
                            {
                            dimensions: ['name', 'time'],
                            source: [
                                ['选票1', 41 ],
                                ['选票2', 20],
                                ['选票3', 52],
                                ['选票4', 37],
                                ['选票5', 25],
                                ['选票6', 19],
                                ['选票7', 71],
                                ['选票8', 36],
                                ['选票9', 67]
                            ]
                            },
                            {
                            transform: {
                                type: 'sort',
                                config: { dimension: 'time', order: 'desc' }
                            }
                            }
                        ],
                         tooltip: {
                                trigger: 'axis',
                                axisPointer: {
                                type: 'shadow'
                                }
                            },
                        xAxis: {
                            type: 'category',
                            axisLabel: { interval: 0, rotate: 30 }
                        },
                        yAxis: {},
                        series: {
                            type: 'bar',
                            encode: { x: 'name', y: 'time' },
                            datasetIndex: 1
                        }
                    

                }
            },
            shuomishuobj:{
                shuomishu:true,
                 option:{
       
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'horizontal',
                        left: 'left'
                    },
                    series: [
                        {
                        name: 'Access From',
                        type: 'pie',
                        radius: '50%',
                        data: [
                            { value: 1048, name: '说明书1' },
                            { value: 735, name: '说明书2' },
                            { value: 580, name: '说明书3' },
                            { value: 484, name: '说明书4' },
                            { value: 300, name: '说明书5' }
                        ],
                        emphasis: {
                            itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                        }
                    ]
                }
            },
            qudonobj:{
                 qudon:true,
                  option:{
                    xAxis: {
                        type: 'category',
                        data: ['票箱', '扫描仪', '后台']
                    },
                     tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                        type: 'shadow'
                        }
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                        data: [120, 200, 150],
                        type: 'bar',
                         emphasis: {
                            itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                        }
                    ]
                }
            },
            anzhuanqiobj:{
                anzhuanqi:true,
                 option:{
                  xAxis: {
                        type: 'category',
                        data: ['票箱', '扫描仪', '后台']
                    },
                     tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                        type: 'shadow'
                        }
                    },
                    yAxis: {},
                    series: [
                        {
                        type: 'bar',
                        name: 'window',
                        data: [89.3, 92.1, 94.4, 85.4]
                        },
                        {
                        type: 'bar',
                        name: 'linux',
                        data: [95.8, 89.4, 91.2, 76.9]
                        },
                        {
                        type: 'bar',
                        name: 'buildroot',
                        data: [97.7, 83.1, 92.5, 78.1]
                        }
                    ]
                }
            }

        };
    },
    mounted() {
        this.initruanjian();
        this. initxuanpiao();
        this.initshuomishuo();
        this.initqudon();
        this.inianzhuanqi();
    },
    methods: {
        getImageUrl(url: string | URL) {
            return new URL(url, import.meta.url).href;
        
        },
        initruanjian(){
            var chartDom = document.getElementById('ruanjian');
                var myChart = echarts.init(chartDom);

                this.ruanjianobj.option && myChart.setOption(this.ruanjianobj.option);
                this. ruanjianobj.ruanjian=false;
        },
         initxuanpiao(){
            var chartDom = document.getElementById('xuanpiao');
                var myChart = echarts.init(chartDom);

                this.xuanpiaoobj.option && myChart.setOption(this.xuanpiaoobj.option);
                this. xuanpiaoobj.xuanpiao=false;
        },
        initshuomishuo(){
            var chartDom = document.getElementById('shuomishu');
                var myChart = echarts.init(chartDom);

                this.shuomishuobj.option && myChart.setOption(this.shuomishuobj.option);
                this. shuomishuobj.shuomishu=false;
        },
         initqudon(){
            var chartDom = document.getElementById('qudon');
                var myChart = echarts.init(chartDom);

                this.qudonobj.option && myChart.setOption(this.qudonobj.option);
                this. qudonobj.qudon=false;
        },
         inianzhuanqi(){
            var chartDom = document.getElementById('anzhuanqi');
                var myChart = echarts.init(chartDom);

                this. anzhuanqiobj.option && myChart.setOption(this. anzhuanqiobj.option);
                this.  anzhuanqiobj. anzhuanqi=false;
        },
         initsystem(){
                 this.count+= 2
               
        }
    
    },
    watch: {


    },
    created() {

    },
    components: {

    }
});
