<template>
 <el-container class="layout-container-demo" style="height: 100vh">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu 
        :default-openeds="['1', '2','3']"
        router
        >
          <el-sub-menu index="1">
            <template #title>
              <el-icon><message /></el-icon>课程管理
            </template>
            <el-menu-item index="/teacher/tCourseManager" >课程修改</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><Menu/></el-icon>考试管理
            </template>
              <el-menu-item index="/teacher/textManager/textShow" >试卷展示</el-menu-item>
              <el-menu-item index="/teacher/textManager/textUp" >试卷生成</el-menu-item>
              <el-menu-item index="/teacher/textManager/answerSheets" >答卷展示</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><setting /></el-icon>资源管理
            </template>
            <el-menu-item index="/teacher/tResourceManager">资源修改</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div class="pageInfo">{{route.meta.title}}</div>
        <div class="toolbar">
          <el-dropdown @command="exit" size="large">
            <el-icon style="margin-right: 8px; margin-top: 1px" size="16">
              <setting />
            </el-icon>
            <template #dropdown >
              <el-dropdown-menu>
                <el-dropdown-item command="exit">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span style="font-size:18px">{{store.state.user.userInfo.username}}</span>
        </div>
      </el-header>

      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>

</template>

<script setup>
import Aside from "@/views/teacher/tCourseManager/component/aside.vue";
import Header from "@/views/teacher/tCourseManager/component/header.vue";
import { ref } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";
import { removeStorge } from "@/utils/storage";
let router = useRouter();
let route = useRoute();
const store = useStore();
const cc = "cccc"
//退出登录
function exit(command) {
  removeStorge("token");
  router.push("/login")
}

</script>
    
<style lang = 'less'>

.layout-container-demo .el-header {
  position: relative;
  /* background-color: var(--el-color-primary-light-7); */
  background-color: aliceblue;
  color: var(--el-text-color-primary);
}
.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  /* background: var(--el-color-primary-light-8); */
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
.pageInfo{
  text-align: left;
  position: relative;
  top:50%;
  transform:translate(0,-50%);
  font-size: 20px;
  /* width: 80%; */
}
.toolbar{
  position: relative;
  top: -40%;
}
</style>