<template>
  <div>
    <h1>考试信息列表</h1>

    <table>
      <thead>
      <tr>
        <th>课程ID</th>
        <th>课程名称</th>
        <th>描述</th>
        <th>考试地点</th>
        <th>开始时间</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="exam in exams" :key="exam.id">
        <td>{{ exam.courseId }}</td>
        <td>{{ exam.name }}</td>
        <td>{{ exam.comment }}</td>
        <td>{{ exam.place }}</td>
        <td>{{ exam.beginTime }}</td>
        <td><button @click="startExam(exam)">考试</button></td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {getAllExam,getExamInfoBySId} from '@/api/exam.js';
import {  createExamAnswerSheet } from '@/api/examAnswerSheet'
import router from "@/router"; // 确保路径正确
import { useStore } from "vuex";


export default {
  data() {
    return {
      exams: [] ,// 存储考试信息
      username:"xuesheng"
    };
  },
  created() {
    this.fetchExams();
  },
  methods: {
    // 异步获取考试数据
    async fetchExams() {
      try {
        const response = await getExamInfoBySId(this.$store.state.user.userInfo.id); // 调用 API 获取数据
        this.exams = response.data; // 将返回的数据存储到 exams 中
      } catch (error) {
        console.error('获取考试信息失败:', error);
      }
    },
    // 点击考试按钮的处理函数
   async startExam(exam) {
    const answerExamId = await createExamAnswerSheet(exam.id, this.$store.state.user.userInfo.id,0)
    console.log(answerExamId);
      this.$router.push({
        path: '/student/scourse/exam',
        query: {
          id: answerExamId.data,
          examName: exam.name,
          username: this.username,
          isAnswer:true,
          examId:exam.id
        }
      });
    }
  }
};
</script>

<style scoped>
table {
  margin-top: 20px;
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 8px 12px;
  border: 1px solid #ddd;
  text-align: left;
}

th {
  background-color: #f4f4f4;
}
td button{
  background-color: white;
  border: none;
}
td button:hover{
  background-color:white;
  border: none;
  color: red;
}
</style>