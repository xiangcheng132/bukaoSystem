<template>
  <div class="top">
    <div class="platForm">在线测试平台</div>
    <div class="answerName">{{ answerSheetInfo.examName }}</div>
    <div class="username">{{ answerSheetInfo.userName }}</div>
    <div class="submit"><button @click="submitAnswerSheet">提交</button></div>
    <div class="deadTime" v-if="isAnswer">剩余时间:00:59:59</div>
  </div>
  <div class="wapper">
    <answerSheet
        @changeScore="changeScore"
        :singleList="singleList"
        :tFList="tFList"
        :completionList="completionList"
        :bigquestionList="bigquestionList"
        :isAnswer="isAnswer"
        :answerSheetId="answerSheetInfo.id"
    ></answerSheet>
  </div>
</template>
<style lang="less" scoped>
.top {
  width: 100%;
  height: 100px;
  border: 1px solid #ccc;
}
.platForm,
.answerName,
.username {
  float: left;
  margin-left: 30px;
  line-height: 100px;
}
.submit,
.deadTime {
  float: right;
  margin-right: 30px;
  line-height: 100px;
}
.wapper {
  width: 1460px;
  margin: 0 auto;
}
</style>
<script setup>
import { ref, reactive, onMounted, defineProps } from "vue";
import { updateExamAnswerSheetDetail } from "@/api/examAnswerSheetDetail";
import {
  updateExamAnswerSheet,
  getByAnswerld,
  getByTeacherId,
} from "@/api/examAnswerSheet";
import { getExamExamResourcesByExamId } from "@/api/exanmExamResources";
import { getByExamIdPlus } from "@/api/examResources";
import { setStorage, getStorage } from "@/utils/storage.js";
import { useRoute, useRouter } from "vue-router";
import answerSheet from "@/components/answerSheet";
import { ElMessage, ElMessageBox } from "element-plus";
import { useStore } from "vuex";
const store = useStore();
const route = useRoute();
const router = useRouter();
const answerSheetInfo = route.query;
const isAnswer = answerSheetInfo.isAnswer;

/*
  router.push({
    path: "/teacher/textManager/answerSheet",
    query: {
      "id", 答卷id
      "examName", 试卷名
      "isAnswer", 是否为回答的状态 学生端过来 isAnswer的值就设置为true
      "username", 用户名
    }
  });
{
  "id", 答卷id
  "examName", 试卷名
  "isAnswer", 是否为回答的状态 学生端过来 isAnswer的值就设置为true
  "username", 用户名
}

*/

console.log(answerSheetInfo);
let singleList = reactive([]);
let tFList = reactive([]);
let completionList = reactive([]);
let bigquestionList = reactive([]);
function changeScore(value) {
  singleList = value.singleList;
  tFList = value.tFList;
  completionList = value.completionList;
  bigquestionList = value.bigquestionList;
}
async function submitAnswerSheet() {
  if (store.state.user.userInfo.role == "teacher") {
    console.log("教师提交");
    let score = 0;
    singleList.forEach((item) => {
      score += parseInt(item.score, 10);
    });
    tFList.forEach((item) => {
      score += parseInt(item.score, 10);
    });
    completionList.forEach((item) => {
      score += parseInt(item.score, 10);
    });
    bigquestionList.forEach((item) => {
      score += parseInt(item.score, 10);
    });
    console.log(score);
    //调改变答卷表分数的接口 跳转路由
    ElMessageBox.alert("确认提交？", "提交", {
      // if you want to disable its autofocus
      // autofocus: false,
      confirmButtonText: "确认",
      callback: async (action) => {
        const result = await updateExamAnswerSheet(
            answerSheetInfo.id,
            answerSheetInfo.examId,
            answerSheetInfo.userId,
            score
        );
        //调改变答卷表分数的接口 跳转路由

        console.log(result, "更新分数");
        if (result.status == 200) {
          ElMessage({
            type: "info",
            message: `提交成功`,
          });
          router.push("/teacher/textManager/answerSheets");
        } else {
          ElMessage({
            type: "info",
            message: `提交失败`,
          });
        }
      },
    });
  } else if (store.state.user.userInfo.role == "student") {
    // 弹框
    console.log("学生提交");
    ElMessageBox.alert("确认提交？", "Title", {
      // if you want to disable its autofocus
      // autofocus: false,
      confirmButtonText: "确认",
      callback: (action) => {
        ElMessage({
          type: "info",
          message: `提交成功`,
        });
        //Todo router.push("") 提交后跳转路由
        router.push({
          path: "/student/notice"
        })
      },
    });
  }
}
onMounted(async function () {
  /*
  根据考卷id拿到所有资源
  把资源根据题型进行分类
  */
  let result = "";
  if (store.state.user.userInfo.role == "teacher") {
    result = await getByAnswerld(answerSheetInfo.id);
    console.log(result);
    result.data.forEach((i) => {
      const kk = i.examResources;
      if (kk.type == "single_choice") {
        const optionsArray = Object.entries(kk.options).map(
            ([key, value]) => `${key}: ${value}`
        );
        console.log(optionsArray);
        singleList.push({
          id: kk.id,
          title: kk.question,
          options: optionsArray,
          userKey: i.userKey,
          key: kk.key,
          score: kk.score,
        });
      }
      if (kk.type == "true_false") {
        tFList.push({
          id: kk.id,
          title: kk.question,
          options: ["对", "错"],
          userKey: i.userKey,
          key: kk.key,
          score: kk.score,
        });
      }
      if (kk.type == "completion") {
        completionList.push({
          id: kk.id,
          title: kk.question,
          userKey: i.userKey,
          key: kk.key,
          score: kk.score,
        });
      }
      if (kk.type == "bigquestion") {
        bigquestionList.push({
          id: kk.id,
          title: kk.question,
          userKey: i.userKey,
          key: kk.key,
          score: kk.score,
        });
      }
    });
  } else if (store.state.user.userInfo.role == "student") {
    result = await getByExamIdPlus(answerSheetInfo.examId);
    console.log(result, 22222);
    result.data.forEach((i) => {
      const kk = i;
      if (kk.type == "single_choice") {
        const optionsObject = JSON.parse(kk.options);
        const optionsArray = Object.entries(optionsObject).map(
            ([key, value]) => `${key}: ${value}`
        );
        console.log(optionsArray);
        singleList.push({
          id: kk.id,
          title: kk.question,
          options: optionsArray,
          key: i.key,
          score: i.score,
        });
      }
      if (kk.type == "true_false") {
        tFList.push({
          id: kk.id,
          title: kk.question,
          options: ["对", "错"],
          key: i.key,
          score: i.score,
        });
      }
      if (kk.type == "completion") {
        completionList.push({
          id: kk.id,
          title: kk.question,
          key: i.key,
          score: i.score,
        });
      }
      if (kk.type == "bigquestion") {
        bigquestionList.push({
          id: kk.id,
          title: kk.question,
          key: i.key,
          score: i.score,
        });
      }
    });
  }
});
</script>
