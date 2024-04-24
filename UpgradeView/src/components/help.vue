<template>
    <div style="height:200vh;">
        <pre >
        包名称：
            1.安装器脚本：
                1.1  票箱 installenvq.zip
                1.2  扫描仪 installenvx.zip
                1.3  后台   installenvh.zip

            2. 环境包：
                2.1 票箱： APPQ_rtm_arm_0000000_00000000.zip
                2.2 扫描仪：APPX_rtm_arm_0000000_00000000.zip
                2.3 后台：APP_rtm_arm_0000000_00000000.zip
            3. 票箱包：(包名称_包类型_系统类型_版本号_日期.zip)
                3.1 计票程序
                    3.1.1 完整包:KSTPXQ_rtm_arm_2001001_20231208.zip(例)
                    3.1.2 更新包:KSTPXQ_updated_arm_2001001_20231208.zip(例)
                    3.1.3 完整包:KSTPXQ_rtm_win_2001001_20231208.zip(例)
                    3.1.4 更新包:KSTPXQ_updated_win_2001001_20231208.zip(例)
                3.2 首页程序
                    3.2.1 完整包:KSTMAINQ_rtm_arm_2001001_20231208.zip(例)
                    3.2.2 更新包:KSTMAINQ_updated_arm_2001001_20231208.zip(例)
            4. 扫描仪包：
                4.1 计票程序
                    4.1.1 完整包:KSTPXX_rtm_arm_2001001_20231208.zip(例)
                    4.1.2 更新包:KSTPXX_updated_arm_2001001_20231208.zip(例)
                    4.1.3 完整包:KSTPXX_rtm_win_2001001_20231208.zip(例)
                    4.1.4 更新包:KSTPXX_updated_win_2001001_20231208.zip(例)
                4.2 首页程序
                    4.2.1 完整包:KSTMAINX_rtm_arm_2001001_20231208.zip(例)
                    4.2.2 更新包:KSTMAINX_updated_arm_2001001_20231208.zip(例)
                4.3 扫描端
                    4.3.1 完整包:KSTSCAN_rtm_arm_2001001_20231208.zip(例)
                    4.3.2 更新包:KSTSCAN_updated_arm_2001001_20231208.zip(例)
                4.4 测评
                    4.4.1 完整包:WTKP_rtm_arm_2001001_20231208.zip(例)
                    4.4.2 更新包:WTKP_updated_arm_2001001_20231208.zip(例)
            5. 后台包：
                5.1 后端
                    5.1.1 完整包:KST_rtm_arm_2001001_20231208.zip(例)
                    5.1.2 更新包:KST_updated_arm_2001001_20231208.zip(例)
                    5.1.3 完整包:KST_rtm_win_2001001_20231208.zip(例)
                    5.1.4 更新包:KST_updated_win_2001001_20231208.zip(例)  
                5.2 另选
                    5.2.1 完整包:KSTLX_rtm_arm_2001001_20231208.zip(例)
                    5.2.2 更新包:KSTLX_updated_arm_2001001_20231208.zip(例)
                    5.2.3 完整包:KSTLX_rtm_win_2001001_20231208.zip(例)
                    5.2.4 更新包:KSTLX_updated_win_2001001_20231208.zip(例)  

        压缩说明：
            1. 压缩包层次如下：
                1.1 完整包：
                    KSTPXQ_rtm_arm_2001001_20231208.zip
                        - KSTPXQ
                            - ……
                    KSTMAINQ_rtm_arm_2001001_20231208.zip
                        - KSTMAINQ
                            - ……
                    WTKP_rtm_arm_2001001_20231208.zip
                        - WTKP
                            - ……
                    KSTSCAN_rtm_arm_2001001_20231208.zip
                        - KSTSCAN
                            - ……
                1.2 更新包：
                    KST_updated_arm_2001001_20231208.zip  
                        - …… 更新内容
        安装说明：
            1. 从安装器选择合适的脚本进行下载
            2. 将安装器放置在设备的特点位置（buildroot- 放置在/mnt 中，……）
            3. 运行installenv?中的setup.sh/setup.bat脚本   
        </pre>

    </div>
</template>
  
<script lang="ts"  src="./ts/help.ts">




</script>
  
  
  
  
<style scoped></style>