<template>
  <div>
    <el-form :model="classFrom" ref="ClassForm" :inline="true" style="margin:15px 10px 0px;">
      <el-form-item label="班级ID:">
        <el-input v-model="classFrom.id" placeholder="请输入班级ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button style="margin-right: 15px;" type="primary" @click="FindClass">查询</el-button>
        <el-button type="primary" @click="showCreateClassDialog = true">创建班级</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="item.values" style="width: 100%">
      <el-table-column prop="name" label="班级名称"></el-table-column>
      <el-table-column prop="comment" label="班级介绍"></el-table-column>
      <el-table-column prop="teacher" label="班主任"></el-table-column>
      <el-table-column label="操作">
        <template v-slot:default="{ row }">
          <router-link :to="{ path: '/manager/classAdd', query: { id: row.id } }" class="link-left">
            <el-button type="primary"  size="small" >编辑</el-button>
          </router-link>
          <el-button type="danger" size="small" @click="deleteClassById(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 创建班级对话框 -->
    <el-dialog title="创建班级" v-model="showCreateClassDialog">
      <el-form :model="newClassForm">
        <el-form-item label="班级名称">
          <el-input v-model="newClassForm.name"></el-input>
        </el-form-item>
        <el-form-item label="班级介绍">
          <el-input v-model="newClassForm.comment"></el-input>
        </el-form-item>
        <el-form-item label="教师id">
          <el-input v-model="newClassForm.teacherId"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showCreateClassDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCreateClass">创建</el-button>
      </span>
    </el-dialog>
    <div class="footer-box">
      <Pagination background layout="prev, pager, next, jumper" :total="total" :pageSize="pageSize"
        :current-page.sync="currentPage" @update:currentPage="handlePageChange" @update:pageSize="handleSizeChange" />
    </div>
  </div>
</template>

<script setup>
import Pagination from '@/components/pagination.vue';
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from 'element-plus';
import { getAllClasswithteacher, getAllClass, getAllClasswithteacherById, createClass, deleteClass } from "@/api/examClass";
import { getExamClassStudentByClassId, getExamClassStudentsById, getExamClassStudentByStuId, createExamClassStudent, updateExamClassStudent, deleteExamClassStudent } from "@/api/examClassStudent";
import { createExamClassTeacher, deleteExamClassTeacher } from "@/api/examClassTeacher";
import { useStore } from "vuex";
import { nextTick } from 'vue';
// const props = defineProps({
//       foo: String
//     })

//     const emit = defineEmits(['change', 'delete'])
//     // setup code
const store = useStore();

const item = reactive({ values: [] });
const showCreateClassDialog = ref(false);
const newClassForm = reactive({
  name: '',
  comment: '',
  teacherId: '',
  teacher: '',
});

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

const form = reactive({
  id: '',
  name: '',
  comment: '',
  teacher: '',
})


const total = ref(0);
const pageSize = ref(10);
const currentPage = ref(1);

let allClasses = [];

const refreshClassInfo = () => {
  getAllClasswithteacher()
    .then((res) => {
      allClasses = res.data || [];
      total.value = allClasses.length;
      updateTableData();
      nextTick(() => {
        const table = document.querySelector('.el-table');
        if (table) {
          table.style.display = 'none';
          table.offsetHeight; // 强制重绘
          table.style.display = '';
        }
      });
    })
    .catch((err) => {
      console.error("获取班级数据失败", err);
      item.values = [];
      total.value = 0;
      ElMessage.error('获取班级数据失败，请稍后重试');
    });
};

const updateTableData = () => {
  item.values = allClasses.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
};

const handlePageChange = (page) => {
  console.log("Page changed to:", page);
  currentPage.value = page;
  updateTableData();
};

const handleSizeChange = (size) => {
  console.log("Page size changed to:", size);
  pageSize.value = size;
  updateTableData();
};
onMounted(() => {
  refreshClassInfo();
});


// 创建班级
const handleCreateClass = () => {
  createClass({ name: newClassForm.name, comment: newClassForm.comment })
    .then((res) => {
      console.log("创建班级成功");
      console.log(res.data);
      createExamClassTeacher({ classId: res.data, teacherId: newClassForm.teacherId })
        .then((res) => {
          console.log("新建教师班级记录");
          ElMessage.success('创建班级成功');
          showCreateClassDialog.value = false;
          refreshClassInfo();
        })
    })
    .catch((err) => {
      ElMessage.error('创建班级失败');
      console.error("创建班级失败", err);
    });
}
// 删除班级
const deleteClassById = (classId) => {
  deleteClass(classId)
    .then(() => {
      console.log("删除班级成功");
      // 删除教师课程中间表的联系
      deleteExamClassTeacher(classId)
        .then(() => {
          console.log("删除教师课程关系成功");
          // 批量删除课程和学生关系
          deleteExamClassStudent(classId)
            .then(() => {
              ElMessage.success('删除班级及其关联成功');
              console.log("删除学生课程关系成功");
              refreshClassInfo();
            })
            .catch((err) => {
              console.error("删除学生课程关系失败", err);
              ElMessage.error('删除学生课程关系失败');
            });
        })
        .catch((err) => {
          console.error("删除教师课程关系失败", err);
          ElMessage.error('删除教师课程关系失败');
        });
    })
    .catch((err) => {
      console.error("删除班级失败", err);
      ElMessage.error('删除班级失败');
    });
};

const classFrom = ref({
  id: null,
});

const FindClass = () => {
  if (!classFrom.value.id) {
    ElMessage.warning('请输入班级ID');
    return;
  }
  console.log(classFrom.value.id)
  getAllClasswithteacherById(classFrom.value.id)
    .then((res) => {
      item.values = res.data;
      console.log(res.data)
    }
    )
}

</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
.el-button--primary {
  margin: 10px;
}
.buttonBox {
  position: fixed;
  top: 10px;
  right: 20px;
  z-index: 9999;
}

.footer-box{
  width: 90%; 
  display: flex; 
  justify-content: center;
  margin-top: 10px;
  padding: 10px;
}

</style>
