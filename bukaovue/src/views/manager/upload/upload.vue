<template>
  <div class="uploadBox">
    <!-- 返回主页 -->
    <div class="homeBox">
      <router-link :to="{ path: '/manager/classAdd'}">
        <el-button placement="top">
          返回列表页面
        </el-button>
      </router-link>
    </div>
    <!-- 上传文件按钮 -->
    <div class="buttonBox">
      <el-upload
        action
        accept=".xlsx,.xls"
        :auto-upload="false"
        :show-file-list="false"
        @change="handle"
      >
        <el-button type="primary" slot="trigger">选取 Excel 文件</el-button>
      </el-upload>
      <el-button type="success" @click="submit">采集数据提交</el-button>
    </div>

    <!-- 解析出来的数据 -->
    <div class="tableBox" v-show="show.valueOf">
      <h3>
        <i class="el-icon-info">
          以下是采集完成的数据，请您检查无误后，点击“采集数据提交”按钮上传至服务器</i
        >
      </h3>
      <el-table :data="tempData" border style="width: 100%" :height="height.valueOf">
        <el-table-column
          prop="studentId"
          label="学生编号"
          min-width="150"
        ></el-table-column>
        <el-table-column
          prop="username"
          label="姓名"
          min-width="150"
        ></el-table-column>
        <el-table-column
          prop="classId"
          label="班级编号"
          min-width="150"
        ></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import * as xlsx from 'xlsx';
import { readFile, character, delay } from '@/assets/utils'
import { ElLoading, ElMessage } from 'element-plus'
import{getExamClassStudentByClassId,getExamClassStudentsById,getExamClassStudentByStuId,createExamClassStudent,updateExamClassStudent,deleteExamClassStudent}from"@/api/examClassStudent";

// 响应式数据
const height = ref(document.documentElement.clientHeight - 130)
const tempData = ref([])
const show = ref(false)

// 采集 EXCEL 数据
const handle = async (e) => {
  const file = e.raw;
  if (!file) return;
  show.value = false
  const loading = ElLoading.service({
    text: '请您稍等片刻，系统正在处理中...',
    background: 'rgba(0,0,0,.5)'
  })
  await delay(300)
  // 读取FILE中的数据
  const data = await readFile(file)
  const workbook = xlsx.read(data, { type: 'binary' })
  const worksheet = workbook.Sheets[workbook.SheetNames[0]]
  const list = xlsx.utils.sheet_to_json(worksheet)
  console.log(list)
// 把读取出来的数据变为可以提交为服务器的数据格式
  const arr = []
  // const oldData = JSON.parse(window.localStorage.getItem('excel') || '[]')
  // let index = oldData.length
  list.forEach((item) => {
    const obj = {}
    for (const key in character) {
      if (!character.hasOwnProperty(key)) break;
      let v = character[key]
        const text = v.text
        const type = v.type
      v = item[text] || ''
      if (type === 'string') v = String(v)
      if (type === 'number') v = Number(v)
      obj[key] = v
    }
    // obj.id = ++index
    // obj.time = new Date()
    arr.push(obj)
  });
  console.log(arr)

  await delay(300)

  // 展示到页面中
  show.value = true
  tempData.value = arr
  console.log(tempData.value)
  loading.close()
}

// // 提交数据给服务器
// const submit = () => {
//   if (this.tempData.length <= 0) {
//     return this.$message({
//       message: '请您先选择 EXCEL 文件！',
//       type: 'warning',
//       showClose: true
//     })
//   }
//   //遍历数组,提交每项数据
//   createExamClassStudent
// }
// 提交数据给服务器
const submit = async () => {
  if (tempData.value.length <= 0) {
    return ElMessage({
      message: '请您先选择 EXCEL 文件！',
      type: 'warning',
      showClose: true
    });
  }
  // 遍历数组, 提交每项数据
  for (const dataItem of tempData.value) {
    try {
      console.log(dataItem.classId);
      const classId = dataItem.classId
      const studentId = dataItem.studentId
      await createExamClassStudent(classId,studentId)
        .then((res) => {
          console.log("新建学生班级记录");
          ElMessage.success('提交成功');
        })
    } catch (error) {
      console.error('提交失败:', error);
      ElMessage({
        message: `提交失败: ${error.message}`,
        type: 'error',
        showClose: true
      });
    }
  }
}

</script>

<style scoped lang="less">
.homeBox {
  position: fixed;
  top: 10px;
  right: 20px;
  z-index: 9999;
  font-size: 40px;
}

.buttonBox {
  padding: 15px;
  display: flex;
  width: 35%;
  justify-content: flex-start;
  & .el-button {
    margin-right: 20px !important;
  }
}

.tableBox {
  padding: 0 15px;
  h3 {
    font-size: 18px;
    color: #f56c6c;
    padding-bottom: 15px;
  }
}
</style>
