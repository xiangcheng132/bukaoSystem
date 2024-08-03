import { getStorage } from "./storage";
import { getAllUserById } from "@/api/examUser";
import store from "@/store"
export function userInfoRefrsh(token) {
  // console.log(token);
    getAllUserById(getStorage(token)).then(res=>{
      // console.log(store);
      store.commit("user/setUserInfo",res.data)
      console.log("用户信息刷新成功");
      return;
    }).catch(err=>{
      // console.log(err);
      return "";
    })
}
