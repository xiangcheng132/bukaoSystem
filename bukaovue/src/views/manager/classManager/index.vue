<template>
  <div>
    <el-form :model="queryParam" ref="queryForm" :inline="true" style="margin:15px 10px 0px;">
      <el-form-item label="班级ID:">
        <el-input v-model="queryParam.id" placeholder="请输入班级ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button style="margin-right: 15px;" type="primary" @click="submitForm">查询</el-button>
        <el-button type="primary" @click="showCreateClassDialog = true" >创建班级</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="item.values" style="width: 100%">
      <el-table-column prop="name" label="班级名称"></el-table-column>
      <el-table-column prop="comment" label="班级介绍"></el-table-column>
      <el-table-column prop="teacher" label="班主任"></el-table-column>
      <!-- <el-table-column prop="studentCount" label="学生人数"></el-table-column> -->
      <el-table-column label="操作">
        <template v-slot:default="{ row }"> <!-- v-slot="scope" -->
          <router-link :to="{path:'/manager/classAdd', query:{id:row.id}}" class="link-left">
            <el-button>编辑</el-button>
          </router-link>
          <el-button type="danger" @click="deleteClassById(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 创建班级对话框 -->
    <el-dialog title="创建班级"  v-model="showCreateClassDialog">
      <el-form :model="newClassForm">
        <el-form-item label="班级名称">
          <el-input v-model="newClassForm.name"></el-input>
        </el-form-item>
        <el-form-item label="班级介绍">
          <el-input v-model="newClassForm.comment"></el-input>
        </el-form-item>
        <el-form-item label="教师id">
          <el-input v-model="newClassForm.teacherId"></el-input>
        </el-form-item>
        <!-- <el-form-item label="班主任">
          <el-input v-model="newClassForm.teacher"></el-input>
        </el-form-item> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showCreateClassDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCreateClass ">创建</el-button>
      </span>
    </el-dialog>
    <!-- <el-dialog v-model="studentDialogVisible" title="学生信息">
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
          <el-button type="primary">添加学生</el-button> 
      </div>
    </el-dialog> -->
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, toRaw } from "vue";
import { ElMessage } from 'element-plus';
import{getAllClasswithteacher,getAllClass,getClassById,createClass,updateClassInfo,deleteClass}from"@/api/examClass";
import{getExamClassStudentByClassId,getExamClassStudentsById,getExamClassStudentByStuId,createExamClassStudent,updateExamClassStudent,deleteExamClassStudent}from"@/api/examClassStudent";
import {createExamClassTeacher,deleteExamClassTeacher} from "@/api/examClassTeacher";
import { useStore } from "vuex";
const store = useStore();

const item = reactive({ values: [] });
const showCreateClassDialog = ref(false);
const newClassForm = reactive({
  name: '',
  comment: '',
  teacherId: '',
  teacher: '',
});

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
  // studentCount: '',
})

const refreshClassInfo = () => {
  getAllClasswithteacher()
    .then((res) => {
      console.log("刷新班级信息成功");
      item.values= res.data;
    })
    .catch((err) => {});
}
onMounted(() => {
  refreshClassInfo();
});
// 创建班级
const handleCreateClass  = () => {
  createClass({name:newClassForm.name,comment:newClassForm.comment})
    .then((res) => {
      console.log("创建班级成功");
      console.log(res.data);
      createExamClassTeacher({classId:res.data,teacherId:newClassForm.teacherId})
        .then((res) => {
          console.log("新建教师班级记录");
          ElMessage.success('创建班级成功');
          showCreateClassDialog.value = false;
          refreshClassInfo();
        })
    })
    .catch((err) => {
      ElMessage.error('创建班级失败');
      console.error("创建班级失败", err);
    });
}
// 删除班级
const deleteClassById = (classId) => {
  deleteClass(classId)
    .then(() => {
      console.log("删除班级成功");
      // 删除教师课程中间表的联系
      deleteExamClassTeacher(classId)
        .then(() => {
          console.log("删除教师课程关系成功");
          // 批量删除课程和学生关系
          deleteExamClassStudent(classId)
            .then(() => {
              ElMessage.success('删除班级及其关联成功');
              console.log("删除学生课程关系成功");
              refreshClassInfo();
            })
            .catch((err) => {
              console.error("删除学生课程关系失败", err);
              ElMessage.error('删除学生课程关系失败');
            });
        })
        .catch((err) => {
          console.error("删除教师课程关系失败", err);
          ElMessage.error('删除教师课程关系失败');
        });
    })
    .catch((err) => {
      console.error("删除班级失败", err);
      ElMessage.error('删除班级失败');
    });
};
const queryParam = ref({
  id: '',
  username: '',
  role: '',
  pageIndex: 1,
  pageSize:10
});

</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
