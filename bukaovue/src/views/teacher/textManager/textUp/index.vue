<template>
  <el-form :model="form" label-width="auto" style="max-width: 600px">
    <el-form-item label="试卷名：">
      <el-input v-model="form.name" />
    </el-form-item>
    <el-form-item label="试卷详情：">
      <el-input v-model="form.comment" />
    </el-form-item>
    <el-form-item label="单选题题数：">
      <el-input v-model="form.singleChoiceCount" />
    </el-form-item>

    <el-form-item label="判断题题数：">
      <el-input v-model="form.trueFasleCount" />
    </el-form-item>
    <el-form-item label="填空题题数：">
      <el-input v-model="form.fillnBlackCount" />
    </el-form-item>
    <el-form-item label="问答题题数：">
      <el-input v-model="form.bigquestionCount" />
    </el-form-item>
    <el-form-item label="考试地点：">
      <el-input v-model="form.place" />
    </el-form-item>
    <el-form-item label="所属课程：">
      <el-select
        v-model="form.courseId"
        placeholder="所属课程"
        @change="courseChange"
      >
        <el-option v-for="v in courseList" :label="v.name" :value="v.id" />
      </el-select>
    </el-form-item>
    <el-form-item label="所属班级：">
      <el-select v-model="form.classId" placeholder="所属班级">
        <el-option
          v-for="v in classList"
          :label="v.className"
          :value="v.classId"
        />
        <!-- <el-option
          v-for="v in classList"
          :label="v.className"
          :value="v.classId"
        /> -->
      </el-select>
    </el-form-item>

    <el-form-item label="发布时间：">
      <div class="demo-datetime-picker">
        <div class="block">
          <el-date-picker
            v-model="form.beginTime"
            type="datetime"
            placeholder="Select date and time"
            style="width: 100%"
            format="YYYY/MM/DD hh:mm:ss"
            value-format="YYYY-MM-DD h:m:s"
          />
        </div>
      </div>
    </el-form-item>
    <el-form-item label="结束时间：">
      <div class="demo-datetime-picker">
        <div class="block">
          <el-date-picker
            v-model="form.endTime"
            type="datetime"
            placeholder="Select date and time"
            style="width: 100%"
            format="YYYY/MM/DD hh:mm:ss"
            value-format="YYYY-MM-DD h:m:s"
          />
        </div>
      </div>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit" class="bbb">生成</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref, reactive, onMounted,toRaw } from "vue";
import { deleteExamById, getExamInfoByTeaId,createExam} from "@/api/exam";
import { getAllCourseByUId } from "@/api/examCourse";
import { setStorage, getStorage } from "@/utils/storage.js";
import { ElMessage, ElMessageBox } from "element-plus";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { getByCourseId } from "@/api/examCourseClass";
import { examUp } from "@/api/exam.js";

const store = useStore();
// do not use same name with ref
let courseList = reactive([]);
let classList = reactive([]);
let form = reactive({
  name: "",
  comment: "",
  singleChoiceCount: "",
  trueFasleCount: "",
  fillnBlackCount: "",
  bigquestionCount: "",
  place: "",
  classId: "",
  courseId: "",
  beginTime: "",
  endTime: "",
});

const onSubmit = async () => {
  console.log("submit!");
  // console.log(form);
  let newForm = toRaw(form);
  // console.log(newForm);
  // newForm["state"] = 1;
        // console.log(code)
      ElMessageBox.alert("确认创建？", "Title", {
      // if you want to disable its autofocus
      // autofocus: false,
      confirmButtonText: "确认",
      callback:async (action) => {
        ElMessage({
          type: "info",
          message: `提交成功`,
        });
        //Todo router.push("") 提交后跳转路由
        const code = await examUp(newForm);
      },
    });

};
async function courseChange(v) {
  console.log(v);
  classList.splice(0, classList.length);
  let classes = await getByCourseId(v);
  console.log(classes.data);
  classes.data.forEach((e) => {
    classList.push(e);
  });
}

function refreshCourseInfo() {
  courseList.splice(0, courseList.length);
  getAllCourseByUId(store.state.user.userInfo.id)
    .then((res) => {
      console.log(res);
      res.data.map((item) => {
        courseList.push({
          name: item.name,
          id: item.id,
        });
      });
    })
    .catch((err) => {});
}
onMounted(function() {
  refreshCourseInfo();
});
</script>

<style lang="less">
.bbb {
  margin: 0 auto;
}
</style>
