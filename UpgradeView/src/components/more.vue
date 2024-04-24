<template>
  <div style="height: 200vh; padding: 5%">
    <el-row :gutter="20" v-if="showifame">
      <el-col :span="24">
        <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
          <el-tab-pane v-for="(item, index) in mycompenttype" :key="index" :label="item.typechar" :name="item.type">
              <el-row >
                <el-col :span="6" v-for="(item2,index2) in item.mycompentlist">
                    <el-card  class="box-card" style="max-width:320px;max-height:250px;min-height:120px">
              <el-row style="height:4rem">
                <el-col :span="4" >
                    <el-image :src="item2.logosrc" :onload="setimagepath('/image/icon?path='+item2.logosrc,index,index2)" width="100%"></el-image>
                </el-col>
                <el-col :span="12">
                  <span style="font-weight: bold;">{{ item2.title }}</span>
                </el-col>
                <el-col :span="8">
                  <span>[{{ item2.typechar }}]</span>

                </el-col>
              </el-row>
              <el-row>
                {{ item2.desc }}
              </el-row>
            </el-card>
                </el-col>
                <el-col :span="6">
                  <el-card  class="box-card" 
                  style="max-width:320px;max-height:250px;min-height:120px;text-align:center;"
                  body-style="padding:0px;height:100%;;min-height:120px;"
                  >
                    <el-button type="primary" style="width:100%;height:100%;;min-height:120px; " text  @click="addcompdialog=!addcompdialog">
                        <el-icon style="font-size:30px;"><Plus /></el-icon>

                    </el-button>
                  </el-card>
                </el-col>
              </el-row>
          
              
          </el-tab-pane>

        </el-tabs>
      </el-col>
    </el-row>
    <!-- 
    <el-row :gutter="20">
      <el-col :span="6" style="text-align: center">
        <el-card shadow="always" style="height: 280px">
          <el-button style="height: 100%; padding: 5px" @click="uploadfiletopdf()">
            <el-row style="height: 100%; padding: 5px">
              <el-col :span="24" style="height: 80%">
                <el-image
                  :src="getImageUrl('../../static/assets/icons/icon-p1.svg')"
                  style="width: 50%; height: 50%"
                ></el-image>
              </el-col>
              <el-col :span="24" style="height: 20%">
                <el-text type="danger">文件转换</el-text>
              </el-col>
            </el-row>
          </el-button>
        </el-card>
      </el-col>
      <el-col :span="6" style="text-align: center">
        <el-card shadow="always" style="height: 280px">
          <el-button style="height: 100%; padding: 5px" @click="uploadimagetopdf()">
            <el-row style="height: 100%; padding: 5px">
              <el-col :span="24" style="height: 80%">
                <el-image
                  :src="getImageUrl('../../static/assets/icons/icon-p2.svg')"
                  style="width: 67%; height: 67%"
                ></el-image>
              </el-col>
              <el-col :span="24" style="height: 20%">
                <el-text type="success"> 图片转pdf</el-text>
              </el-col>
            </el-row>
          </el-button>
        </el-card>
      </el-col>
      <el-col :span="6" style="text-align: center">
        <el-card shadow="always" style="height: 280px">
          <el-button style="height: 100%; padding: 5px" @click="uploadimagetotxt()">
            <el-row style="height: 100%; padding: 5px">
              <el-col :span="24" style="height: 80%">
                <el-image
                  :src="getImageUrl('../../static/assets/icons/icon-p3.svg')"
                  style="width: 50%; height: 50%"
                ></el-image>
              </el-col>
              <el-col :span="24" style="height: 20%">
                <el-text type="primary">进制</el-text>
              </el-col>
            </el-row>
          </el-button>
        </el-card>
      </el-col>
      <el-col :span="6" style="text-align: center">
        <el-card shadow="always" style="height: 280px">
          <el-button style="height: 100%; padding: 5px">
            <el-row style="height: 100%; padding: 5px">
              <el-col :span="24" style="height: 80%">
                <el-image
                  :src="getImageUrl('../../static/assets/icons/icon-p4.svg')"
                  style="width: 50%; height: 50%"
                ></el-image>
              </el-col>
              <el-col :span="24" style="height: 20%">
                <el-text type="warning"> 数据备份</el-text>
              </el-col>
            </el-row>
          </el-button>
        </el-card>
      </el-col>
    </el-row> -->

    <el-dialog v-model="addcompdialog" title="添加加服务" width="50%" style="height: 38%"
      :before-close="handleClose">
      <el-form :model="form" label-width="120px"   ref="ruleFormRef"    status-icon>
        <el-form-item label="服务标题：" :rules="[
                { required: true, message: '请输入标题', trigger: ['blur', 'change']},
                { min: 3, max: 10, message: '标题长度3~10个字符', trigger:['blur', 'change'] }
              ]">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="服务类型">
          <el-select v-model="form.type" placeholder="选择类型">
            <el-option label="默认" :value="9" />
            <el-option label="开发类" :value="1" />
            <el-option label="文档类" :value="2" />
            <el-option label="图片类" :value="3" />
            <el-option label="音频类" :value="4" />
            <el-option label="视频类" :value="5" />
            <el-option label="运维类" :value="6" />
            <el-option label="设计类" :value="7" />
            <el-option label="经验学术类" :value="8" />        
          </el-select>
        </el-form-item>
        <el-form-item label="服务路径："  :rules="[
                { required: true, message: '请输入标题', trigger: ['blur', 'change']}
              ]">
          <el-input v-model="form.urlpath" placeholder="http://127.0.0.1:9988" />
        </el-form-item>
        <el-form-item label="服务描述">
          <el-input v-model="form.desc" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit()">创建</el-button>
          <el-button @click="closeDialog()">取消</el-button>
        </el-form-item>
      </el-form>
  
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./ts/more.ts"></script>

<style scoped>
:deep(.el-dialog__body) {
  height: 75%;
}
</style>
