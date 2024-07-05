import {setStorage,getStorage} from "@/utils/storage.js";
import router from "@/router";
// import {userInfoRequest} from "@/api/user.js";
export default{
  namespaced: true,
  state: {
    token: getStorage("token") || "",
    userInfo: []
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
     state.userInfo = userInfo;
    }
  },
  actions: {
    // 发起登录请求后的操作
    async loginSuccessful(context,data){
      // console.log(data);
          // console.log(data.token);
          await context.commit("setTokenData",data.token);
          router.push("/");
      },
    // 获取用户的信息
    async getUserInfo(context){
      return userInfoRequest({token: context.state.token}).then(data => {
                // console.log(data);
                context.commit("setUserInfo",data.data.data);
                return Promise.resolve(data.data.data.permission)
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
