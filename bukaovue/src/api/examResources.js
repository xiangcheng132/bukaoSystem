import request from "@/utils/axios"

/* 请求字段说明
id (Long): 资源ID
courseId (Long): 课程ID （必填）
chapterId (Long): 章节ID （必填）
question (String): 题目 （必填）
type (Enum): 题目类型 (single_choice, true_false, completion, bigquestion)（默认bigquestion）
options (JsonNode): 选项
key (String): 答案
analysis (String): 解析
score (Double): 分数
createTime (String): 创建时间 */

//获取所有考试资源
export function getAllExamResources(){
  return request({
    url:'/bukaoSystem/resources',
    method: 'get',
  })
} 
//根据课程ID获取考试资源
export function getExamResourceByCId(courseId){
  return request({
    url:'/bukaoSystem/resources/getByCourseId',
    method: 'post',
    data:{courseId}
  })
} 
//根据章节ID获取考试资源
export function getExamResourceByChapId(chapterId){
  return request({
    url:'/bukaoSystem/resources/getByChapterId',
    method: 'post',
    data:{chapterId}
  })
} 
//根据资源ID获取特定考试资源信息
export function getExamResourceByCRId(id){
  return request({
    url:'/bukaoSystem/resources/getById',
    method: 'post',
    data:{id}
  })
} 
//根据资源ID获取特定考试资源信息
export function getByExamIdPlus(examId){
  return request({
    url:'/bukaoSystem/examResources/getByExamIdPlus',
    method: 'post',
    data:{examId}
  })
} 
//创建一个新考试资源
export function createExamResource(newExamResourceInfo){
  return request({
    url:'/bukaoSystem/resources/create',
    method: 'post',
    data:{...newExamResourceInfo}
  })
} 
// 更新考试资源信息
export function updateExamResource(examResourceInfo){
  return request({
    url:'/bukaoSystem/resources/update',
    method: 'post',
    data:{...examResourceInfo}
  })
} 

// 删除考试资源
export function deleteExamResource(id){
  return request({
    url:'/bukaoSystem/resources/delete',
    method: 'post',
    data:{id}
  })
} 