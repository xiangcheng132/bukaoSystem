import {setStorage,getStorage} from "@/utils/storage.js";
import router from "@/router";
import {getAllUserById} from "@/api/examUser.js";
import { userInfoRefrsh } from "@/utils/userInfoRefrsh";
export default{
  namespaced: true,
  state: {
    token: getStorage("token") || "",
    userInfo: userInfoRefrsh("token") || ""
  },
  mutations: {
    setTokenData(state,token){
      //context action 独有的访问状态的方法
      // console.log(response);
      // console.log(token);
     state.token = token;
     setStorage("token",token);
    },
    // 设置用户信息
    setUserInfo(state,userInfo){
      console.log(userInfo);
     state.userInfo = userInfo;
    }
  },
  getters: {
    // 当前播放的歌曲对象
    // currentSong(state){
    //   return state.musicPlayList[state.currentIndex] || {}
    // }
  },
  actions: {
    // 发起登录请求后的操作
    async loginSuccessful(context,data){
      // console.log(data);
          // console.log(data.token);
          try{
            await context.commit("setTokenData",data.id);
            await context.commit("setUserInfo",data);
          }catch{
            console.log("id获取失败");
          }
    
          if(context.state.userInfo.role == "student"){
             router.push("/student");
           
          }
          if(context.state.userInfo.role == "teacher"){
            console.log("进入教师端");
             router.push("/teacher");
            
          }
          if(context.state.userInfo.role == "admin"){
             router.push("/manager");
            
          }
      },
    // 获取用户的信息
    async getUserInfo(context){
      return getAllUserById({id:context.state.token}).then(data => {
                // console.log(data);
                context.commit("setUserInfo",data.data);
                return Promise.resolve("获取用户信息成功")
            })
             .catch(()=>{
                return Promise.reject("获取用户信息失败");
             })
      
    },
    async loginOut(context){
      context.commit("setTokenData","");
      context.commit("setUserInfo",[]);
      this.commit("permission/setRoutesData",[]);
      router.push("/login");
    }
  }
}
