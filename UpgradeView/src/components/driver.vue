<template>
    <div style=" height: calc(100vh - 110px);">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
        <el-tab-pane label="驱动列表" name="filelist" >  
                <el-table :data="tableData" style="width:100%;min-width:400px; " :show-overflow-tooltip="true">
                    <el-table-column prop="drivername" label="驱动包名称" min-width="30%" >
                          <template #header>
                        <el-button style="position: relative; right: 10px;" @click="inittable()">
                            <el-icon>
                                <RefreshLeft />
                            </el-icon>
                        </el-button>
                            驱动包名称 
                            </template>
                            <template #default="scope">
                                  <a style="text-decoration:none; color:rgb(64,158,255); "  :href="'/driver/download?path=Driver/'+scope.row.drivername" :download="scope.row.votename">
                                    {{scope.row.drivername}}


                                  </a>

                            </template>

                    </el-table-column>
                    <el-table-column prop="type" label="文件类型" min-width="20%"  style="text-align:center;"/>
                    <el-table-column prop="driversize" label="文件大小" min-width="20%" />
                    <el-table-column prop="updatetime" label="上传时间" min-width="20%" >
                        <template #default="scope">
                            <span v-text=" dateFormat(scope.row.updatetime) "></span>
                        </template>

                    </el-table-column>
                    <el-table-column prop="downloadtime" label="下载次数" min-width="20%" />
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
                          <el-button :type="(istop==scope.row.priority)?'primary':'info'" style="margin-bottom:8px; margin-left:20px;"  circle  @click="setTop(scope.$index, scope.row)" link >
                               置顶
                           </el-button>
                          <el-dropdown style="margin-left:20px;margin-top:5px;">
                                    <span class="el-dropdown-link">
                                    更多
                                    </span>
                                    <template #dropdown>
                                      <el-dropdown-menu>
                                        <el-dropdown-item   @click="deleteRow(scope.$index, scope.row)">
                                              <span>   <el-icon style="color:red;"><Delete /></el-icon>删除</span>
                                        
                                        </el-dropdown-item>
                                        <el-dropdown-item >
                                              <span> <el-icon><Document /></el-icon>日志</span>
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
                                          
        
        </el-tab-pane>
        <el-tab-pane label="上传驱动程序" name="upload" >
            <uploaddrivervue></uploaddrivervue>
        
        
        </el-tab-pane>
  </el-tabs>
</div>
</template>
  
<script lang="ts" src="./ts/driver.ts" >


</script>
  
  
  
  
<style scoped>



</style>