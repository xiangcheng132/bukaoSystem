<template>
  <el-button type="primary" @click="addResource">新建资源</el-button>
  <div>
    <el-table :data="item" style="width: 100%" border ref="paragraph">
      <!-- <el-table-column fixed prop="id" label="Id" width="300" /> -->
      <el-table-column prop="question" label="问题" width="200" />
      <el-table-column prop="A" label="选项一" width="150" show-overflow-tooltip />
      <el-table-column prop="B" label="选项二" width="150" show-overflow-tooltip />
      <el-table-column prop="C" label="选项三" width="150" show-overflow-tooltip />
      <el-table-column prop="D" label="选项四" width="150" show-overflow-tooltip />
      <el-table-column prop="E" label="选项五" width="150" show-overflow-tooltip/>
      <el-table-column prop="key" label="答案" width="60" />
      <el-table-column prop="analysis" label="解析" width="100" />
      <el-table-column prop="type" label="题型" width="80" />
      <el-table-column prop="courseName" label="所属课程" width="100" />
      <el-table-column prop="chapterName" label="章节" width="100" />
      <el-table-column prop="score" label="分数" width="100" />
      <el-table-column fixed="right" label="操作" min-width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click.prevent="modifyRow(scope.$index)">
            修改
          </el-button>
          <el-button link type="primary" size="small"  @click.prevent="deleteRow(scope.$index)">删除</el-button>
        </template>
    </el-table-column>
  </el-table>
  </div>
  <div v-if="formChange">
      <el-dialog v-model="dialogFormVisible" title="资源信息修改" width="500">
        <el-form :model="form">
          <el-form-item label="问题">
            <el-input v-model="form.question" autocomplete="off" />
          </el-form-item>
          <el-form-item label="选项A">
            <el-input v-model="form.A" autocomplete="off" />
          </el-form-item>
          <el-form-item label="选项B">
            <el-input v-model="form.B" autocomplete="off" />
          </el-form-item>  
          <el-form-item label="选项C">
            <el-input v-model="form.C" autocomplete="off" />
          </el-form-item>
          <el-form-item label="选项D">
            <el-input v-model="form.D" autocomplete="off" />
          </el-form-item>
          <el-form-item label="选项E">
            <el-input v-model="form.E" autocomplete="off" />
          </el-form-item>
          <el-form-item label="答案">
            <el-input v-model="form.key" autocomplete="off" />
          </el-form-item>
           <el-form-item label="解析">
            <el-input v-model="form.analysis" autocomplete="off" />
          </el-form-item>
          <el-form-item label="题型">
            <el-input v-model="form.type" autocomplete="off" disabled/>
          </el-form-item>
          <el-form-item label="课程名">
            <el-input v-model="form.courseName" autocomplete="off" disabled/>
          </el-form-item>
          <el-form-item label="文章名">
            <el-input v-model="form.chapterName" autocomplete="off" disabled/>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-input v-model="form.createTime" autocomplete="off" disabled/>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click.prevent="handleModify">
              确认
            </el-button>
          </div>
        </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, toRaw, nextTick} from "vue";
import { useRoute,useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import { Action } from "element-plus";
import {
  getAllExamResources,
  getExamResourceByCId,
  getExamResourceByChapId,
  getExamResourceByCRId,
  createExamResource,
  updateExamResource,
  deleteExamResource,
} from "@/api/examResources";
import { createExamTeacherCourse } from "@/api/examTeacherCourse";
import { getTCoursesByCName } from "@/api/examCourse";
import { createExamCourseChappter } from "@/api/examCourseChapter";
import { getAllCourseByUId } from "@/api/examCourse";
import {setStorage,getStorage} from "@/utils/storage.js";
import { useStore } from "vuex";
import {
  dealResourceOptionsInfo,
  dealResourceTypeInfo,
  dealResourceCourseInfo,
  dealResourceChapterInfo,
  dealResourceOptions,
  typeReserve,
} from "@/utils/resourceDeal.js";
const store = useStore();
const router = useRouter();
let item = reactive([]); // 展示的数据
// const paragraph = ref(null);
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
//清空临时表单
function refreshForm() {
  form.id = "";
  form.A = "";
  form.B = "";
  form.C = "";
  form.D = "";
  form.E = "";
  form.analysis = "";
  form.chapterId = "";
  form.chapterName = "";
  form.courseId = "";
  form.courseName = "";
  form.createTime = "";
  form.key = "";
  form.options = "";
  form.question = "";
  form.score = "";
  form.type = "";
  // console.log(form.id + "执行刷新表单");
}
//修改临时表单
let form = reactive({
  id: "",
  A: "",
  B: "",
  C: "",
  D: "",
  E: "",
  analysis: "",
  chapterId: "",
  chapterName: "",
  courseId: "",
  courseName: "",
  createTime: "",
  key: "",
  options: "",
  question: "",
  score: "",
  type: "",
});
//删除记录
const deleteRow = (index) => {
  // console.log(index);
  ElMessageBox.confirm("确认删除该课程信息？", "警告", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      deleteExamResource(item[index].id)
        .then((res) => {
          if ((res.status = "201")) {
            refreshCourseInfo();
            ElMessage({
              type: "success",
              message: "删除成功",
            });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消删除",
      });
    });
};

const dialogFormVisible = ref(false); //弹出表单的显示隐藏控制
// const formLabelWidth = '140px' 弹出表单大小
const formChange = ref(true); // 改变弹出表单框

//修改记录
const modifyRow = (index) => {
  console.log("修改记录:", index);
  formChange.value = true;
  dialogFormVisible.value = true; // 修改框显示
  form.id = item[index].id;
  form.courseId = item[index].courseId;
  form.chapterId = item[index].chapterId;
  form.A = item[index].A;
  form.B = item[index].B;
  form.C = item[index].C;
  form.D = item[index].D;
  form.E = item[index].E;
  form.analysis = item[index].analysis;
  form.chapterName = item[index].chapterName;
  form.courseName = item[index].courseName;
  form.key = item[index].key;
  form.question = item[index].question;
  form.score = item[index].score;
  form.type = item[index].type;
  form.createTime = item[index].createTime;
  //处理修改
  // dialogFormVisible.value = false;
  // item.value.splice(index, 1)
};
//处理修改
function handleModify() {
  let form1 = toRaw(form);
  console.log("打印form1",form1);
  let options = dealResourceOptions({
    A: form.A,
    B: form.B,
    C: form.C,
    D: form.D,
    E:form.E
  });
  let type = typeReserve(form.type);
  console.log(options);
  const newR = {
    id:form.id,
    courseId: form1.courseId,
    chapterId: form1.chapterId,
    question: form1.question,
    type: type,
    options: options,
    key: form1.key,
    analysis: form1.analysis,
    score: form1.score,
    createTime: form1.createTime,
  };
  console.log("修改后的表单",newR);
 
  updateExamResource(newR)
    .then((res) => {
      console.log(res + "执行更新资源操作");
      refreshCourseInfo();
      ElMessageBox.alert("修改成功", "Title", {
        confirmButtonText: "OK",
        callback: (action) => {
          dialogFormVisible.value = false;
        },
      });
    })
    .catch((err) => {
      console.log(err);
    });
    refreshForm();
}

// 添加资源
function addResource() {
  router.push("/teacher/addResource")
}
// 创建资源
function createNewResource() {

}
//刷新课程信息
function refreshCourseInfo() {
  item.splice(0, item.length);
  getAllCourseByUId(getStorage("token")).then(v=>{
      v.data.forEach(e=>{
    getExamResourceByCId(e.id)
      .then((res) => {
        console.log("刷新资源信息成功");
        // console.log(res);
        // item.value = res.data;
        dealResourceInfo(res.data);
      })
      .catch((err) => {});
        })
  })

}
// 处理资源信息
function dealResourceInfo(data) {
  // 获取数据后加上如下代码
  let tempItem = dealResourceOptionsInfo(data);
   console.log("tempItem",tempItem);
  
  setTimeout(()=>{
    tempItem.forEach(e=>{
      item.push(e);
    })
    // item.value = tempItem;
    // console.log("等待3秒");
  },500)

  // console.log(paragraph);
}
// 对资源信息中的选项信息进行处理

onMounted(() => {
  refreshCourseInfo();
});
</script>
    
<style lang = 'less'>
.el-button--primary {
  margin: 10px;
}
.el-table td.el-table__cell,
.el-table th.el-table__cell.is-leaf {
  text-align: center;
}
</style>