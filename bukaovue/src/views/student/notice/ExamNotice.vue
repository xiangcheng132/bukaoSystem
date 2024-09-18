<template>
  <div class="notice">
    <h2>考试通知</h2>
    <p>根据用户id，返回该用户班级已发布的考试信息</p>
  </div>
</template>

<script>
export default {
  name: 'ExamNotice'
};
</script>

<style scoped>
.notice {
  padding: 1em;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.notice h2 {
  margin-top: 0;
  font-size: 1.5em;
  color: #333;
}
</style>
