<template>
  <div class="custom-tree-container" style="height: 99%">
      <div class="titlecontent" v-if="!showmoretable">
          <h1 style="font-size: 20px"  v-if="showlabel">
                    {{ currentactive }}-{{  tagname }}
          </h1>
      </div>
    <el-tabs
      v-model="activeName"
      class="demo-tabs"
      @tab-click="handleClick"
      style="height: 99%"
    v-if="!showmoretable"
    >
      <el-tab-pane label="软件管理" name="first" style="height: 100%">
        <el-container style="height: 100%; width: 100%">
          <el-aside style="width: 100px; height: 100%; margin-top: 20px">
            <el-menu
              default-active="KSTPXQ"
              class="menue"
              :collapse="true"
              style="width: 100%; height: 90%; margin-top: 10px"
             >
              <el-menu-item
                v-for="(item, index) in groudbox"
                :index="item"
                @click="showtable(item)"
              >
                <el-icon style="width: 100%; height: 100%">
                  <span>
                    <el-row>
                      <el-col :span="24">
                        <el-image
                          style="width: 40px; height: 40px"
                          :src="imagepaths[index]"
                          :zoom-rate="1.2"
                          :max-scale="7"
                          :min-scale="0.2"
                          :initial-index="4"
                          fit="cover"
                        />
                      </el-col>
                      <el-col :span="24" style="font-size: 12px">{{ item }}</el-col>
                    </el-row>
                  </span>
                </el-icon>
              </el-menu-item>
            </el-menu>
          </el-aside>

      <el-container>
        <el-header style="height:30px;">
        <el-row>
          <el-col :span="23">
           <el-menu
               style="width:100%;height:30px;"
                :default-active="currentos"
                class="el-menu-demo"
                :collapse="false"
                :ellipsis="false"
                mode="horizontal"
                      >
                        <el-menu-item v-for="item3 in oslist " :index="item3" @click="searchosfun(item3)" >
                          <el-icon v-if="item3=='arm'"><Operation /></el-icon>
                           <el-icon v-if="item3=='win'"><Menu /></el-icon>
                          <span>{{item3}}</span>
                        </el-menu-item>
                        <div class="flex-grow" />
                      <el-menu-item @click="showsettingsoftname()">
                          <el-icon><Tools /></el-icon>名称设置
                     </el-menu-item>
              </el-menu>
          
          </el-col>
 
        </el-row>
          
      
        
        </el-header>
        <el-main style="width: 100%; height: 99%; text-align: center; padding: 10px">
              
            <el-row v-for="(item, index) in elcards" :key="index" class="elcardbox" >
            
              <el-card
                v-if="item.content.length > 0"
                class="box-card"
                shadow="hover">
                <el-row style="border-bottom: solid 1px rgb(220, 220, 220);">
                  <el-col :span="8" style="text-align: left">
                    <span style="font-size: 1.5rem; font-weight: 20px; font-family: 楷体"
                      >{{ item.title }}({{ item.type }}) </span
                    >  <span style="font-size:0.7rem; color:(100,100,100);">
                        {{settipvalue(item,searchobj.path)}}
                    </span>
                  </el-col>
                  <el-col :span="8"> </el-col>
                  <el-col :span="8" style="text-align: right">
                    <el-tooltip
                      class="box-item"
                      effect="dark"
                      content="查看更多"
                      placement="bottom-start"
                    >
                      <el-button type="info" text @click="showmorelistfun(item, index)">
                        <el-icon><MoreFilled /></el-icon
                      ></el-button>
                    </el-tooltip>
                  </el-col>
                </el-row>
                <el-row
                  style="padding-top: 10px; height: 90%;"

                >
                  <el-container>
                    <el-main>
                      <el-row
                        style="
                          border-bottom: solid 1px rgb(200, 200, 200);
                          height: 25px;
                          color: rgb(100, 100, 100);
                        "
                      >
                        <el-col :span="4">
                          <el-icon>
                            <el-image :src="getImageUrl('../../static/assets/icons/bb.svg')"></el-image>
                          </el-icon>
                          版本
                        </el-col>
                        <el-col :span="4">
                            <el-icon><el-image :src="getImageUrl('../../static/assets/icons/time.svg')"></el-image></el-icon>
                          版本日期
                        </el-col>
                        <el-col :span="4">
                           <el-icon><el-image :src="getImageUrl('../../static/assets/icons/size.svg')"></el-image></el-icon>
                          文件大小
                        </el-col>
                        <el-col :span="4">
                            <el-icon><el-image :src="getImageUrl('../../static/assets/icons/time.svg')"></el-image></el-icon>
                          上传时间
                        </el-col>

                        
                        <el-col :span="4">
                           <el-icon><Download /></el-icon>
                          下载次数
                        </el-col>
                
                        <el-col :span="4">
                           <el-icon><el-image :src="getImageUrl('../../static/assets/icons/todo.svg')"></el-image></el-icon>
                          操作
                        </el-col>
                      </el-row>
                      <div class="listcontent"  v-loading="elcards[index].allloading">
                        <template v-if="item.content.length > 0">
                          <el-row
                            style="
                              border-bottom: solid 1px rgba(200, 200, 200, 0.5);
                              height: 40px;
                            "
                            v-for="(sonitem, index2) in item.content"
                            :key="index2"
                          >
                            <el-col :span="4" style="padding-top: 15px">
                              <el-tooltip
                                class="box-item"
                                effect="dark"
                                :content="sonitem.zipname"
                                placement="top-start"
                              >
                           
                                <a
                                  :href="
                                    '/zip/download?path=' +
                                    sonitem.path +
                                    '/' +
                                    sonitem.zipname
                                  "
                                >
                                     
                                <el-tag class="ml-2" type="danger"  style="margin:-10px 0px 0px -50px;" v-if="ismaxversion(sonitem)">最新</el-tag>
                                    {{ sonitem.versionchar }}
                                    </a>
                 
                            
                              </el-tooltip>
                            </el-col>
                            <el-col :span="4" style="padding-top: 15px">
                              {{ dateFormat(sonitem.updateloadtime) }}
                            </el-col>
                            <el-col :span="4" style="padding-top: 15px">
                              {{ sonitem.zipsize }}
                            </el-col>
                             <el-col :span="4" style="padding-top: 15px">
                               {{ sonitem.nowuploadtime }}
                            </el-col>
                            <el-col :span="4" style="padding-top: 15px">
                              {{ sonitem.downloadtime }}
                            </el-col>
                      
                            <el-col :span="4" style="padding-top: 15px">

                               <el-dropdown>
                                    <span class="el-dropdown-link">
                                     <el-icon><MoreFilled /></el-icon>
                                    </span>
                                    <template #dropdown>
                                      <el-dropdown-menu>
                                        <el-dropdown-item    @click="deleteRow(index2, sonitem)">
                                              <span>   <el-icon style="color:red;"><Delete /></el-icon>删除</span>
                                        
                                        </el-dropdown-item>
                                        <el-dropdown-item >
                                              <span> <el-icon><Document /></el-icon>日志</span>
                                        </el-dropdown-item>
                                      </el-dropdown-menu>
                                    </template>
                                  </el-dropdown>
                            </el-col>
                          </el-row>
                        </template>
                        <template v-else>
                          <div style="padding-top: 5%; color: rgba(100, 100, 100, 0.6)">
                            暂无数据
                          </div>
                        </template>
                      </div>
                    </el-main>
                  </el-container>
                </el-row>
          
              </el-card>
            </el-row>
          </el-main>
      </el-container>
       
        </el-container>
 
      </el-tab-pane>
  
      <el-tab-pane label="软件上传" name="second">
        <uploadvue></uploadvue>
      </el-tab-pane>
     
    </el-tabs>
    
    <el-row v-else>
      <el-row :gutter="20" style="margin-top:10px ;">
        <el-col :span="4">
        <el-button @click="backindex()"><el-icon><Back /></el-icon></el-button>
        </el-col>
        <el-col :span="4" >
            <el-select v-model="searchobj.myos" class="m-2" placeholder="系统" size="large" @change="myoschange">
             <el-option
              label="arm"
              value="arm"
            ></el-option>
             <el-option
              label="win"
              value="win"
            ></el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchobj.versiontype" class="m-2" placeholder="包类型" size="large" @change="myversiontypechange">
              <el-option
              label="默认"
              value="默认"
            ></el-option>
            <el-option
              label="完整包(rtm)"
              value="rtm"
            ></el-option>
             <el-option
              label="更新包(updated)"
              value="updated"
            ></el-option>
             <el-option
              label="安装包(deb)"
              value="deb"
            ></el-option>
          </el-select>
        </el-col>
         <el-col :span="4">
          <el-select v-model="searchobj.path" class="m-2" placeholder="软件" filterable size="large" @change="mypathchange" >

             <el-option
              v-for="item in groudbox"
              :key="item"
              :label="item"
              :value="item"
           >

           </el-option>
             </el-select>
         </el-col>
        <el-col :span="4"> 
            <el-select v-model="searchobj.versionchar" class="m-2" placeholder="版本号" size="large" @change="myversioncharchange">
             <el-option
              v-for="item in versions2"
              :key="item.versionchar"
              :label="item.versionchar"
              :value="item.versionchar"
             />
          </el-select>
        </el-col>
        <el-col :span="4">
            <el-input v-model="searchobj.searchvalue" placeholder="关键字" style="height:100%;" clearable  @change="mysearch"/>
        </el-col>
    
      </el-row>
      <el-row style="height:90%;">
        <el-col :span="24" style="height:45rem;overflow-y:auto;">
              <el-table ref="filterTable" :data="tableData" style="width: 98%; margin-top:10px;margin-bottom:20px;">
              <el-table-column prop="uuid" label="uuid" v-if="false" min-width="30%" />
              <el-table-column prop="zipname" label="软件名称" min-width="30%">
                <template #header>
                  <el-button style="position: relative; right: 10px" @click="inittable()">
                    <el-icon>
                      <RefreshLeft />
                    </el-icon>
                  </el-button>
                  软件名称
                </template>
                <template #default="scope">
                  <a :href="'/zip/download?path=' + scope.row.path + '/' + scope.row.zipname">{{
                    scope.row.zipname
                  }}</a>
                </template>
              </el-table-column>
              <el-table-column
                prop="type"
                label="终端类型"
                min-width="18%"
                :filters="[
                  { text: '票箱', value: 1 },
                  { text: '扫描仪', value: 2 },
                  { text: '后台', value: 3 },
                ]"
                :filter-method="filterTag"
                filter-placement="bottom-end"
               >
                <template #default="scope">
                  <el-tag
                    :type="
                      scope.row.type === 1
                        ? 'danger'
                        : scope.row.type === 3
                        ? 'success'
                        : scope.row.type == 2
                        ? ''
                        : 'warning'
                    "
                    disable-transitions
                  >
                    {{
                      scope.row.type === 1
                        ? "票箱" + getAppName2(scope.row.zipname)
                        : scope.row.type === 3
                        ? "后台" + getAppName2(scope.row.zipname)
                        : scope.row.type === 2
                        ? "扫描仪" + getAppName2(scope.row.zipname)
                        : getAppName2(scope.row.zipname)
                    }}</el-tag
                  >
                </template>
              </el-table-column>

                <el-table-column prop="versionchar" label="版本号" min-width="10%" />
              <el-table-column prop="updateloadtime" label="版本日期" min-width="10%">
                <template v-slot="scope">
                  <span v-text="dateFormat(scope.row.updateloadtime)"></span>
                </template>
              </el-table-column>
            <el-table-column prop="zipsize" label="文件大小" min-width="10%" />
                  
             
        
              <el-table-column prop="versiontype" label="版本类型" min-width="10%">
                <template v-slot="scope">
                  <el-tag :type="scope.row.versiontype === 'updated' ? '' : 'success'">
                    {{ scope.row.versiontype === "updated" ? "更新包" : "完整包" }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="nowuploadtime" label="上传时间" min-width="10%">
              <template v-slot="scope">
                          <span v-text="scope.row.nowuploadtime"></span>
              </template>
              </el-table-column>
          
              <el-table-column prop="version" label="版本号" min-width="10%" v-if="false" />
              <el-table-column prop="os" label="系统类型" min-width="10%">
                <template v-slot="scope">
                  <el-tag :type="scope.row.os === 'win' ? '' : 'success'">
                    {{ scope.row.os === "win" ? "win" : "arm" }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="downloadtime" label="下载次数" min-width="10%" />
              <el-table-column prop="path" label="存储路径" min-width="10%" />

              <el-table-column fixed="right" label="操作" min-width="15%">
                <template #default="scope">
                  <el-button
                    link
                    type="primary"
                    size="small"
                    @click="deleteRow(scope.$index, scope.row)"
                  >
                    删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

        </el-col>
        <el-col :span="24" style="height:5%;margin-top:20px;">
           <el-pagination
            v-model:current-page="searchobj.page"
            v-model:page-size="searchobj.pagesize"
            :page-sizes="[20, 30, 40,50]"
            small="small"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </el-col>
      </el-row>
    
   
    </el-row>
 <el-dialog
    v-model="showsettingsoftnameobj.isshow"
    title="名称设置"
    width="800"
    :before-close="showsettingsoftnameobjhandleClose"
    :opened="getpathclass()"

  >
         <el-row  style="text-align:center;margin-bottom:20px; border-bottom:solid 1px rgba(100,100,100,0.2);">
          <el-col :span="4">程序名称</el-col>
          <el-col :span="12">
            <el-row>
              <el-col :span="12">
                软件名称
              </el-col> 
              <el-col :span="12">
                类型
              </el-col>
            </el-row>
           
          </el-col>
          <el-col :span="8">操作</el-col>
         </el-row>
        <el-row v-for="item in  pathclasslist" style="text-align:center;margin-bottom:10px;">
          <el-col :span="4">
                <el-button text style="height:100%;">{{item.path}}</el-button>
          </el-col>
          <el-col :span="12">
              <el-row>
                <el-col :span="12">
                   <el-input v-model="item.pathname" style="max-width:150px;height:100%;" placeholder="Please input" />
                </el-col>
                  <el-col :span="12">
                      <el-select
                          v-model="item.type"
                          placeholder="Select"
                          size="large"
                          style="width: 100px"
                        >
                          <el-option
                          label="启动页"
                          :value="0"
                        />
                        <el-option
                          label="票箱"
                          :value="1"
                        />
                         <el-option
                          label="扫描仪"
                          :value="2"
                        />
                         <el-option
                          label="后台"
                          :value="3"
                        />
                         <el-option
                          label="其他程序"
                          :value="4"
                        />
                      </el-select>
                  
                  </el-col>
              </el-row>
          </el-col>
          <el-col :span="8">
            <el-button type="primary" style="height:100%;" @click="savesettingsoftname(item)">保存</el-button>
          </el-col>
        </el-row>
  </el-dialog>
  </div>
</template>

<script lang="ts" src="./ts/fileList.ts">

</script>

<style scoped>
 @import url("./css/fileList.css")
</style>
