import request from "@/utils/axios.js"

//获取所有班级
export function getAllClass() {
    return request.get('/bukaoSystem/class')
}

//根据ID获取特定班级
export function getClassById(id) {
    return request.post('/bukaoSystem/class/getById', {id})
}

//创建班级
export function createClass(classInfoList) {
    return request.post('/bukaoSystem/class/create', {...classInfoList})
}

//更新班级
export function updateClassInfo(classInfoList) {
    return request.post('/bukaoSystem/class/update', {...classInfoList})
}

//删除班级
export function deleteClass(id) {
    return request.post('/bukaoSystem/class/delete', {id})
}