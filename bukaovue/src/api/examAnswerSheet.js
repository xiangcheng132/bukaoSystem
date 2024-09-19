import request from "@/utils/axios"

// 获取所有 ExamAnswerSheet 记录。
export function getExamClassTeacherById(){
  return request({
    url:'/bukaoSystem/answerSheet',
    method: 'get'
  })
} 
export function getByTeacherId(id){
  return request({
    url:'/bukaoSystem/answerSheet/getByTeacherId',
    method: 'post',
    data:{id}
  })
} 

// 根据 ID 获取 ExamAnswerSheet 记录。
export function getExamAnswerSheetById(id){
  return request({
    url:'/bukaoSystem/answerSheet/getById',
    method: 'POST',
    data:{id}
  })
} 

// 根据 User ID 获取 ExamAnswerSheet 列表
export function getExamAnswerSheetByUserId(userId){
  return request({
    url:'/bukaoSystem/answerSheet/getByUserId',
    method: 'POST',
    data:{userId}
  })
} 

// 根据 Exam ID 获取 ExamAnswerSheet 列表
export function getExamAnswerSheetByExamId(examId){
  return request({
    url:'/bukaoSystem/answerSheet/getByExamId',
    method: 'POST',
    data:{examId}
  })
} 


// 根据 Exam ID 获取 ExamAnswerSheet 列表
export function getByAnswerld(answerId){
  return request({
    url:'/bukaoSystem/answerSheetDetail/getByAnswerId',
    method: 'POST',
    data:{answerId}
  })
} 


// 创建新的 ExamAnswerSheet 记录。
export function createExamAnswerSheet(examId,userId,score){
  return request({
    url:'/bukaoSystem/answerSheet/create',
    method: 'POST',
    data:{examId,userId,score}
  })
} 

// 更新 ExamAnswerSheet
export function updateExamAnswerSheet(id,examId,userId,score){
  return request({
    url:'/bukaoSystem/answerSheet/update',
    method: 'POST',
    data:{id,examId,userId,score}
  })
} 

// 删除 ExamAnswerSheet
export function deleteExamAnswerSheet(id){
  return request({
    url:'/bukaoSystem/answerSheet/delete',
    method: 'POST',
    data:{id}
  })
} 




