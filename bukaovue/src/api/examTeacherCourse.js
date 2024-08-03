import request from "@/utils/axios"

//获取所有 ExamTeacherCourse
export function getAllExamTeacherCourse() {
    return request.get('/bukaoSystem/teacherCourse')
}

//根据 ID 获取 ExamTeacherCourse
export function getExamTeacherCourseById(id) {
    return request.post('/bukaoSystem/teacherCourse/getById', {id})
}

//根据 TeacherId 获取 ExamTeacherCourse
export function getExamTeacherCourseByTeacherId(teacherId) {
    return request.post('/bukaoSystem/teacherCourse/getByTeacherId', {teacherId})
}

//根据 CourseId 获取 ExamTeacherCourse
export function getExamTeacherCourseByCourseId(courseId) {
    return request.post('/bukaoSystem/teacherCourse/getByCourseId', {courseId})
}

//创建新的 ExamTeacherCourse
export function createExamTeacherCourse(courseInfo) {
    return request.post('/bukaoSystem/teacherCourse/create', {...courseInfo})
}

//更新现有的 ExamTeacherCourse
export function updateExamTeacherCourse(courseInfo) {
    return request.post('/bukaoSystem/teacherCourse/update', {...courseInfo})
}

//删除现有的 ExamTeacherCourse
export function deleteExamTeacherCourse(teacherId, courseId) {
    return request.post('/bukaoSystem/teacherCourse/delete', {teacherId, courseId})
}