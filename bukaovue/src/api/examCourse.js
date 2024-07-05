import request from "@/utils/axios"

// 根据用户获取该用户下的所有课程
export function getAllCourseByUId(id){
  return request({
    url:'/bukaoSystem/course',
    method: 'post',
    data:{id}
  })
} 
// 根据课程ID获取特定课程信息
export function getAllCourseByCId(id){
  return request({
    url:'/bukaoSystem/course/postById',
    method: 'post',
    data:{id}
  })
} 
// 根据课程名获取课程信息
export function getTCoursesByCName(name){
  return request({
    url:'/bukaoSystem/course/getByName',
    method: 'post',
    data:{name}
  })
} 
//创建课程
export function createCourse(name,comment){
  return request({
    url:'/bukaoSystem/course/create',
    method: 'post',
    data:{name,comment}
  })
} 
//更新课程
export function updateCourse(id,name,comment){
  return request({
    url:'/bukaoSystem/course/update',
    method: 'post',
    data:{name,comment,createTime}
  })
} 
//删除课程
export function deleteCourseById(id){
  return request({
    url:'/bukaoSystem/course/delete',
    method: 'post',
    data:{id}
  })
} 
