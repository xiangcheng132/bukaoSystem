<template>
  <div class="app-container" style="margin: 50px;">
    <el-form v-model="form" ref="userForm" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="用户名：" prop="username" required>
        <el-input v-model="form.username" ></el-input>
      </el-form-item>
      <el-form-item label="账号：" prop="account" required>
        <el-input v-model="form.account" ></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="password"  required>
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
        <el-input v-model="form.email" ></el-input>
      </el-form-item>
      <el-form-item label="手机号："  prop="phone" >
        <el-input v-model="form.phone" ></el-input>
      </el-form-item>
      <el-form-item label="性别："  prop="sex" >
        <el-select v-model="form.sex" placeholder="性别" clearable >
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
          <el-option label="未知" value="未知" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-if="form.id === null" type="primary" @click="addForm">添加</el-button>
        <el-button v-else type="primary" @click="submitForm">修改</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted} from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { getAllUserById, createUser, updateUserInfo } from '@/api/examUser.js';
const regEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const phoneRegex = /^(\+\d{1,3}[- ]?)?\(?(\d{3})\)?[-. ]?(\d{3})[-. ]?(\d{5})$/;
const route = useRoute();
const router = useRouter();
const forceUpdateKey = ref(0);
const form = reactive({
  id: null,
  username: '',
  account: '',
  password: '',
  role: '',
  email: '',
  phone: '',
  sex: '',
  createTime: null
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

// 根据ID获取用户数据并填充表单
const fetchUserData = (id) => {
  formLoading.value = true;
  getAllUserById(id)
    .then(re => {
      Object.assign(form, re.data);
      formLoading.value = false;
      console.log('表单数据已更新:', form);
    })
    .catch(err => {
      ElMessage.error('获取用户数据失败');
      console.error(err);
      formLoading.value = false;
    });
};

onMounted(() => {
  const id = route.query.id;
  if (id && !isNaN(parseInt(id))) {
    fetchUserData(id);
  } 
});

const addForm = () => {
  formLoading.value = true;
  createUser(form)
    .then(() => {
      ElMessage.success('用户添加成功');
      formLoading.value = false;
      router.push('/manager/userManager'); // 假设有一个用户列表页面
    })
    .catch(err => {
      ElMessage.error('用户添加失败');
      console.error(err);
      formLoading.value = false;
    });
};

const submitForm = () => {
  formLoading.value = true;
  if (form.sex.length > 10) {
    ElMessage.error('性别长度不能超过10个字符');
    return;
  }
  console.log('提交的数据:', form);
  updateUserInfo(form)
    .then(() => {
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
  });
};

</script>

<style scoped>

</style> 