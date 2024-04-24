<template>
    <div style=" height: calc(100vh - 110px);">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick" >
        <el-tab-pane label="选票列表" name="filelist" > 
            <div class="column">
            <div class="column-left">
                <div class="resize-bar"></div>
                <div class="resize-line"></div>
                <div class="resize-save">
                    
                <el-table :data="tableData" style="width:100%;min-width:400px; " :show-overflow-tooltip="true" @row-click="rowClick">
                    <el-table-column prop="votename" label="选票名称" min-width="30%" >
                          <template #header>
                        <el-button style="position: relative; right: 10px;" @click="inittable()">
                            <el-icon>
                                <RefreshLeft />
                            </el-icon>
                        </el-button>
                            选票名称 
                            </template>
                   <template #default="scope">
                           <a style="text-decoration:none; color:rgb(64,158,255); "  :href="'/vote/download?path=vote/'+scope.row.votename" :download="scope.row.votename">{{scope.row.votename }}</a> 
                    </template>

                    </el-table-column>
                    <el-table-column prop="type" label="类型" min-width="8%" />
                    <el-table-column prop="uploadtime" label="上传时间" min-width="20%" >
                           <template #default="scope">
                            {{ dateFormat(scope.row.updatetime)}}
                        </template>

                     </el-table-column>
                     <el-table-column prop="downloadtime" label="下载" min-width="5%" />
                 
                    <el-table-column fixed="right" label="操作" min-width="20%">
                       <template #header>
                        <el-input v-model="searchobj.searchvalue" size="small"  placeholder="查询">

                            <template #append>
                                <el-button @click="searchfun()">
                                    <el-icon>
                                        <Search />
                                    </el-icon>
                                </el-button>
                            </template>
                        </el-input>
                        </template>
                        <template  #default="scope">
                         <el-button    @click="rowClick(scope.row)" link  style="margin-bottom:8px; margin-left:20px;">
                              预览
                           </el-button>
                           <el-button :type="(istop==scope.row.priority)?'primary':'info'" style="margin-bottom:8px; margin-left:20px;"  circle  @click="setTop(scope.$index, scope.row)" link >
                               置顶
                           </el-button>
                            <el-dropdown style="margin-left:10px;">
                                <span class="el-dropdown-link">
                                        <el-button type="info" link>更多</el-button>    
                                </span>
                                <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item    @click="deleteRow(scope.$index, scope.row)">
                                      <el-button type="danger" link> <el-icon><DeleteFilled /></el-icon> 删除</el-button>
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                        </template>
                        </el-table-column>
                </el-table>
                <el-pagination style="margin-top: 10px;" v-model:current-page="currentPage" v-model:page-size="pageSize"
                    :page-sizes="[20, 30, 40,50]" layout="total,sizes, prev, pager, next" :total="total" @size-change="handleSizeChange"
                    @current-change="handleCurrentChange" />
                </div>                                            
            </div>
            <div class="column-right" v-loading="loading" >
               
                <component :is='componentis' ref="child"></component>
            </div>
            </div>
        </el-tab-pane>
        <el-tab-pane label="上传选票" name="upload" >
            <uploadvotevue></uploadvotevue>
        
        
        </el-tab-pane>
  </el-tabs>
</div>
</template>
   
<script lang="ts" src="./ts/vote.ts" >




</script>
  
  
  
  
<style scoped>


.column {
    height:100%;
    overflow: hidden;
}
.column-left {
    
    height: calc(100vh - 110px);
    background-color: #fff;
    position: relative;
    float: left;
}
.column-right {
    height: calc(100vh - 110px);
    overflow-x:hidden;
    padding:10px;
    overflow-y:auto;
    background-color: rgba(240,240,240,1);
    box-sizing: border-box;
    overflow: hidden;
}
.column-right ::-webkit-scrollbar {
    display: none;
}
.resize-save {
    position: absolute;
   
    top: 0; right: 5px; bottom: 0; left: 0;
    padding: 16px;
    overflow-x: hidden;
}
.resize-bar {
    width: 800px; height: inherit;
    resize: horizontal;
    cursor: ew-resize; 
    opacity: 0;
    overflow: scroll;
}
/* 拖拽线 */
.resize-line {
    position: absolute;
    right: 0; top: 0; bottom: 0;
    border-right: 2px solid #eee;
    border-left: 1px solid #bbb;
    pointer-events: none;
}
.resize-bar:hover ~ .resize-line,
.resize-bar:active ~ .resize-line {
    border-left: 1px dashed skyblue;
}
.resize-bar::-webkit-scrollbar {
    width: 200px; height: inherit;
}

/* Firefox只有下面一小块区域可以拉伸 */
@supports (-moz-user-select: none) {
    .resize-bar:hover ~ .resize-line,
    .resize-bar:active ~ .resize-line {
        border-left: 1px solid #bbb;
    }
    .resize-bar:hover ~ .resize-line::after,
    .resize-bar:active ~ .resize-line::after {
        content: '';
        position: absolute;
        width: 16px; height: 16px;
        bottom: 0; right: -8px;
        background: url(./resize.svg);
        background-size: 100% 100%;
    }
}
.demo-tabs{
    height:100%;
}


.el-tab-pane {
  height: calc(100vh - 110px);
  overflow-y: auto;
}
</style>