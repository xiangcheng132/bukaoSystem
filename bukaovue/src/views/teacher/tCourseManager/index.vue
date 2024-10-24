<template>
  <!-- <el-button type="primary" @click="addCourse">新建课程</el-button> -->
  <div>
    <el-table :data="item.value" style="width: 100%">
      <!-- <el-table-column fixed prop="id" label="Id" width="300" /> -->
      <el-table-column prop="name" label="课程名" width="300" />
      <el-table-column prop="comment" label="课程详情" width="400" />
      <el-table-column prop="createTime" label="创建时间" width="300" />
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
      <el-dialog v-model="dialogFormVisible" title="课程信息修改" width="500">
        <el-form :model="form">
          <el-form-item label="id">
            <el-input v-model="form.id" autocomplete="off" disabled/>
          </el-form-item>
          <el-form-item label="name">
            <el-input v-model="form.name" autocomplete="off" />
          </el-form-item>
          <el-form-item label="comment">
            <el-input v-model="form.comment" autocomplete="off" />
          </el-form-item>  
          <el-form-item label="createTime">
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
  <div v-else>
    <el-dialog v-model="dialogFormVisible" title="新建课程" width="500">
      <el-form :model="form">
        <el-form-item label="name">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="comment">
          <el-input v-model="form.comment" autocomplete="off" />
        </el-form-item>  
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click.prevent="createNewCourse">
            确认
          </el-button>
        </div>
      </template>
  </el-dialog>
</div>
</template>

<script setup>
import { ref, reactive, onMounted, toRaw } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
// import { Action } from "element-plus";
import {
  createCourse,
  updateCourse,
  deleteCourseById,
  getAllCourseByUId,
  getAllCourseByCId,
} from "@/api/examCourse";
import { createExamTeacherCourse } from "@/api/examTeacherCourse";
import { useStore } from "vuex";

const store = useStore();
let item = reactive([]);
//清空临时表单
function refreshForm() {
  form.id = "";
  form.name = "";
  form.comment = "";
  form.createTime = "";
  console.log(form.id + "执行刷新表单");
}
//修改临时表单
let form = reactive({
  id: "",
  name: "",
  comment: "",
  createTime: "",
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
      deleteCourseById(item.value[index].id)
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
  form.id = item.value[index].id;
  form.name = item.value[index].name;
  form.comment = item.value[index].comment;
  form.createTime = item.value[index].createTime;
  //处理修改
  // dialogFormVisible.value = false;
  // item.value.splice(index, 1)
};
//处理修改
function handleModify() {
  console.log();
  updateCourse(toRaw(form))
    .then((res) => {
      console.log(res + "执行更新里的获取用户操作");
      refreshForm();
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
}

// 添加课程
function addCourse() {
  formChange.value = false;
  dialogFormVisible.value = true;
  //输入课程名课程详情创建课程

  //将课程id 教师id 存入教师课程中间表

}
// 创建课程
function createNewCourse() {
  const tempForm = toRaw(form);
  console.log("进入createNewCourse", tempForm);
  createCourse(tempForm.name, tempForm.comment).then((res) => {
    // console.log(store.state.user.userInfo.id," ", res.data);
    createExamTeacherCourse({teacherId:store.state.user.userInfo.id,courseId:res.data}).then(res=>{
      console.log("课程老师中间表新建记录",res);
      refreshCourseInfo();
    })
    ElMessageBox.alert("添加成功", "新建课程", {
      confirmButtonText: "OK",
      callback: (action) => {
        dialogFormVisible.value = false;
      },
    });
  });

}
//刷新课程信息
function refreshCourseInfo(){
    getAllCourseByUId(store.state.user.userInfo.id)
    .then((res) => {
      console.log("刷新课程信息成功");
      item.value = res.data;
    })
    .catch((err) => {});
}
onMounted(() => {
  refreshCourseInfo();
});
</script>
    
<style lang = 'less'>
.el-button--primary {
  margin: 10px;
}
.el-table td.el-table__cell, .el-table th.el-table__cell.is-leaf{
  text-align: center;
}
</style>