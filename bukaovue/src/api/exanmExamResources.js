import request from "@/utils/request";

//获取所有 ExamExamResources
export function getAllExamExamResources() {
    return request.get('/bukaoSystem/examResources')
}

//根据 ID 获取 ExamExamResources
export function getExamExamResourcesById(id) {
    return request.post('/bukaoSystem/examResources/getById', {id})
}

//根据 ExamId 获取 ExamExamResources
export function getExamExamResourcesByExamId(examId) {
    return request.post('/bukaoSystem/examResources/getByExamId', {examId})
}

//根据 ResourceId 获取 ExamExamResources
export function getExamExamResourcesByResourceId(resourceId) {
    return request.post('/bukaoSystem/examResources/getByResourceId', {resourceId})
}

//创建新的 ExamExamResources
export function createExamExamResources(examId, resourceId) {
    return request.post('/bukaoSystem/examResources/create', {examId, resourceId})
}

//更新现有的 ExamExamResources
export function updateExamExamResources(resourceInfo) {
    return request.post('/bukaoSystem/examResources/update', {...resourceInfo})
}

//删除现有的 ExamExamResources
export function deleteExamExamResources(examId, resourceId) {
    return request.post('/bukaoSystem/examResources/update', {examId, resourceId})
}