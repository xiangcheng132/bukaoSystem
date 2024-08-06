<template>
  <el-button type="primary" @click="">新建课程</el-button>
  <div>
    <el-table :data="examTableList" style="width: 100%">
      <!-- <el-table-column fixed prop="id" label="Id" width="300" /> -->
      <el-table-column prop="name" label="课程名" width="300" />
      <el-table-column prop="comment" label="课程详情" width="400" />
      <el-table-column prop="createTime" label="创建时间" width="300" />
      <el-table-column fixed="right" label="操作" min-width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click.prevent="">
            修改
          </el-button>
          <el-button link type="primary" size="small"  @click.prevent="">删除</el-button>
        </template>
    </el-table-column>
  </el-table>
  </div>
</template>
<script setup>
import {ref,reactive,onMounted} from 'vue'

let examTableList = reactive([
  {
    name:'1',
    comment:'2',
    createTime:'3'
  }
])

onMounted(()=>{
  
})
</script>
    
<style lang = 'less'>
.el-button--primary {
  margin: 10px;
}
.el-table td.el-table__cell, .el-table th.el-table__cell.is-leaf{
  text-align: center;
}
</style>