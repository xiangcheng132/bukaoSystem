import request from "@/utils/axios"

//获取所有用户
export function getAllUser() {
    return request.get('/bukaoSystem/users')
}
//根据课程获取特定用户
export function findAllStudent(classId) {
    return request.post('/bukaoSystem/users/getAllStudent', {classId})
}
//根据用户名获取特定用户
export function getAllUserByUsername(username) {
    return request.post('/bukaoSystem/users/getAllUserByUsername', {username})
}

//根据ID和用户名获取特定用户
export function getUserByIdAndUsername(id,username) {
    return request.post('/bukaoSystem/users/getUserByIdAndUsername', {id,username})
}

//根据ID获取特定用户
export function getAllUserById(id) {
    return request.post('/bukaoSystem/users/getById', {id})
}

//创建用户
export function createUser(userInfoList) {
    return request.post('/bukaoSystem/users/create', {...userInfoList})
}

//更新用户
export function updateUserInfo(userInfoList) {
    return request.post('/bukaoSystem/users/update', {...userInfoList})
}

//删除用户
export function deleteUser(id) {
    return request.post('/bukaoSystem/users/delete', {id})
}

//登录
export function userLogin(loginData) {
    return request.post('/bukaoSystem/users/login', {...loginData})
}