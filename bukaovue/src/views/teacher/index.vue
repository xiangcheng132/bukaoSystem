<template>
  <div>
    教师端首页
  </div>
</template>

<script setup>
  import {getAllTCourseByCId, getAllTCourseByUId, getTCoursesByCName ,createCourse} from "@/api/teacherApi/course.js"
  import { getAllExam } from "@/api/teacherApi/exam.js"
  import {getExamTeacherCourseByTeacherId} from '@/api/teacherApi/examClassStudent.js'
  import { createExamClassStudent } from "@/api/teacherApi/examClassStudent.js"

// getExamTeacherCourseByTeacherId(2).then(res=>{
//   console.log(res)
// })
// createExamClassStudent(1,3).then(res=>{
//   console.log(res)
// })

/*   getAllExam().then(res=>{
    console.log(res)
  }) */
  
</script>
    
<style lang = 'less'>
    
</style>