<template>

  <el-table :data="answerList" style="width: 100%">
    <el-table-column fixed prop="examname" label="试卷名" width="150" />
    <el-table-column prop="username" label="考生名" width="120" />
    <el-table-column prop="score" label="答卷分数" width="120" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button link type="primary" size="small" @click="handleClick(scope.$index)">
          批阅
        </el-button>
      </template>
    </el-table-column>
  </el-table>

</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { getExamClassTeacherById, getByTeacherId } from "@/api/examAnswerSheet";
import { setStorage, getStorage } from "@/utils/storage.js";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
const store = useStore();
const router = useRouter();

const answerList = reactive([
  {
    examname: "测试1",
    username: "zhenglou",
    userId: 10,
    score: 100,
    id: 2,
    examId: 1,
  },
]);
function handleClick(index) {
  /*   let id = answerList[index].answerSheetId;
  let userId = answerList[index].userId;
  let examId = answerList[index].examId; */
  router.push({
    path: "/teacher/textManager/answerSheet",
    query: {...answerList[index],isAnswer:false},
  });
}
onMounted(async function() {
  const result = await getByTeacherId(store.state.user.userInfo.id);
  console.log(result);
  result.data.forEach((i) => {
    answerList.push(i);
  });
});
</script>
    
<style lang = 'less'>
</style>