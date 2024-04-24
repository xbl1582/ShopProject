<template>
  <div style="width: 100%; height: 100%; padding: 0px">
    <el-container>
      <el-header>
        <el-row class="title">
          <el-col :span="8">
            <el-row>
              <el-col :span="3" style="padding: 5px; padding-left: 20px">
                <el-image
                  @click="showhome()"
                  :src="getImageUrl('../../static/assets/KST.png')"
                  style="width: 2.5rem"
                ></el-image>
              </el-col>
              <el-col :span="20">
                <h2 class="text">快选通发布服务</h2>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="8">
            <el-input
              v-model="searchobj.searchvalue"
              placeholder="搜索"
              @change="handlechange"
            >
              <template #append>
                <el-button @click="searchfun()">
                  <el-icon>
                    <Search />
                  </el-icon>
                </el-button>
              </template>
            </el-input>
          </el-col>
          <el-col :span="8"> </el-col>
        </el-row>
      </el-header>
      <el-container v-if="!showsearch">
        <el-aside :style="isCollapse ? 'width:' + 80 + 'px' : 200 + 'px; '">
          <el-menu default-active="1" class="menue" :collapse="isCollapse">
            <el-menu-item index="1" @click="activename = 'fileList'" style="padding: 0px">
              <el-icon style="width: 100%; height: 100%">
                <span>
                  <el-row>
                    <el-col :span="24">
                      <el-icon><FolderOpened /></el-icon
                    ></el-col>
                    <el-col :span="24" style="font-size: 12px">软件</el-col>
                  </el-row>
                </span>
              </el-icon>
            </el-menu-item>

            <el-menu-item index="2" @click="activename = 'empower'" style="padding: 0px">
              <el-icon style="width: 100%; height: 100%">
                <span>
                  <el-row>
                    <el-col :span="24">
                      <el-icon><Aim /></el-icon
                    ></el-col>

                    <el-col :span="24" style="font-size: 12px">授权</el-col>
                  </el-row>
                </span>
              </el-icon>
            </el-menu-item>

            <!-- <el-menu-item index="2" @click="activename='uploadvue'">
           <el-icon><Upload /></el-icon>
            <template #title>上传</template>
          </el-menu-item> -->
            <el-menu-item index="3" @click="activename = 'vote'" style="padding: 0px">
              <el-icon style="width: 100%; height: 100%">
                <span>
                  <el-row>
                    <el-col :span="24">
                      <el-icon><Tickets /></el-icon
                    ></el-col>
                    <el-col :span="24" style="font-size: 12px">选票</el-col>
                  </el-row>
                </span>
              </el-icon>
            </el-menu-item>
            <el-menu-item index="4" @click="activename = 'info'" style="padding: 0px">
              <el-icon style="width: 100%; height: 100%">
                <span>
                  <el-row>
                    <el-col :span="24">
                      <el-icon><InfoFilled /></el-icon
                    ></el-col>
                    <el-col :span="24" style="font-size: 12px">说明书</el-col>
                  </el-row>
                </span>
              </el-icon>
            </el-menu-item>

            <el-menu-item index="5" @click="activename = 'driver'" style="padding: 0px">
              <el-icon style="width: 100%; height: 100%">
                <span>
                  <el-row>
                    <el-col :span="24">
                      <el-icon><Suitcase /></el-icon
                    ></el-col>
                    <el-col :span="24" style="font-size: 12px">驱动</el-col>
                  </el-row>
                </span>
              </el-icon>
            </el-menu-item>

            <el-menu-item index="6" @click="activename = 'setup'" style="padding: 0px">
              <el-icon style="width: 100%; height: 100%">
                <span>
                  <el-row>
                    <el-col :span="24">
                      <el-icon><SoldOut /></el-icon
                    ></el-col>
                    <el-col :span="24" style="font-size: 12px">安装器</el-col>
                  </el-row>
                </span>
              </el-icon>
            </el-menu-item>

            <el-menu-item index="7" @click="activename = 'LogAnlay'" style="padding: 0px">
              <el-icon style="width: 100%; height: 100%">
                <span>
                  <el-row>
                    <el-col :span="24"
                      ><el-icon><PieChart /></el-icon
                    ></el-col>
                    <el-col :span="24" style="font-size: 12px">日志</el-col>
                  </el-row>
                </span>
              </el-icon>
            </el-menu-item>

            <el-menu-item index="8" @click="activename = 'morevue'" style="padding: 0px">
              <el-icon style="width: 100%; height: 100%">
                <span>
                  <el-row>
                    <el-col :span="24">
                      <el-icon><Menu /></el-icon
                    ></el-col>
                    <el-col :span="24" style="font-size: 12px">更多</el-col>
                  </el-row>
                </span>
              </el-icon>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main style="overflow: hidden">
          <component :is="activename"></component>
        </el-main>
      </el-container>
      <el-container v-else>
        <el-main style="height: 100%">
          <el-row style="width: 100%;padding:10px;" v-if="filelist.length > 0">
            <el-card shadow="hover" style="width: 100%">
              <el-row
                style="
                  border-bottom: solid 1px rgb(200, 200, 200);
                  height: 25px;
                  color: rgb(100, 100, 100);
                 
                "
              >
              <el-col :span="3">
                  <el-icon>
                    <el-image :src="getImageUrl('../../static/assets/icons/name.svg')"></el-image>
                  </el-icon>
                  软件名称
                </el-col>
                <el-col :span="2">
                  <el-icon>
                    <el-image :src="getImageUrl('../../static/assets/icons/bb.svg')"></el-image>
                  </el-icon>
                  系统版本
                </el-col>
                <el-col :span="3">
                  <el-icon>
                    <el-image :src="getImageUrl('../../static/assets/icons/bb.svg')"></el-image>
                  </el-icon>
                  版本
                </el-col>
                <el-col :span="2">
                  <el-icon>
                    <el-image :src="getImageUrl('../../static/assets/icons/bb.svg')"></el-image>
                  </el-icon>
                  包
                </el-col>
                <el-col :span="3">
                  <el-icon
                    ><el-image
                      :src="getImageUrl('../../static/assets/icons/time.svg')"
                    ></el-image
                  ></el-icon>
                  版本日期
                </el-col>
                     <el-col :span="3">
                  <el-icon
                    ><el-image
                      :src="getImageUrl('../../static/assets/icons/time.svg')"
                    ></el-image
                  ></el-icon>
                  上传时间
                </el-col>
                <el-col :span="2">
                  <el-icon><Download /></el-icon>
                  下载
                </el-col>
                <el-col :span="3">
                  <el-icon
                    ><el-image
                      :src="getImageUrl('../../static/assets/icons/size.svg')"
                    ></el-image
                  ></el-icon>
                  文件大小
                </el-col>
                <el-col :span="3">
                  <el-icon
                    ><el-image
                      :src="getImageUrl('../../static/assets/icons/todo.svg')"
                    ></el-image
                  ></el-icon>
                  操作
                </el-col>
              </el-row>
              <div class="listcontent">
                <template v-if="filelist.length > 0">
                  <el-row
                    style="
                      border-bottom: solid 1px rgba(200, 200, 200, 0.5);
                      height: 50px;
              
                    "
                    v-for="(sonitem, index2) in filelist"
                    :key="index2"
                  >
                   <el-col :span="3" style="padding-top: 15px">
                      <el-tooltip
                        class="box-item"
                        effect="dark"
                        :content="sonitem.zipname"
                        placement="top-start"
                      >
                        <a
                          :href="
                            '/zip/download?path=' + sonitem.path + '/' + sonitem.zipname
                          "
                        >
                        
                          <el-tag :type="
                          sonitem.path=='KSTMAIN'?'info'
                          :(sonitem.path=='KSTPXQ'?'danger'
                          :(sonitem.path=='KSTPXX'?'warning'
                          :(sonitem.path=='WTKP'?''
                          :(sonitem.path=='WTKPX'?''
                          :(sonitem.path=='KSTSCAN'?'success'
                          :(sonitem.path=='KST'?'success':'info'))))))">
                           {{ getAppName(sonitem.path)  }}-
                           {{"\t"+ sonitem.path }}
                          </el-tag></a
                        >
                      </el-tooltip>
                    </el-col>
                    <el-col :span="2" style="padding-top: 15px">
                        <el-tag :type="sonitem.os=='arm'?'danger':'success'">

                          {{ sonitem.os }}

                        </el-tag> 
                    </el-col>
                    <el-col :span="3" style="padding-top: 15px">
                      <el-tooltip
                        class="box-item"
                        effect="dark"
                        :content="sonitem.zipname"
                        placement="top-start"
                      >
                        <a
                          :href="
                            '/zip/download?path=' + sonitem.path + '/' + sonitem.zipname
                          "
                        >
                          {{ sonitem.versionchar }}</a
                        >
                      </el-tooltip>
                    </el-col>
                    <el-col :span="2" style="padding-top: 15px">
                      <el-tag :type="sonitem.versiontype=='updated'?'primary':(sonitem.versiontype=='rtm'?'success':'info')">
                          {{ 
                            sonitem.versiontype=='updated'?'更新包':(sonitem.versiontype=='rtm'?'完整包':'安装包')

                           }}
                     </el-tag>
                    </el-col>
                    <el-col :span="3" style="padding-top: 15px">
                      {{ dateFormat(sonitem.updateloadtime) }}
                    </el-col>
                       <el-col :span="3" style="padding-top: 15px">
                      {{ dateFormat2(sonitem.nowuploadtime) }}
                    </el-col>
                    <el-col :span="3" style="padding-top: 15px">
                      {{ sonitem.downloadtime }}
                    </el-col>
                    <el-col :span="2" style="padding-top: 15px">
                      {{ sonitem.zipsize }}
                    </el-col>
                    <el-col :span="3" style="padding-top: 15px">
                      <el-button type="danger" text @click="deleteRowfilelist(index2, sonitem)" v-if="false"
                        >删除</el-button
                      >
                      <el-button type="info" text>记录</el-button>
                    </el-col>
                  </el-row>
                </template>
                <template v-else>
                  <div style="padding-top: 5%; color: rgba(100, 100, 100, 0.6)">
                    暂无数据
                  </div>
                </template>

                <el-pagination
                  style="margin-top:10px;"
                  v-model:current-page="filelistobj.page"
                  v-model:page-size="filelistobj.pageSize"
                  :page-sizes="[10, 20, 30, 40]"
                  
                  layout="total,sizes, prev, pager, next"
                  :total="filelistobj.total"
                  @size-change="filelisthandleSizeChange"
                  @current-change="filelisthandleCurrentChange"
                />
              </div>
            </el-card>
          </el-row>
          <el-row style="width: 100%;padding:10px;" v-if="infolist.length > 0">
            <el-card shadow="hover" style="width: 100%"> 
                <el-row
                  style="
                    border-bottom: solid 1px rgb(200, 200, 200);
                    height: 25px;
                    color: rgb(100, 100, 100);
                    
                  "
                  >
                  <el-col :span="8">
                      <el-icon>
                        <el-image :src="getImageUrl('../../static/assets/icons/name.svg')"></el-image>
                      </el-icon>
                      文件
                    </el-col>
                    <el-col :span="4">
                      <el-icon>
                        <el-image :src="getImageUrl('../../static/assets/icons/bb.svg')"></el-image>
                      </el-icon>
                      类型
                    </el-col>
                    <el-col :span="4">
                      <el-icon><Download /></el-icon>
                      下载次数
                    </el-col>
                    <el-col :span="4">
                      <el-icon
                        ><el-image
                          :src="getImageUrl('../../static/assets/icons/size.svg')"
                        ></el-image
                      ></el-icon>
                      文件大小
                    </el-col>
                    <el-col :span="4">
                      <el-icon
                        ><el-image
                          :src="getImageUrl('../../static/assets/icons/todo.svg')"
                        ></el-image
                      ></el-icon>
                      操作
                  </el-col>
              </el-row>
              <div class="listcontent">
                <template v-if="infolist.length > 0">
                  <el-row
                    style="
                      border-bottom: solid 1px rgba(200, 200, 200, 0.5);
                      height: 40px;

                    "
                    v-for="(sonitem, index2) in infolist"
                    :key="index2"
                  >
                   <el-col :span="8" style="padding-top: 15px">
                      <el-tooltip
                        class="box-item"
                        effect="dark"
                        :content="sonitem.infoname"
                        placement="top-start"
                      >
                      <span class="textclass">
                        <a
                          :href="
                            '/info/download?path=Info/'+ sonitem.infoname
                          "
                        >
                          {{ sonitem.infoname }}</a
                        >   </span>
                      </el-tooltip>
                   
                    </el-col>
                    
                    <el-col :span="4" style="padding-top: 15px">
                     <span class="textclass">
                      {{ sonitem.type }}
                      </span>
                    </el-col>
                    <el-col :span="4" style="padding-top: 15px">
                      <span class="textclass">
                      {{ sonitem.downloadtime }}
                      </span>
                    </el-col>
                    <el-col :span="4" style="padding-top: 15px">
                     <span class="textclass">
                      {{ sonitem.infosize }}
                      </span>
                    </el-col>
                    <el-col :span="4" style="padding-top: 15px">
                      <el-button type="danger" text @click="deleteRowinfo(index2, sonitem)"
                      v-if="false"
                        >删除</el-button
                      >
                      <el-button type="info" text>记录</el-button>
                    </el-col>
                  </el-row>
                </template>
                <template v-else>
                  <div style="padding-top: 5%; color: rgba(100, 100, 100, 0.6)">
                    暂无数据
                  </div>
                </template>

                <el-pagination
                  v-model:current-page="infolistobj.page"
                  v-model:page-size="infolistobj.pageSize"
                  :page-sizes="[10, 20, 30, 40]"
                  small="small"
                  layout="total,sizes, prev, pager, next"
                  :total="infolistobj.total"
                  @size-change="infohandleSizeChange"
                  @current-change="infohandleCurrentChange"
                />
              </div>
            
            </el-card>
          </el-row>
          <el-row style="width: 100%;padding:10px;" v-if="votelist.length > 0">
            <el-card shadow="hover" style="width: 100%"> 
              <el-row
                  style="
                    border-bottom: solid 1px rgb(200, 200, 200);
                    height: 25px;
                    color: rgb(100, 100, 100);
                  "
                  >
                  <el-col :span="8">
                      <el-icon>
                        <el-image :src="getImageUrl('../../static/assets/icons/name.svg')"></el-image>
                      </el-icon>
                      文件
                    </el-col>
                    <el-col :span="4">
                      <el-icon>
                        <el-image :src="getImageUrl('../../static/assets/icons/bb.svg')"></el-image>
                      </el-icon>
                      类型
                    </el-col>
                    <el-col :span="4">
                      <el-icon><Download /></el-icon>
                      下载次数
                    </el-col>
                    <el-col :span="4">
                      <el-icon
                        ><el-image
                          :src="getImageUrl('../../static/assets/icons/size.svg')"
                        ></el-image
                      ></el-icon>
                      文件大小
                    </el-col>
                    <el-col :span="4">
                      <el-icon
                        ><el-image
                          :src="getImageUrl('../../static/assets/icons/todo.svg')"
                        ></el-image
                      ></el-icon>
                      操作
                  </el-col>
              </el-row>
              <div class="listcontent">
                <template v-if="votelist.length > 0">
                  <el-row
                    style="
                      border-bottom: solid 1px rgba(200, 200, 200, 0.5);
                      height: 40px;
                    "
                    v-for="(sonitem, index2) in votelist"
                    :key="index2"
                  >
                   <el-col :span="8" style="padding-top: 15px">
                      <el-tooltip
                        class="box-item"
                        effect="dark"
                        :content="sonitem.votename"
                        placement="top-start"
                      >
                       <span class="textclass">
                        <a
                          :href="
                            '/vote/download?path=vote/'+ sonitem.votename
                          "
                        >
                          {{ sonitem.votename }}</a
                        >
                        </span>
                      </el-tooltip>
                    </el-col>
                    
                    <el-col :span="4" style="padding-top: 15px">
                     <span class="textclass">
                      {{ sonitem.type }}
                      </span>
                    </el-col>
                    <el-col :span="4" style="padding-top: 15px">
                     <span class="textclass">
                      {{ sonitem.downloadtime }}
                      </span>
                    </el-col>
                    <el-col :span="4" style="padding-top: 15px">
                     <span class="textclass">
                      {{ sonitem.votesize }}
                      </span>
                    </el-col>
                    <el-col :span="4" style="padding-top: 15px">
                      <el-button type="danger" text @click="deleteRowvote(index2, sonitem)"
                       v-if="false"
                        >删除</el-button
                      >
                      <el-button type="info" text>记录</el-button>
                    </el-col>
                  </el-row>
                </template>
                <template v-else>
                  <div style="padding-top: 5%; color: rgba(100, 100, 100, 0.6)">
                    暂无数据
                  </div>
                </template>

                <el-pagination
                  v-model:current-page="votelistobj.page"
                  v-model:page-size="votelistobj.pageSize"
                  :page-sizes="[10, 20, 30, 40]"
                  small="small"
                  layout="total,sizes, prev, pager, next"
                  :total="votelistobj.total"
                  @size-change="votehandleSizeChange"
                  @current-change="votehandleCurrentChange"
                />
              </div>
            
            
            </el-card>
          </el-row>
          <el-row style="width: 100%;padding:10px;" v-if="empowerlist.length > 0">
            <el-card shadow="hover" style="width: 100%"> 
                <el-table :data="empowerlist" style="width:100%;min-width:400px; " :show-overflow-tooltip="true">
                    <el-table-column prop="sn" label="sn" min-width="30%" >
                          <template #header>
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
                    <el-table-column prop="lic" label="授权LIC" min-width="30%" >
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
                      <el-table-column prop="licpath" label="LIC路径" v-if="false" min-width="20%" />
                     <el-table-column prop="uploadtime" label="上传时间" min-width="20%" >
              

                     </el-table-column>
                     <el-table-column  label="下载次数" min-width="10%" >
                        <template #default="scope">
                            {{scope.row.sndownloadtime}}/ {{scope.row.licdownloadtime}}

                        </template>

                     </el-table-column>
                    <el-table-column fixed="right" label="操作" min-width="15%">
                        <template  #default="scope">
                            <el-button  v-if="false" link type="primary" style="margin-left:20px;" size="small" @click="deleteRowEmpower(scope.$index, scope.row)">
                                删除
                            </el-button>
                               <el-button link type="info">
                                更多
                            </el-button>
                        </template>
                        </el-table-column>
                </el-table>
                <el-pagination style="margin-top: 10px;" v-model:current-page="empowerlistobj.page" v-model:page-size="empowerlistobj.pageSize"
                    :page-sizes="[10, 20,30,40]" layout="total,sizes, prev, pager, next" :total="empowerlistobj.total" @size-change="empowerhandleSizeChange"
                    @current-change="empowerhandleCurrentChange" />  
            </el-card>
          </el-row>
          <el-row style="width: 100%;padding:10px;" v-if="driverlist.length > 0">
            <el-card shadow="hover" style="width: 100%"> 
                         <el-table :data="driverlist" style="width:100%;min-width:400px; " :show-overflow-tooltip="true">
                    <el-table-column prop="drivername" label="驱动包名称" min-width="30%" >
                          <template #header>
                            驱动包名称 
                          </template>

                    </el-table-column>
                    <el-table-column prop="type" label="文件类型" min-width="20%"  style="text-align:center;"/>
                    <el-table-column prop="driversize" label="文件大小" min-width="20%" />
                    <el-table-column prop="updatetime" label="更新时间" min-width="20%" >
                        <template #default="scope">
                            <span v-text="dateFormat2(scope.row.updatetime) "></span>
                        </template>

                    </el-table-column>
                    <el-table-column prop="downloadtime" label="下载次数" min-width="20%" />
                    <el-table-column fixed="right" label="操作" min-width="20%">
                        <template  #default="scope">
                            <a style="text-decoration:none; color:rgb(64,158,255); "  :href="'/driver/download?path=Driver/'+scope.row.drivername" :download="scope.row.votename">下载</a>
                            <el-button link  v-if="false" type="primary" style="margin-left:20px;" size="small" @click="deleteRowDriverlist(scope.$index, scope.row)">
                                删除
                            </el-button>
                               <el-button link type="info" style="margin-left:20px;">
                                更多
                            </el-button>
                        </template>
                        </el-table-column>
                </el-table>
                <el-pagination style="margin-top: 10px;" v-model:current-page="driverlistobj.page" v-model:page-size="driverlistobj.pageSize"
                    :page-sizes="[10, 20,30,40]" layout="total,sizes, prev, pager, next" :total="driverlistobj.total" @size-change="driverlisthandleSizeChange"
                    @current-change="driverlisthandleCurrentChange" />
                      
            
            
            </el-card>
          </el-row>
          <el-row style="width:100%;height:100%;text-align:center;" v-if="filelist.length==0&&infolist.length==0&&empowerlist.length==0&&driverlist.length==0" >
            <el-image :src="getImageUrl('../../static/assets/icons/empty.svg')" style="width:500px;margin:auto;"></el-image>
          
          </el-row>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" src="./ts/Helloworld.ts"></script>

<style scoped>
@import url("./css/helloworld.css");
</style>
