import request from "@/utils/axios"

//获取所有 ExamCourseChapter
export function getAllExamCourseChapter() {
    return request.get('/bukaoSystem/courseChapter')
}

//根据 ID 获取 ExamCourseChapter
export function getExamCourseChapterById(id) {
    return request.post('/bukaoSystem/courseChapter/getById', {id})
}

//创建新的 ExamCourseChapter
export function createExamCourseChappter(courseId, name) {
    return request.post('/bukaoSystem/courseChapter/create', {courseId, name})
}

//更新现有的 ExamCourseChapter
export function updateExamCourseChapter(courseChpterInfo) {
    return request.post('/bukaoSystem/courseChapter/update', {...courseChpterInfo})
}

//删除现有的 ExamCourseChapter
export function deleteExamCourseChapter(id) {
    return request.post('/bukaoSystem/courseChapter/delete', {id})
}

//根据courseId查询该课程下所有章节
export function getCourseChapterByCourseId(id) {
    return request.post('/bukaoSystem/courseChapter/getByCourseId', {id})
}