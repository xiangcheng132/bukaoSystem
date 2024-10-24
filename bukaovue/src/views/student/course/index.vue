<template>
  <div>
    <h2>我学的课</h2>
    <!--    <p>根据用户id返回返回所有用户已加入的所有课程信息</p>-->
    <!--    <p>加入班级-&#45;&#45;根据输入的用户id和班级id将用户加入班级，返回加入结果信息</p>-->
  </div>
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item>
      <!-- <el-button type="primary" @click="showJoinClassModal" class="yangshi">加入班级</el-button> -->
    </el-form-item>
    <el-form-item class="yangshi2">
      loading...
    </el-form-item>
  </el-form>

  <!--  <div>-->
  <!--    <el-table :data="item.value" style="width: 100%">-->
  <!--      &lt;!&ndash; <el-table-column fixed prop="id" label="Id" width="300" /> &ndash;&gt;-->
  <!--      <el-table-column prop="name" label="课程名" width="300" />-->
  <!--      <el-table-column prop="comment" label="课程详情" width="400" />-->
  <!--      <el-table-column prop="createTime" label="创建时间" width="300" />-->
  <!--    </el-table>-->
  <!--  </div>-->
  <!-- 加入班级的弹窗 -->
  <div v-if="showModal" class="addClass">
    <div class="modal-content">
      <h3>加入班级</h3>
      <label for="classId" style="margin-top: 20px">班级ID:</label>
      <input v-model="classId" id="classId" type="text" placeholder="请输入班级ID"/><br>
      <label for="studentId" style="margin-top: 20px">学生ID:</label>
      <input v-model="studentId" id="studentId" type="text" placeholder="请输入学生ID"/><br>

      <button @click="joinClass" style="margin-top: 10px">确认</button>
      <button @click="closeModal" style="margin-left: 5px">取消</button>
    </div>
  </div>

</template>

<script>
import {createExamClassStudent} from "@/api/examClassStudent.js"; // 导入接口方法

export default {
  data() {
    return {
      showModal: false,  // 控制弹窗显示与隐藏
      classId: "",       // 存储班级ID
      studentId: "",     // 存储学生ID
    };
  },
  methods: {
    // 显示弹窗
    showJoinClassModal() {
      this.showModal = true;
    },
    // 关闭弹窗
    closeModal() {
      this.showModal = false;
      this.classId = "";
      this.studentId = "";
    },

    // 加入班级操作
    async joinClass() {
      try {
        if (!this.classId || !this.studentId) {
          alert("请填写班级ID和学生ID！");
          return;
        }

        // 调用createExamClassStudent方法，将学生加入对应班级
        await createExamClassStudent(this.classId, this.studentId);
        alert("加入班级成功！");
        this.closeModal();
      } catch (error) {
        console.error("加入班级失败:", error);
        alert("加入班级失败，请重试！");
      }
    },
  },
};
</script>

<style scoped>
.addClass {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 300px;
}

.yangshi {
  margin-top: 10px;
}

.yangshi2 {
  position: relative;
  top: 50px;
  right: 118px;
}
</style>
