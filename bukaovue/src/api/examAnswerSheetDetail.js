import request from "@/utils/axios"

//获取所有 ExamAnswerSheetDetail
export function getAllExamAnswerSheetDetail() {
    return request.get('/bukaoSystem/answerSheetDetail')
}

//根据 ID 获取 ExamAnswerSheetDetail
export function getExamAnswerSheetDetailById(id) {
    return request.post('/bukaoSystem/answerSheetDetail/getById', {id})
}

//根据 Answer ID 获取 ExamAnswerSheetDetail 列表
export function getExamAnswerSheetDetailByAnswerId(answerId) {
    return request.post('/bukaoSystem/answerSheetDetail/getByAnswerId', {answerId})
}

//根据 Resource ID 获取 ExamAnswerSheetDetail 列表
export function getExamAnswerSheetDetailByResourceId(resourceId) {
    return request.post('/bukaoSystem/answerSheetDetail/getByResourceId', {resourceId})
}

//创建新的 ExamAnswerSheetDetail
export function createExamAnswerSheetDetail(examAnswerSheetDetailInfo) {
    return request.post('/bukaoSystem/answerSheetDetail/create', {...examAnswerSheetDetailInfo})
}

//更新 ExamAnswerSheetDetail
export function updateExamAnswerSheetDetail(examAnswerSheetDetailInfo) {
    return request.post('/bukaoSystem/answerSheetDetail/update', {...examAnswerSheetDetailInfo})
}

//删除 ExamAnswerSheetDetail
export function deleteExamAnswerSheetDetail(id) {
    return request.post('/bukaoSystem/answerSheetDetail/delete', {id})
}



export function saveOrUpdateExamAnswerSheetDetail(userKeyInfo) {
    return request.post('/bukaoSystem/answerSheetDetail/saveOrUpdate', userKeyInfo)
}