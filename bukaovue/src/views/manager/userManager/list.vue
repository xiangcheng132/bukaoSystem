<template>
  <div class="app-container">
    <el-form :model="queryParam" ref="queryForm" :inline="true" style="margin:15px 10px 0px;">
      <el-form-item label="用户ID:">
        <el-input v-model="queryParam.id" placeholder="请输入用户ID"></el-input>
      </el-form-item>
      <el-form-item label="用户名：">
        <el-input v-model="queryParam.userName" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="身份">
        <el-select v-model="queryParam.role" placeholder="选择身份" clearable style="width: 100px;">
          <el-option label="教师" value="teacher" />
          <el-option label="学生" value="student" />
          <el-option label="管理员" value="admin" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button style="margin-right: 15px;" type="primary" @click="submitForm">查询</el-button>
        <router-link :to="{ path: '/manager/userModify' }" class="link-left">
          <el-button type="primary">添加</el-button>
        </router-link>
      </el-form-item>
    </el-form>
    <el-table :data="paginatedData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="id" label="Id" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="account" label="账号名" />
      <el-table-column prop="password" label="密码" />
      <el-table-column prop="role" label="身份" width="120px" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="sex" label="性别" width="60px;" />
      <el-table-column prop="createTime" label="创建时间" width="200px" />
      <el-table-column width="230px" label="操作" align="center">
        <template v-slot:default="{ row }">
          <router-link :to="{ path: '/manager/userModify', query: { id: row.id } }" class="link-left">
            <el-button size="small" type="primary">编辑</el-button>
          </router-link>
          <el-button size="small" type="danger" @click="handledeleteUser(row)" class="link-left">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="footer-box">
      <Pagination :total="total" :pageSize="queryParam.pageSize" :currentPage.sync="queryParam.pageIndex"
        @update:currentPage="handlePageChange" @update:pageSize="handlePageSizeChange" />
    </div>
  </div>
</template>

<script setup>
import Pagination from '@/components/pagination.vue';
import { useStore } from 'vuex';
import { ref, onMounted, computed } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { getAllUser, getAllUserByUsername, getAllUserById, getUserByIdAndUsername, deleteUser } from '@/api/examUser.js';
const store = useStore()
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

const queryParam = ref({
  id: null,
  username: '',
  role: '',
  pageIndex: 1,
  pageSize: 15
});

const listLoading = ref(true)
const tableData = ref([])
const total = ref(0);
const filteredData = ref([]); // 用于存储筛选后的数据

const paginatedData = computed(() => {
  const start = (queryParam.value.pageIndex - 1) * queryParam.value.pageSize;
  const end = start + queryParam.value.pageSize;
  return filteredData.value.slice(start, end);
});

const search = () => {
  listLoading.value = true;
  let apiCall;
  if (queryParam.value.id && queryParam.value.username) {
    // 如果同时提供了 ID 和用户名，调用组合查询的 API 方法
    apiCall = getUserByIdAndUsername({ id: queryParam.value.id, username: queryParam.value.username });
  } else if (queryParam.value.id) {
    apiCall = getAllUserById(queryParam.value.id);
  } else if (queryParam.value.username) {
    apiCall = getAllUserByUsername(queryParam.value.username);
  } else {
    apiCall = getAllUser();
  }
  apiCall.then(response => {
    const data = response.data; 

    tableData.value = Array.isArray(data) ? data : [data]; // 确保数据是数组
    filterData(); // 在筛选数据之后更新 filteredData
    total.value = filteredData.value.length;
    listLoading.value = false;
  }).catch(error => {
    console.error('An error occurred while fetching user data:', error);
    listLoading.value = false;
    ElMessage.error('查询用户数据时出错');
  });
};


const filterData = () => {
  filteredData.value = tableData.value.filter(user => {
    return (!queryParam.value.role || user.role === queryParam.value.role);
  });
};

const handlePageSizeChange = (size) => {
  queryParam.value.pageSize = size;
  queryParam.value.pageIndex = 1; // 重置为第一页
};

const handlePageChange = (page) => {
  queryParam.value.pageIndex = page;
};

onMounted(() => {
  search();
});

const submitForm = () => {
  queryParam.value.pageIndex = 1; // 重置分页索引为1
  search(); // 执行查询
};

const handledeleteUser = (row) => {
  ElMessageBox.confirm('确认删除该用户?', '提示', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteUser(row.id).then(response => {
      ElMessage.success('用户删除成功');
      if (paginatedData.value.length === 1 && queryParam.value.pageIndex > 1) {
        queryParam.value.pageIndex--; // 如果当前页只剩下一个用户，删除后回到上一页
      }
      search(); // 删除成功后重新搜索数据
    }).catch(error => {
      console.error('删除用户时出错:', error);
      ElMessage.error('删除用户时出错');
    });
  }).catch(() => {
    ElMessage.info('取消删除操作');
  });
};
</script>

<style scoped >

.el-button {
  margin-left: 5px;
  margin-right: 5px;
}

.footer-box {
  width: 90%;
  display: flex;
  justify-content: center;
  margin-top: 10px;
  padding: 10px;
}
</style>