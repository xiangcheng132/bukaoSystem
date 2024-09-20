<template>
  <div class="container">
    <el-form :model="classForm">
      <el-form-item label="班级名称">
        <el-input v-model="classForm.name"></el-input>
      </el-form-item>
      <el-form-item label="班级介绍">
        <el-input v-model="classForm.comment"></el-input>
      </el-form-item>
      <el-form-item label="任课教师Id">
        <el-input v-model="classForm.teacherId"></el-input>
      </el-form-item>
      <el-form-item label="任课教师">
        <el-input v-model="classForm.username" readonly></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateClass">修改</el-button>
      </el-form-item>
    </el-form>
    <div class="studentlist" >
      <el-table  :data="studentList.values" border style="width: 100% ">
      <el-table-column prop="studentId" label="编号"></el-table-column>
      <el-table-column prop="studentname" label="学生姓名"></el-table-column>
      <el-table-column label="操作" width:120px >
        <template v-slot="scope">
          <el-button type="danger" @click="removeStudent(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    </div>
    <div>
      <el-button type="primary"  @click="openAddStudentDialog">添加学生</el-button> 
      <router-link :to="{path:'/manager/upload', query:{id: classid }}">
        <el-button type="primary" style="margin-left: 10px; text-align: center;" > Excle导入 </el-button>
      </router-link>
    </div>
    <!-- 添加学生对话框 -->
    <el-dialog title="添加学生" v-model="showAddStudentDialog">
      <el-table :data=" allStudents.values " style="width: 100%" @selection-change="handleSelectionChange" v-loading="loading">
        <el-table-column type="selection"></el-table-column>
        <el-table-column prop="id" label="学生编号"></el-table-column>
        <el-table-column prop="username" label="学生姓名"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showAddStudentDialog = false">取消</el-button>
        <el-button type="primary" @click="addSelectedStudents">添加</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { findAllStudent, deleteUser } from '@/api/examUser.js';
import{getAllClass,getClassById,createClass,updateClassInfo,deleteClass}from"@/api/examClass";
import { 
  getExamClassStudentsById, 
  createExamClassStudent, 
  deleteExamClassStudent,
} from "@/api/examClassStudent";
import {getExamClassTeacher,createExamClassTeacher,getExamClassTeachersnameByClassId,updateExamClassTeacher} from "@/api/examClassTeacher";
import { useRoute } from 'vue-router';

const route = useRoute();
const classid = route.query.id
const studentList = reactive({ values: [] });
const showAddStudentDialog = ref(false);

const classForm = reactive({
  name: "",
  comment: "",
  teacherId:null,
  username: "",
});

// 获取学生信息
const getStudentList = (id) => {
  getExamClassStudentsById(id)
    .then((res) => {
      console.log("获取学生信息成功");
      studentList.values = res.data;
    })
    .catch((err) => {
      console.error("获取学生信息失败", err);
    });
};

// 刷新班级信息
const refreshClassInfo = () => {
  getClassById(route.query.id)
    .then((res) => {
      console.log("刷新班级信息成功");
      console.log(route.query.id);
      classForm.name = res.data.name;
      classForm.comment = res.data.comment;
      // 拿到教师名称（后端方法更改）
      getExamClassTeachersnameByClassId(route.query.id)
        .then((teacherRes) => {
          if (teacherRes.data && teacherRes.data.length > 0) {
            const teacher = teacherRes.data[0]; // 获取第一个教师的信息（或根据需求处理多个教师）
            classForm.teacherId = teacher.teacherId; // 确保 teacher 对象中有 teacherId 字段
            classForm.username = teacher.username; // 确保 teacher 对象中有 teachername 字段
          } else {
            // 如果没有找到教师，处理空数据的情况
            classForm.teacherId = null;
            classForm.username = '';
          }
        })
    })
    .catch((err) => {
      console.error("刷新班级信息失败", err);
    });
};

const resfrom = reactive({ values: [] });
// 获取中间表 id
const getExamClassTeacherId = async () => {
  try {
    const res = await getExamClassTeacher(route.query.id);
    resfrom.values = res.data;

    if (resfrom.values.length > 0) {
      console.log('ID为' + resfrom.values[0].id);
    } else {
      console.log('没有可用的教师数据');
    }
  } catch (error) {
    console.error('获取中间表 ID 失败', error);
  }
};

// 更新班级信息
const updateClass = async () => {
  try {
    // 更新班级信息
    await updateClassInfo({
      id: route.query.id,
      name: classForm.name,
      comment: classForm.comment,
    });
    // 重新获取中间表 ID
    await getExamClassTeacherId();
    if (resfrom.values.length > 0) {
      // 更新班级教师信息
      await updateExamClassTeacher(resfrom.values[0].id, route.query.id, classForm.teacherId);
      console.log('更新班级信息成功', classForm);
      refreshClassInfo();
    } else {
      await createExamClassTeacher({
        classId: route.query.id,
        teacherId: classForm.teacherId,
      });
      refreshClassInfo();
    }
  } catch (error) {
    console.error('更新班级信息失败', error);
  }
};


// 删除学生
const removeStudent = (studentId) => {
  deleteExamClassStudent(studentId)
    .then(() => {
      console.log("删除学生成功");
      getStudentList(route.query.id); 
    })
    .catch((err) => {
      console.error("删除学生失败", err);
    });
};

const allStudents = reactive({values:[]})
const loading = ref(false);

// 打开对话框并加载学生数据
const openAddStudentDialog = () => {
  showAddStudentDialog.value = true;
  loadAllStudents();
};
// 加载所有学生数据
const loadAllStudents = () => {
  loading.value = true;
  findAllStudent(route.query.id)
    .then((res) => {
      allStudents.values = res.data;
    })
    .catch((err) => {
      console.error('加载学生数据失败', err);
    })
    .finally(() => {
      loading.value = false;
    });
};

const selectedStudents = ref([]);

// 选择学生的处理函数
const handleSelectionChange = (selection) => {
  selectedStudents.value = selection;  // 更新选中学生的列表
  console.log('选择的学生:', selection);
};

// 添加选中学生的函数
const addSelectedStudents = async () => {
  // 检查是否有学生被选中
  if (selectedStudents.value.length === 0) {
    console.log('没有学生被选中');
    return;
  }

  // 遍历选中的学生并调用 createExamClassStudent
  for (const student of selectedStudents.value) {
    try {
      const classId = route.query.id; // 假设 classId 是通过路由参数获取的
      const studentId = student.id; // 从选中的学生中获取 studentId
      await createExamClassStudent(classId, studentId);
      console.log(`成功添加学生: ${studentId}`);
      
    } catch (error) {
      console.error(`添加学生 ${student.id} 失败:`, error);
    }
  }
  // 关闭添加学生对话框
  showAddStudentDialog.value = false;
  // 刷新学生列表
  getStudentList(route.query.id);
  // 打印出当前选中的所有学生
  console.log('已添加的学生:', selectedStudents.value);
};

onMounted(() => {
  const id = route.query.id;
  if (id && !isNaN(parseInt(id))) {
    getStudentList(id);
    refreshClassInfo();
  } 
});
</script>
<style scoped>

.container {
  padding: 20px 40px;
}

.studentlist {
  width: 90%;
  margin-bottom: 20px;
}

.button-container {
  margin-bottom: 20px;
}

</style>