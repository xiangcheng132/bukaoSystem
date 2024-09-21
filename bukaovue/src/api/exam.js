import request from "@/utils/axios"

/* {请求字段说明
  "courseId": 103,
  "name": "Physics Final Exam",
  "comment": "Final exam for the physics course",
  "place": "Room 103",
  "state": 1,
  "beginTime": "2021-07-01 09:00:00",
  "endTime": "2021-07-01 11:00:00",
  "createTime": "2021-03-01 00:00:00"
} */

//获取所有考试
export function getAllExam(){
  return request({
    url:'/bukaoSystem/exam',
    method: 'get',
  })
} 
//根据考试ID获取特定考试信息
export function getExamInfoById(id){
  return request({
    url:'/bukaoSystem/exam/getById',
    method: 'post',
    data:{id}
  })
} 
//根据课程ID获取考试信息
export function getExamInfoByCId(courseId){
  return request({
    url:'/bukaoSystem/exam/getByCourseId',
    method: 'post',
    data:{courseId}
  })
} 
//根根据考试名称获取考试信息
export function getExamInfoByEName(name){
  return request({
    url:'/bukaoSystem/exam/getByName',
    method: 'post',
    data:{name}
  })
} 
//创建一个新考试
export function createExam(examInfo) {
  return request({
    url:'/bukaoSystem/exam/create',
    method: 'post',
    data:{...examInfo}
  })
} 
//更新考试信息
export function updateExamInfo(examInfo){
  return request({
    url:'/bukaoSystem/exam/update',
    method: 'post',
    data:{...examInfo}
  })
} 
//删除考试
export function deleteExamById(id){
  return request({
    url:'/bukaoSystem/exam/delete',
    method: 'post',
    data:{id}
  })
} 
//根据学生id获取该用户的考试试卷信息
export function getExamInfoBySId(id){
  return request({
    url:'/bukaoSystem/exam/getByStuId',
    method: 'post',
    data:{id}
  })
} 
//根据教师id获取该用户的考试试卷信息
export function getExamInfoByTeaId(id){
  return request({
    url:'/bukaoSystem/exam/getByTeaId',
    method: 'post',
    data:{id}
  })
} 

export function examUp(examInfo){
  return request({
    url:'/bukaoSystem//exampaper',
    method: 'post',
    data:{examInfo}
  })
} 
