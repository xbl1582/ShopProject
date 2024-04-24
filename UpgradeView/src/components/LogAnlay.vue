<template>
  <div style="width: 100%; height: 100%; overflow: hidden">
    <el-row :gutter="12" style="height: 100%">
      <el-col :span="8">
        <el-card shadow="always" class="cardclass" v-loading="ruanjianobj.ruanjian">
          <el-row>
            <el-col :span="12"> 软件下载统计</el-col>
            <el-col :span="12" style="text-align: right">
              <el-button text>
                <el-icon><FullScreen /></el-icon>
              </el-button>
              <el-button text>
                <el-icon><Download /></el-icon>
              </el-button>
            </el-col>
          </el-row>
          <div
            style="min-width: 300px; min-height: 300px; width: 100%; height: 100%"
            id="ruanjian"
          ></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="always" class="cardclass" v-loading="xuanpiaoobj.xuanpiao">
          <el-row>
            <el-col :span="12">选票下载统计</el-col>
            <el-col :span="12" style="text-align: right">
              <el-button text>
                <el-icon><FullScreen /></el-icon>
              </el-button>
              <el-button text>
                <el-icon><Download /></el-icon>
              </el-button>
            </el-col>
          </el-row>

          <div
            style="min-width: 300px; min-height: 300px; width: 100%; height: 100%"
            id="xuanpiao"
          ></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="always" class="cardclass" v-loading="shuomishuobj.shuomishu">
          <el-row>
            <el-col :span="12"> 说明书下载统计</el-col>
            <el-col :span="12" style="text-align: right">
              <el-button text>
                <el-icon><FullScreen /></el-icon>
              </el-button>
              <el-button text>
                <el-icon><Download /></el-icon>
              </el-button>
            </el-col>
          </el-row>
          <div
            style="min-width: 300px; min-height: 300px; width: 100%; height: 100%"
            id="shuomishu"
          ></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="always" class="cardclass" v-loading="qudonobj.qudon">
          <el-row>
            <el-col :span="12"> 驱动下载统计</el-col>
            <el-col :span="12" style="text-align: right">
              <el-button text>
                <el-icon><FullScreen /></el-icon>
              </el-button>
              <el-button text>
                <el-icon><Download /></el-icon>
              </el-button>
            </el-col>
          </el-row>
          <div
            style="min-width: 300px; min-height: 300px; width: 100%; height: 100%"
            id="qudon"
          ></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="always" class="cardclass" v-loading="anzhuanqiobj.anzhuanqi">
          <el-row>
            <el-col :span="12"> 安装器下载统计</el-col>
            <el-col :span="12" style="text-align: right">
              <el-button text>
                <el-icon><FullScreen /></el-icon>
              </el-button>
              <el-button text>
                <el-icon><Download /></el-icon>
              </el-button>
            </el-col>
          </el-row>
          <div
            style="min-width: 300px; min-height: 300px; width: 100%; height: 100%"
            id="anzhuanqi"
          ></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="always" class="cardclass">
          <el-row>
            <el-col :span="12"> 系统执行日志</el-col>
            <el-col :span="12" style="text-align: right">
              <el-button text>
                <el-icon><FullScreen /></el-icon>
              </el-button>
              <el-button text>
                <el-icon><Download /></el-icon>
              </el-button>
            </el-col>
          </el-row>
          <ul v-infinite-scroll="initsystem" class="infinite-list" style="overflow: auto">
            <li v-for="i in count" :key="i" class="infinite-list-item">{{ i }}</li>
          </ul>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" src="./ts/LogAnaly.ts"></script>

<style scoped>
@import url("./css/LogAnaly.css");
</style>
