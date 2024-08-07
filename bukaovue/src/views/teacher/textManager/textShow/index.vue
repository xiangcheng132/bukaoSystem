<template>
  <el-button type="primary" @click="createExam">新建试卷</el-button>
  <div>
    <el-table :data="examTableList" style="width: 100%">
      <!-- <el-table-column fixed prop="id" label="Id" width="300" /> -->
      <el-table-column prop="name" label="考卷名" width="300" />
      <el-table-column prop="comment" label="考卷描述" width="300" />
      <el-table-column prop="createTime" label="考试地点" width="300" />
      <el-table-column prop="beginTime" label="开始时间" width="400" />
      <el-table-column prop="endTime" label="结束时间" width="400" />
      <el-table-column fixed="right" label="操作" min-width="120"> 
        <template #default="scope">
          <el-button link type="primary" size="small" @click.prevent="modifyExam(scope.$index)">
            修改
          </el-button>
          <el-button link type="primary" size="small"  @click.prevent="deleteExam(scope.$index)">删除</el-button>
        </template>
    </el-table-column>
  </el-table>
  </div>
</template>
<script setup>
import {ref,reactive,onMounted} from 'vue'
import { deleteExamById,getExamInfoByTeaId } from "@/api/exam"
import {setStorage,getStorage} from "@/utils/storage.js";
import { ElMessage, ElMessageBox } from "element-plus";
import { useRoute,useRouter } from "vue-router";

// 试卷数据
let examTableList = reactive([])
const router = useRouter();

// 修改试卷
function modifyExam(index){
  // 传入试卷id
  let id = examTableList[index].id;
  router.push({
    path: '/teacher/textManager/textDetail', 
    query: { 
      examId: id, 
    }
  })
  // 跳转
}

// 删除试卷
function deleteExam(index){
 // 根据试卷id删除 
 ElMessageBox.confirm("确认删除试卷信息？", "警告", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      deleteExamById(examTableList[index].id)
        .then((res) => {
          if ((res.status = "201")) {
          refreshTable(getStorage("token"));
            ElMessage({
              type: "success",
              message: "删除成功",
            });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消删除",
      });
    });
}

// 新建试卷
function createExam(){
  router.push("/teacher/textManager/textUp")
}

// 数据初始化
async function refreshTable(id){
  examTableList.splice(0, examTableList.length);
  await getExamInfoByTeaId(id).then(v=>{
      v.data.forEach(value => {
          examTableList.push(value);
      });
   })
}
onMounted(()=>{
  refreshTable(getStorage("token"));
})
</script>
    



<style lang = 'less'>
.el-button--primary {
  margin: 10px;
}
.el-table td.el-table__cell, .el-table th.el-table__cell.is-leaf{
  text-align: center;
}
</style>