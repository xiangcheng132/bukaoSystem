<template>
  <div class="app-container" style="margin: 50px;">
    <el-form v-model="form" ref="userForm" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="用户名：" prop="username" required>
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="账号：" prop="account" required>
        <el-input v-model="form.account"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="password" required>
        <el-input v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item label="身份：" prop="role" required>
        <el-select v-model="form.role" placeholder="身份">
          <el-option label="教师" value="teacher" />
          <el-option label="学生" value="student" />
          <el-option label="管理员" value="admin" />
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱：" prop="email">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="手机号：" prop="phone">
        <el-input v-model="form.phone"></el-input>
      </el-form-item>
      <el-form-item label="性别：" prop="sex">
        <el-select v-model="form.sex" placeholder="性别" clearable>
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
          <el-option label="未知" value="未知" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属班级：" prop="className">
        <el-select v-model="form.classId" multiple collapse-tags collapse-tags-tooltip placeholder="所属班级">
          <el-option v-for="v in classList" :key="v.id" :label="v.name" :value="v.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-if="form.id === ''" type="primary" @click="addForm">添加</el-button>
        <el-button v-else type="primary" @click="submitForm">修改</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { getAllUserById, createUser, updateUserInfo } from '@/api/examUser.js';
import { getAllClass, getClassById } from '@/api/examClass.js';
import { createExamClassTeacher, getExamClassTeacherById, updateExamClassTeacher,deleteExamClassTeacher} from '@/api/examClassTeacher.js';
import { createExamClassStudent, getExamClassStudentByStuId, updateExamClassStudent } from '@/api/examClassStudent.js';
const regEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const phoneRegex = /^(\+\d{1,3}[- ]?)?\(?(\d{3})\)?[-. ]?(\d{3})[-. ]?(\d{5})$/;
const route = useRoute();
const router = useRouter();
const classList = reactive([]);
let IsEmpty = reactive(false);
const form = reactive({
  id: '',
  username: '',
  account: '',
  password: '',
  role: '',
  email: '',
  phone: '',
  sex: '',
  // createTime: '',
  classId: [],
  className: [],
  classUserId: [],
  classMapping: new Map() // 创建 classMapping 属性
});

const formLoading = ref(false);
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  account: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择身份', trigger: 'change' }
  ],
  email: [
    { pattern: regEmail, message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  phone: [
    { pattern: phoneRegex, message: '请输入正确的手机号', trigger: 'blur' }
  ]
});
const getAllClasses = () => {
  classList.splice(0, classList.length);
  return getAllClass().then((res) => {
    // console.log(res);
    res.data.forEach((item) => {
      classList.push({
        name: item.name,
        id: item.id,
      });
    });
  });
};

const addForm = () => {
  // 在调用 createUser 之前先检查条件
  if (form.role === "student" && form.classId.length !== 1) {
    ElMessage.error('只能加入一个班级');
    formLoading.value = false;
  //  router.push('/manager/userManager'); //用户列表页面
    return; // 如果条件不满足，则直接返回，不执行后续代码
  }
  console.log(form.classId)
  formLoading.value = true;
  createUser(form)
    .then((res) => {
      ElMessage.success('用户添加成功');
      form.id = res.data;
      if (form.role === "teacher") {
        const coursePromises = form.classId.map(classId => {
          return createExamClassTeacher({ classId: classId, teacherId: form.id });
        });
        Promise.all(coursePromises)
          .then((results) => {
            console.log("所有课程创建成功", results);
            // 可在这里处理进一步的操作或状态更新
          })
          .catch((err) => {
            ElMessage.error('课程创建失败');
            console.error("课程创建失败", err);
          });
      } else if (form.role === "student") {
        // 由于在调用 createUser 之前已经检查了班级数量，这里不需要再次检查
        const ClassId = form.classId[0];
        return createExamClassStudent(ClassId, form.id);
      }
    })
    .catch((error) => {
      // 处理 createUser 失败的情况
      ElMessage.error('用户添加失败');
      console.error("用户添加失败", error);
    })
    .finally(() => {
      formLoading.value = false; // 无论成功或失败，都重置加载状态
      router.push('/manager/userManager'); //用户列表页面
    });
};

// 根据ID获取用户数据并填充表单
const fetchUserData = (id) => {
  formLoading.value = true;
  getAllUserById(id)
    .then((re) => {
      Object.assign(form, re.data);
      // console.log(form)
      if (form.role === "teacher") {
        getExamClassTeacherById(id).then((res) => {
          form.classUserId = res.data.map(item => item.id);
          form.classId = res.data.map(item => item.classId);
          // console.log("Class User IDs:", form.classUserId);
          // console.log("Class IDs:", form.classId);
          // 创建 classMapping
          form.classMapping.clear(); // 清空之前的映射
          form.classUserId.forEach((userId, index) => {
            const classId = form.classId[index]; // 确保每个用户ID对应一个班级ID
            // console.log(`User ID: ${userId}, Class ID: ${classId}`); // 输出每个用户和对应的班级ID
            form.classMapping.set(userId, classId);
          });
          form.className = classList
            .filter(classItem => form.classId.includes(classItem.id)) // 找到匹配的ID
            .map(classItem => classItem.name); // 获取名称并赋值给form.classId
          // console.log(form.classId)
        })
      } else if (form.role === "student") {
        getExamClassStudentByStuId(id).then((res) => {
          form.classUserId = res.data.map(item => item.id);
          // console.log(form.classUserId)
          form.classId = res.data.map(item => item.classId); // 获取学生班级ID
          form.className = classList
            .filter(classItem => form.classId.includes(classItem.id)) // 找到匹配的ID
            .map(classItem => classItem.name); // 获取名称并赋值给form.classId
          // console.log(form.classId)
          if (form.classId.length === 0) {
            IsEmpty = true
          } else {
            IsEmpty = false
          }
          // console.log(form.classId.length)
        })
      }
      formLoading.value = false;
    })
    .catch(err => {
      ElMessage.error('获取用户数据失败');
      // console.error(err);
      formLoading.value = false;
    });
};

const submitForm = () => {
  formLoading.value = true;
  if (form.sex.length > 10) {
    ElMessage.error('性别长度不能超过10个字符');
    formLoading.value = false;
    return;
  }
  if (form.role === "student" && form.classId.length !== 1) {
    ElMessage.error('只能加入一个班级');
    formLoading.value = false;
    return; // 如果条件不满足，则直接返回，不执行后续代码
  }
  console.log('提交的数据:', form);
  updateUserInfo(form)
    .then(() => {
      if (form.role === "student") {
        if (IsEmpty) {
          console.log(form.classId[0])
          createExamClassStudent(form.classId[0], form.id);
          IsEmpty = false
        } else {
          updateExamClassStudent(form.classUserId[0], form.classId[0], form.id)
        }
      } else if (form.role === "teacher") {
        // 获取现有的 classId 和对应的 classUserId
        const existingEntries = Array.from(form.classMapping.entries());
        const existingClassIds = new Set(existingEntries.map(([classUserId, classId]) => classId));

        // 创建新的记录
        form.classId.forEach(classId => {
          if (!existingClassIds.has(classId)) {
            const classUserId = form.classUserId[form.classId.indexOf(classId)]; // 获取对应的 classUserId
            createExamClassTeacher({ classId, teacherId: form.id });
            // // 这里可以选择是否将新创建的映射添加到 classMapping
            // form.classMapping.set(classUserId, classId);
          }
        });

        // 删除不再存在的记录
        existingEntries.forEach(([classUserId, classId]) => {
          if (!form.classId.includes(classId)) {
            deleteExamClassTeacher(classUserId);
            // // 从 classMapping 中删除对应的条目
            // form.classMapping.delete(classUserId);
          }
        });
      }
      ElMessage.success('用户信息更新成功');
      formLoading.value = false;
      router.push('/manager/userManager'); //用户列表页面
    })
    .catch(err => {
      ElMessage.error('用户信息更新失败');
      console.error(err);
      formLoading.value = false;
    });
};

const resetForm = () => {
  Object.assign(form, {
    username: '',
    account: '',
    password: '',
    role: '',
    email: '',
    phone: '',
    sex: '',
    classId: [],
    className: []
  });
};

onMounted(() => {
  if (route.query.id) {
    const id = route.query.id;
    getAllClasses().then(() => {
      fetchUserData(id);
    });
  } else {
    getAllClasses(); // 处理没有 id 的情况
  }
});
</script>

<style scoped></style>