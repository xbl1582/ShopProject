<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-upload
          class="upload-demo"
          ref="upload"
          drag
          :auto-upload="false"
          :on-preview="handlePreview"
           :on-change="handleChange"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          :file-list="fileList"
          :on-progress="handleprogress"
          :http-request="myUploadFile"
          accept=".gz,.tar,.zip,.tar.gz"
          multiple
        >
          <svg
            t="1702281594898"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="9624"
            width="200"
            height="200"
          >
            <path
              d="M554.666667 550.4V768h192c85.333333-8.533333 149.333333-81.066667 149.333333-170.666667 0-93.866667-76.8-170.666667-170.666667-170.666666h-4.266666c-21.333333-98.133333-106.666667-170.666667-209.066667-170.666667-119.466667 0-213.333333 93.866667-213.333333 213.333333-72.533333 8.533333-128 72.533333-128 149.333334C170.666667 699.733333 238.933333 768 320 768H512v-217.6l-98.133333 98.133333-29.866667-29.866666 149.333333-149.333334 149.333334 149.333334-29.866667 29.866666-98.133333-98.133333z m0 217.6v42.666667h-42.666667v-42.666667h-64v42.666667h-128C213.333333 810.666667 128 725.333333 128 618.666667c0-85.333333 55.466667-157.866667 128-183.466667C273.066667 311.466667 379.733333 213.333333 512 213.333333c110.933333 0 209.066667 72.533333 243.2 170.666667 102.4 12.8 183.466667 102.4 183.466667 213.333333s-85.333333 200.533333-192 213.333334h-128v-42.666667H554.666667z"
              fill="#444444"
              p-id="9625"
            ></path>
          </svg>
          <div class="el-upload__text">拖拽文件上传 或者<em> 选择文件上传</em></div>
          <template #tip>
            <div class="el-upload__tip">
              <el-button
                class="ml-3"
                style="float: right; margin-right: 10px; margin-bottom: 40px"
                type="success"
                @click="submitUpload"
              >
                提交
              </el-button>
              选择.gz，.tar，.zip，.tar.gz等文件上传
              <br />
              <br />
              <br />
              <br />

              <div
                style="
                  width: 100%;
                  height: 1px;
                  background-color: rgba(200, 200, 200, 1);
                  margin-top: 10px;
                "
              />
            </div>
          </template>
        </el-upload>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" src="./ts/uploaddrivervue.ts"></script>

<style scoped></style>
