<template>
  <div class="sourse">
    <h2>课程中心</h2>
    <p>根据id，展示所有用户加入的课程</p>
    <p>根据输入的用户id和班级id将用户加入班级，返回加入结果信息</p>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'CourseCenter'
};
</script>

<style scoped>
.sourse {
  padding: 1em;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.sourse h2 {
  margin-top: 0;
  font-size: 1.5em;
  color: #333;
}
</style>
