import request from "@/utils/axios"

//获取所有 ExamExamClass
export function getAllExamExamClass() {
    return request.get('/bukaoSystem/examClass')
}

//根据 ID 获取 ExamExamClass
export function getExamExamClassById(id) {
    return request.post('/bukaoSystem/examClass/getById', {id})
}

//根据 ExamId 获取 ExamExamClass
export function getExamExamClassByExamId(examId) {
    return request.post('/bukaoSystem/examClass/getByExamId', {examId})
}

//根据 ClassId 获取 ExamExamClass
export function getExamExamClassByClassId(classId) {
    return request.post('/bukaoSystem/examClass/getByClassId', {classId})
}

//创建新的 ExamExamClass
export function createExamExamClass(examId, classId) {
    return request.post('/bukaoSystem/examClass/create', {examId, classId})
}

//更新现有的 ExamExamClass
export function updateExamExamClass(examClassInfo) {
    return request.post('/bukaoSystem/examClass/update', {...examClassInfo})
}

//删除现有的 ExamExamClass
export function deleteExamExamClass(examId, classId) {
    return request.post('/bukaoSystem/examClass/delete', {examId, classId})
}