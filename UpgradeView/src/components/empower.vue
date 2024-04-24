<template>
    <div style=" height: calc(100vh - 110px);">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick" >
        <el-tab-pane label="授权列表" name="emporty" > 
            <div class="column">
            <div class="column-left">
                <div class="resize-bar"></div>
                <div class="resize-line"></div>
                <div class="resize-save">
                    
                <el-table :data="tableData" style="width:100%;min-width:400px; " :show-overflow-tooltip="true" @row-click="rowClick">
                    <el-table-column prop="sn" label="sn" min-width="25%" >
                          <template #header>
                        <el-button style="position: relative; right: 10px;" @click="inittable()">
                            <el-icon>
                                <RefreshLeft />
                            </el-icon>
                        </el-button>
                            SN
                            </template>
                        <template #default="scope">
                            <a
                              :href="
                                '/empower/download?path=' +
                               scope.row.snpath +
                                '/' +
                                 scope.row.sn
                              "
                            >
                              {{scope.row.sn}}</a
                            >
                        </template>
                    </el-table-column>
                    <el-table-column prop="lic" label="授权LIC" min-width="25%" >
                     <template #default="scope">
                            <a
                              :href="
                                '/empower/download?path=' +
                                 scope.row.licpath +
                                '/' +
                               scope.row.lic
                              "
                            >
                              {{scope.row.lic}}</a
                            >
                        </template>


                    </el-table-column>
                    <el-table-column prop="type" label="类型" v-if="false" min-width="20%" />
                     <el-table-column prop="snpath" label="SN路径" v-if="false" min-width="20%" />
                      <el-table-column prop="licpath" label="LIC路径" v-if="false" min-width="30%" />
                     <el-table-column prop="uploadtime" label="上传时间" min-width="20%" >
              

                     </el-table-column>
                     <el-table-column  label="下载次数" min-width="10%" >
                        <template #default="scope">
                            {{scope.row.sndownloadtime}}/ {{scope.row.licdownloadtime}}

                        </template>

                     </el-table-column>
                    <el-table-column fixed="right" label="操作" min-width="15%">
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
                                <el-button type="primary" link  @click="rowClick(scope.row)">预览</el-button>
                              
                              <el-dropdown style="margin-top:3px;margin-left:20px;">
                                <span class="el-dropdown-link">
                                     <el-button type="info" link>更多</el-button>    
                                </span>
                                <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item   @click="deleteRow(scope.$index, scope.row)">
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
        <el-tab-pane label="上传SN或授权" name="upload" >
            <uploadempowervue></uploadempowervue>
        
        
        </el-tab-pane>
  </el-tabs>
</div>
</template>
  
<script lang="ts" src="./ts/empower.ts" >





</script>
  
  
  
  
<style scoped>
@import url("./css/empower.css")
</style>