import request from "@/utils/axios"

//获取所有 ExamCourseClass
export function getAllExamCourseClass() {
    return request.get('/bukaoSystem/courseClass')
}

//根据 ID 获取 ExamCourseClass
export function getExamClassById(id) {
    return request.post('/bukaoSystem/courseClass/getById', {id})
}
//根据 ID 获取 ExamCourseClass
export function getByCourseId(id) {
    return request.post('/bukaoSystem/courseClass/getByCourseId', {courseId:id})
}

//创建新的 ExamCourseClass
export function createExamCourseClass(classId, courseId) {
    return request.post('/bukaoSystem/courseClass/create', {classId:classId, courseId:courseId})
}

//更新现有的 ExamCourseClass
export function updateExamCourseClass(courseClassInfo) {
    return request.post('/bukaoSystem/courseClass/create', {...courseClassInfo})
}

//删除现有的 ExamCourseClass
export function deleteExamCourseClass(id) {
    return request.post('/bukaoSystem/courseClass/delete', {id})
}