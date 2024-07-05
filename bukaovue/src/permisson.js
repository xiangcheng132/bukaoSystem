import router from "./router/index.js";
import store from "@/store";

// 因为响应的数据是模拟返回来的 对象, 即便对象内定义状态码为失败的，但axios依旧会认为是成功
/*
  1.判断token是否存在,，
  2.token不存在获取数据库用户信息（数据库不存在弹窗账号不存在，数据库存在（存入本地存入user状态）
  3.判断用户身份信息，若是学生student跳转学生首页，是教师teacher跳转教师首页，是管理员admin跳转管理员首页
  4.token存在 获取token信息拿到身份判断用户身份信息，若是学生student跳转学生首页，是教师teacher跳转教师首页，是管理员admin跳转管理员首页。
  */
const whiteList = ["/login"];
// 定义前置守卫
router.beforeEach(async (to, from, next)=>{
  console.log(to.path," ",from.path);
  if(store.state.user.token){
    // if(/\/login/i.test(to.path)){
    //   console.log("经过loginto匹配分支");
    //   return next();
    // }
    
    next();
  }else{
     return whiteList.includes(to.path) ? next() : next("/login");
  }
})

