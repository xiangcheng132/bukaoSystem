<template>
  <div>
    <el-form style="margin:15px 10px 0px;">
      <!-- <el-form-item label="用户ID:">   :model="queryParam" ref="queryForm" :inline="true" 
        <el-input v-model="queryParam.id" placeholder="请输入用户ID"></el-input>
      </el-form-item>
      <el-form-item label="用户名：">
        <el-input v-model="queryParam.userName"  placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="身份">
      <el-select
        v-model="queryParam.role"
        placeholder="选择身份"
        clearable
        style="width: 100px;"
      >
        <el-option label="教师" value="teacher" />
        <el-option label="学生" value="student" />
        <el-option label="管理员" value="admin" />
      </el-select>
    </el-form-item> -->
      <el-form-item>
        <el-button style="margin-right: 15px;" type="primary" @click="submitForm">查询</el-button>
        <router-link :to="{path:'/manager/classAdd'}" class="link-left">
          <el-button type="primary">添加班级</el-button>
         </router-link>
      </el-form-item>
    </el-form>
    <el-table :data="item.values" style="width: 100%">
      <el-table-column prop="name" label="班级名称"></el-table-column>
      <el-table-column prop="comment" label="班级介绍"></el-table-column>
      <el-table-column prop="teacher" label="班主任"></el-table-column>
      <el-table-column prop="studentCount" label="学生人数"></el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button @click="openStudentDialog(scope.row)">查看学生</el-button>
          <el-button @click="openEditDialog(scope.row)">编辑</el-button>
          <el-button type="danger" @click="deleteClass(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="studentDialogVisible" title="学生信息">
      <el-table :data="studentList.values" style="width: 100%">
        <el-table-column prop="studentId" label="编号"></el-table-column>
        <el-table-column prop="studentname" label="学生姓名"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="danger" @click="removeStudent(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="dialog-footer" >
        <router-link :to="{path:'/manager/classAdd'}" class="link-left">
          <el-button type="primary">添加学生</el-button> 
        </router-link>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, toRaw } from "vue";
import{getAllClass,getClassById,createClass,updateClassInfo,deleteClass}from"@/api/examClass";
import{getExamClassStudentByClassId,getExamClassStudentsById,getExamClassStudentByStuId,createExamClassStudent,updateExamClassStudent,deleteExamClassStudent}from"@/api/examClassStudent";
import { getExamClassTeacher } from "@/api/examClassTeacher";
import { useStore } from "vuex";
import { getAllUser ,getAllUserByUsername,getAllUserById,getUserByIdAndUsername, deleteUser} from '@/api/examUser.js';
const store = useStore();

const item = reactive({ values: [] });

const debounce = (fn, delay) => {
  let timer;
  return (...args) => {
    if (timer) {
      clearTimeout(timer);
    }
    timer = setTimeout(() => {
      fn(...args);
    }, delay);
  };
};
const resizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends resizeObserver {
  constructor(callback) {
    callback = debounce(callback, 200);
    super(callback);
  }
};
const form = reactive({
  id: '',
  name: '',
  comment: '',
  teacher: '',
  studentCount: '',
})

const refreshClassInfo = () => {
  getAllClass()
    .then((res) => {
      console.log("刷新班级信息成功");
      item.values= res.data;
    })
    .catch((err) => {});
}
onMounted(() => {
  refreshClassInfo();
});
const studentList = reactive({ values: [] });
const studentDialogVisible = ref(false)
const openStudentDialog = (row) => {
  getExamClassStudentsById(row.id)
    .then((res) => {
      console.log("获取学生信息成功");
      studentList.values = res.data;
      studentDialogVisible.value = true;
    })
    .catch((err) => {
      console.error("获取学生信息失败", err);
    });
};

// const openAddDialog = () => {
//   form.value = { id: '', name: '', teacher: '', studentCount: '' }
//   dialogVisible.value = true
// }

// const openEditDialog = (row) => {
//   form.value = { ...row }
//   dialogVisible.value = true
// }

// const saveClass = () => {
//   if (form.value.id) {
//     const index = classList.value.findIndex(item => item.id === form.value.id)
//     if (index !== -1) {
//       classList.value.splice(index, 1, form.value)
//     }
//   } else {
//     form.value.id = Date.now()
//     classList.value.push(form.value)
//   }
//   dialogVisible.value = false
// }

// const deleteClass = (id) => {
//   classList.value = classList.value.filter(item => item.id !== id)
// }
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
