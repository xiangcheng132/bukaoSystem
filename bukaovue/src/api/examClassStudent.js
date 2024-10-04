import request from "@/utils/axios"

// 获取所有 ExamClassStudent 记录。
export function getExamClassTeacherById(){
  return request({
    url:'/bukaoSystem/classStudent',
    method: 'get'
  })
} 

// 根据 classIdID 获取 Student名字。
export function getExamClassStudentsById(classId){
  return request({
    url:'/bukaoSystem/classStudent/getStudentByClassId',
    method: 'POST',
    data:{classId}
  })
} 

// 根据 ID 获取 ExamClassStudent 记录。
export function getExamClassStudentById(id){
  return request({
    url:'/bukaoSystem/classStudent/getById',
    method: 'POST',
    data:{id}
  })
} 

// 根据 Class ID 获取 ExamClassStudent 列表
export function getExamClassStudentByClassId(classId){
  return request({
    url:'/bukaoSystem/classStudent/getByClassId',
    method: 'POST',
    data:{classId}
  })
} 

// 根据 Student ID 获取 ExamClassStudent 列表
export function getExamClassStudentByStuId(studentId){
  return request({
    url:'/bukaoSystem/classStudent/getByStudentId',
    method: 'POST',
    data:{studentId}
  })
} 


// 创建新的 ExamClassStudent 记录。
export function createExamClassStudent(classId,studentId){
  return request({
    url:'/bukaoSystem/classStudent/create',
    method: 'POST',
    data:{classId,studentId}
  })
} 

// 更新 ExamClassStudent
export function updateExamClassStudent(id,classId,studentId,createTime){
  return request({
    url:'/bukaoSystem/classStudent/update',
    method: 'POST',
    data:{id,classId,studentId,createTime}
  })
} 

// 删除 ExamClassStudent
export function deleteExamClassStudent(id){
  return request({
    url:'/bukaoSystem/classStudent/delete',
    method: 'POST',
    data:{id}
  })
} 




