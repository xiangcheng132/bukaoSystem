<template>
  <el-container class="layout-container-demo" style="height: 100vh">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu :default-openeds="['1', '2', '3','4']" router>
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <message />
              </el-icon>用户管理
            </template>
            <el-menu-item index="/manager/userManager">用户列表页</el-menu-item>
            <el-menu-item index="/manager/userModify">用户管理页</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon>
                <Menu />
              </el-icon>课程管理
            </template>
            <el-menu-item index="/manager/mCourseManager">课程列表页</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon>
                <setting />
              </el-icon>班级管理
            </template>
            <el-menu-item index="/manager/classManager">班级管理页</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="4">
            <template #title>
              <el-icon>
                <Histogram />
              </el-icon>数据统计
            </template>
            <el-menu-item index="/manager/dataStatistics">数据统计页</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div class="pageInfo">{{ route.meta.title }}</div>
        <div class="toolbar">
          <el-dropdown @command="exit" size="large">
            <el-icon style="margin-right: 8px; margin-top: 1px" size="16">
              <setting />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="exit">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span style="font-size:18px">{{ store.state.user.userInfo.username }}</span>
        </div>
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>

</template>

<script setup>
import { ref } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";
import { removeStorge } from "@/utils/storage";
let router = useRouter();
let route = useRoute();
const store = useStore();

//退出登录
function exit(command) {
  removeStorge("token");
  router.push("/login")
}

</script>

<style lang='less'>
.layout-container-demo .el-header {
  position: relative;
  background-color: aliceblue;
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background-color: aliceblue;
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

.el-icon:focus {
  outline: none;
  cursor: pointer;
}

.pageInfo {
  text-align: left;
  position: relative;
  top: 50%;
  transform: translate(0, -50%);
  font-size: 20px;
}

.toolbar {
  position: relative;
  top: -40%;
}
</style>