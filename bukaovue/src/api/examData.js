import request from "@/utils/axios.js"

//获取所有数据
export function getExamData() {
    return request.get('/bukaoSystem/data')
}
//获取新增所有数据
export function getMonthlyUserData() {
    return request.post('/bukaoSystem/data/getdata')
}