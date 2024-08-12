import request from "@/utils/axios"

//获取所有 ExamClassTeacher 记录。
export function getAllExamClassTeacher(){
  return request({
    url:'/bukaoSystem/classTeacher',
    method: 'get',
  })
} 

//根据 ID 获取 ExamClassTeacher 记录。
export function getExamClassTeacherById(id){
  return request({
    url:'/bukaoSystem/classTeacher/getById',
    method: 'post',
    data:{id}
  })
} 

// 根据 Class ID 获取 ExamClassTeacher 列表
export function getExamClassTeacher(classId){
  return request({
    url:'/bukaoSystem/classTeacher/getByClassId',
    method: 'post',
    data:{classId}
  })
} 

// 根据 Class ID 获取 ExamClassTeacherName 列表
export function getExamClassTeachersnameByClassId(classId){
  return request({
    url:'/bukaoSystem/classTeacher/getTeacherNameByClassId',
    method: 'post',
    data:{classId}
  })
} 

// 创建新的 ExamClassTeacher
export function createExamClassTeacher(classInfoList){
  return request({
    url:'/bukaoSystem/classTeacher/create',
    method: 'post',
    data:{...classInfoList}
  })
} 

// 更新 ExamClassTeacher
export function updateExamClassTeacher(id,classId,teacherId){
  return request({
    url:'/bukaoSystem/classTeacher/update',
    method: 'post',
    data:{id,classId,teacherId}
  })
} 

// 删除 ExamClassTeacher
export function deleteExamClassTeacher(id){
  return request({
    url:'/bukaoSystem/classTeacher/delete',
    method: 'post',
    data:{id}
  })
} 