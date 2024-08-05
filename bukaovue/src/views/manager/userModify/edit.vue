<!-- <template>
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="用户名："  prop="userName" required>
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="账号：" prop="account" required>
        <el-input v-model="form.account"></el-input>
      </el-form-item>
      <el-form-item label="密码："  required>
        <el-input v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item label="身份：" prop="role" required>
        <el-select v-model="form.role" placeholder="身份">
          <el-option label="教师" value="teacher" />
          <el-option label="学生" value="student" />
          <el-option label="管理员" value="admin" />
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱：">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="手机号：">
        <el-input v-model="form.phone"></el-input>
      </el-form-item>
      <el-form-item label="性别：">
        <el-select v-model="form.sex" placeholder="性别" clearable>
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
          <el-option label="未知" value="未知" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-if="form.id===null" type="primary" @click="addForm">添加</el-button>
        <el-button v-else type="primary" @click="submitForm">修改</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template> -->
<template>
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="用户名：" prop="username" required>
        <el-input v-model="form.username" @input="forceUpdate"></el-input>
      </el-form-item>
      <el-form-item label="账号：" prop="account" required>
        <el-input v-model="form.account" @input="forceUpdate"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="password"  required>
        <el-input v-model="form.password" @input="forceUpdate"></el-input>
      </el-form-item>
      <el-form-item label="身份：" prop="role" required>
        <el-select v-model="form.role" placeholder="身份" @change="forceUpdate">
          <el-option label="教师" value="teacher" />
          <el-option label="学生" value="student" />
          <el-option label="管理员" value="admin" />
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱：" prop="email">
        <el-input v-model="form.email" @input="forceUpdate"></el-input>
      </el-form-item>
      <el-form-item label="手机号："  prop="phone" >
        <el-input v-model="form.phone" @input="forceUpdate"></el-input>
      </el-form-item>
      <el-form-item label="性别："  prop="sex" >
        <el-select v-model="form.sex" placeholder="性别" clearable @change="forceUpdate">
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
    <div>
      <h3>表单数据：</h3>
      <pre>{{ JSON.stringify(form, null, 2) }}</pre>
    </div>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted, watch } from 'vue';
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
      form.id = re.data.id || null;
      form.userName = re.data.userName || '';
      form.account = re.data.account || '';
      form.password = re.data.password || '';
      form.role = re.data.role || '';
      form.email = re.data.email || '';
      form.phone = re.data.phone || '';
      form.sex = re.data.sex || '';
      form.createTime = re.data.createTime || null;
      Object.assign(form, re.data);
      formLoading.value = false;
      console.log('表单数据已更新:', form);
      forceUpdateKey.value++;
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
    forceUpdateKey.value++;
  } 
});

const addForm = () => {
  formLoading.value = true;
  createUser(form)
    .then(() => {
      ElMessage.success('用户添加成功');
      formLoading.value = false;
      router.push('/user-list'); // 假设有一个用户列表页面
    })
    .catch(err => {
      ElMessage.error('用户添加失败');
      console.error(err);
      formLoading.value = false;
    });
};

const submitForm = () => {
  formLoading.value = true;
  updateUserInfo(form.id, form)
    .then(() => {
      ElMessage.success('用户信息更新成功');
      formLoading.value = false;
      router.push('/user-list'); // 假设有一个用户列表页面
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
  forceUpdateKey.value++;
};

const forceUpdate = () => {
  forceUpdateKey.value++;
};

// import { ref, reactive, toRaw ,onMounted} from 'vue';
// import { useRoute, useRouter } from 'vue-router';
// import { useStore } from 'vuex';
// import { ElMessage } from 'element-plus';
// import {createUser , getAllUser ,getAllUserByUsername,getAllUserById,getUserByIdAndUsername, deleteUser} from '@/api/examUser.js';

// const regEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
// const phoneRegex = /^(\+\d{1,3}[- ]?)?\(?(\d{3})\)?[-. ]?(\d{3})[-. ]?(\d{5})$/;
// const route = useRoute();
// const router = useRouter();
// const store = useStore();

// // onMounted(() => {
// //   const id = route.query.id;
// //   if (id && !isNaN(parseInt(id))) {
// //     formLoading.value = true;
// //     getAllUserById(id)
// //       .then(re => {
// //         // API 返回的结构需要与表单的属性匹配
// //         Object.assign(form, re.data); 
// //         formLoading.value = false;
// //       })
// //       .catch(err => {
// //         ElMessage.error('获取用户数据失败');
// //         console.error(err);
// //         formLoading.value = false;
// //       });
// //   } else {
// //     ElMessage.warning('无效的用户ID');
// //   }
// // });
// onMounted(() => {
//   const id = route.query.id;
//  if (id && !isNaN(parseInt(id))) {
//     formLoading.value = true;
//     // 发送 POST 请求获取用户数据
//     getAllUserById(id)
//       .then(response => {
//         // 假设 response.data 包含用户数据
//         const userData = response.data;
//         // 确保用户数据结构与表单字段匹配
//         if (userData) {
//           form.id = userData.id || null;
//           form.username = userData.username || '';
//           form.account = userData.account || '';
//           form.password = userData.password || '';
//           if (Array.isArray(userData.role) && userData.role.length > 0 && userData.role[0].message) {
//             ElMessage.warning(userData.role[0].message); // 显示错误消息
//             form.role = ''; // 设置默认值或适当值
//           } else {
//             form.role = userData.role || ''; // 处理正常值
//           }
//           form.email = userData.email || '';
//           form.phone = userData.phone || '';
//           form.sex = userData.sex || '';
//           form.createTime = userData.createTime || null;
//         }
//         console.log(userData)
//         formLoading.value = false;
//       })
//     // getAllUserById(id)
//     //   .then(response => {
//     //     // API 返回的结构需要与表单的属性匹配
//     //     Object.assign(form,response.data); 
//     //     formLoading.value = false;
//     //   })
//       .catch(err => {
//         debugWarn(err); // 使用 debugWarn 输出错误信息
//         ElMessage.error('获取用户数据失败');
//         formLoading.value = false;
//       });
//   } else {
//     ElMessage.warning('无效的用户ID');
//   }
// });

// const form = reactive({
//   id: null,
//   username: '',
//   account: '',
//   password: '',
//   role:null,
//   email: '',
//   phone: '',
//   sex: '',
//   createTime:null,
// });
// const formLoading = ref(false);
// const rules = reactive({
//   userName: [
//     { required: true, message: '请输入用户名', trigger: 'blur' }
//   ],
//   account: [
//     { required: true, message: '请输入账号', trigger: 'blur' }
//   ],
//   password: [
//     { required: true, message: '请输入密码', trigger: 'blur' }
//   ],
//   role: [
//     { required: true, message: '请选择身份', trigger: 'change' }
//   ],
//   email: [
//     { pattern: regEmail, message: '请输入正确的邮箱地址', trigger: 'blur' }
//   ],
//   phone: [
//     { pattern: phoneRegex, message: '请输入正确的手机号', trigger: 'blur' }
//   ]
// });

// // const submitForm = () => {
// //   formLoading.value = true;
// //   const formRef = ref('form');
// //   formRef.value.validate((valid) => {
// //     if (valid) {
// //       const userInfo = toRaw(form);
// //       createUser(userInfo).then(res => {
// //         ElMessage.success(res.data);
// //         router.push('/some-route'); // redirect to another page
// //       }).catch(err => {
// //         ElMessage.error('添加失败');
// //         console.error(err);
// //       }).finally(() => {
// //         formLoading.value = false;
// //       });
// //     } else {
// //       ElMessage.error('请填写完整信息');
// //       formLoading.value = false;
// //     }
// //   });
// // };
// const addForm = () => {
//   // 处理添加表单的逻辑
//   console.log('Adding form data:', form);
//   // 示例：发送请求添加数据
// };

// const submitForm = () => {
//   // 处理提交表单的逻辑
//   console.log('Submitting form data:', form);
//   // 示例：发送请求更新数据
// };


// const resetForm = () => {
//   const formRef = ref('form');
//   if (formRef.value) {
//     formRef.value.resetFields();
//   }
// };

// onMounted(() => {
//   const id = route.query.id;
//   if (id && parseInt(id) !== 0) {
//     formLoading.value = true;
//     getAllUserById(id).then(re => {
//       Object.assign(form, re.data);
//       formLoading.value = false;
//     }).catch(err => {
//       ElMessage.error('获取用户数据失败');
//       console.error(err);
//       formLoading.value = false;
//     });
//   }
// });

</script>

<style scoped>

</style> 