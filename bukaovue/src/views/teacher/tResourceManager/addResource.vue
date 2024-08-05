<template>
  <div class="bbb">
    <el-form :model="form" label-width="auto" style="max-width: 600px">
      <el-form-item label="题目：">
        <el-input v-model="form.question" />
      </el-form-item>
      <el-form-item label="题型：">
        <el-select v-model="form.type" placeholder="请选择题目类型" @change="changeByType">
          <el-option label="单选" value="single_choice" />
          <el-option label="判断" value="true_false" />
          <el-option label="填空" value="completion" />
          <el-option label="问答" value="bigquestion" /><!-- 'single_choice','true_false','completion','bigquestion' -->
        </el-select>
      </el-form-item>
      <div v-if="currentComponent">
        <el-form-item label="选项A">
          <el-input v-model="form.options.A" />
        </el-form-item>
        <el-form-item label="选项B">
          <el-input v-model="form.options.B" />
        </el-form-item>
        <el-form-item label="选项C">
          <el-input v-model="form.options.C" />
        </el-form-item>
        <el-form-item label="选项D">
          <el-input v-model="form.options.D" />
        </el-form-item>
        <el-form-item label="选项E">
          <el-input v-model="form.options.E" />
        </el-form-item>
      </div>

      <el-form-item label="答案：">
        <el-input v-model="form.key" />
      </el-form-item>
      <el-form-item label="解析：">
        <el-input v-model="form.analysis" />
      </el-form-item>
      <el-form-item label="分数">
        <el-input v-model="form.score" />
      </el-form-item>
      <el-form-item label="课程">
        <el-select v-model="course" placeholder="请选择课程" @change="selectCourseOptions" >
          <el-option v-for="(v,i) in courseOptions" :label="v.label" :value="v.value" :key="v.value"/>
        </el-select>                
     </el-form-item>
     <el-form-item label="章节">
        <el-select v-model="chapter" placeholder="请选择章节"  @change="selectChapterOptions">
          <el-option v-for="(v,i) in chapterOptions" :label="v.label" :value="v.value" :key="v.value"/>
        </el-select>
     </el-form-item>   
    <el-form-item>
      <el-button type="primary" @click="onSubmit" class="bbb">创建</el-button>
    </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref ,onMounted,toRaw} from "vue";
import { getExamTeacherCourseByTeacherId } from "@/api/examTeacherCourse"
import { getCourseChapterByCourseId } from "@/api/examCourseChapter"
import { getAllCourseByCId } from "@/api/examCourse"
import { createExamResource } from "@/api/examResources"
import {setStorage,getStorage} from "@/utils/storage.js";
import { ElMessage, ElMessageBox } from "element-plus";
// do not use same name with ref
const currentComponent = ref(false);
let selectKey = ref(1);
let form = reactive({
  question: "",
  type: "",
  options: {
    A: "无", 
    B: "无", 
    C: "无", 
    D: "无", 
    E: "无"
  },
  key: "",
  analysis: "",
  score: "",
  courseId: "",
  chapterId: "",
});

 let courseOptions = reactive([]);
 let course = ref("");
 let chapterOptions = reactive([]);
 let chapter = ref("");
// 改变选项框
function changeByType(value) {
  // console.log(111);
  if (value == "single_choice") {
    currentComponent.value = true;
  } else {
    currentComponent.value = false;
  }
}
// 选择课程后处理
async function selectCourseOptions(value){
  // console.log(value);
form.courseId = value;
chapterOptions.splice(0, chapterOptions.length);
 await getCourseChapterByCourseId(value).then(v=>{
    // console.log(v);
    v.data.forEach(async v=>{
     await chapterOptions.push({label:v.name,value:v.id})
    });
  })
}
// 选择章节后处理
function selectChapterOptions(value){
  // console.log(value);
  form.chapterId = value;
}
function refreshForm() {
  form.question =  "";
  form.type = "";
  form.options =  {
    A: "无", 
    B: "无", 
    C: "无", 
    D: "无", 
    E: "无"
  };
  form.key =  "";
  form.analysis =  "";
  form.score =  "";
  form.courseId =  "";
  form.chapterId =  "";
  course.value = "";
  chapter.value = "";
  
}
const onSubmit = () => {
  console.log("submit!");

  const tempForm = toRaw(form);
  console.log(tempForm);
  createExamResource(tempForm).then(res=>{
    console.log("资源创建",res);
    refreshForm();
    ElMessageBox.alert("创建", "资源创建", {
      confirmButtonText: "OK",
      callback: (action) => {

      },
    });
 }).catch((e) => {console.log(e)})

 


};

onMounted( async () => {
  // 根据教师id拿到所有课程id和课程名 赋值给courseOptions
  const id = getStorage("token");
  
  await getExamTeacherCourseByTeacherId(id).then((value)=>{
    // console.log(value); 根据教师id拿到教师所有课程
      value.data.forEach(async e => { 
       await getAllCourseByCId(e.courseId).then(v=>{
          // console.log(v);根据课程id拿到所有课程名和课程id
          courseOptions.push({label:v.data.name,value:v.data.id})
        })
      });
  })

 
});
</script>
<style lang = 'less'>
.bbb{
  margin: 0 auto;
}
</style>
