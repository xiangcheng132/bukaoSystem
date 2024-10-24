<template>
	<div class="login-register">
		<div class="contain">
			<!-- 主体表单 -->
			<div class="big-box" :class="{active:change}">
				<!-- 登陆表单 -->
				<div class="big-contain" key="bigContainLogin" v-if="change">
					<div class="btitle">账户登录</div>
					<div class="bform">
						<div>
							<span>账号名: &nbsp;</span><input type="text" placeholder="账号名" v-model="form.account" required>
						</div>
						<div>
							<span>密码: &nbsp;&nbsp;</span><input type="password" placeholder="密码" v-model="form.password" required>
						</div>
					</div>
					<button class="bbutton" ><el-button @click="login">登录</el-button></button>
				</div>
				<!-- 注册表单 -->
				<div class="big-contain" key="bigContainRegister" v-else>
					<div class="btitle">注册账户</div>
					<div class="bform">
						<div>
							<span>用户名: </span>
              <input type="text" placeholder="用户名"  v-model="form.username" required>
						</div>
            <div>
							<span>账号: &nbsp;&nbsp;&nbsp;</span>
              <input type="text" placeholder="账号"  v-model="form.account" required>
						</div>
						<div>
							<span>邮箱: &nbsp;&nbsp;&nbsp;</span>
							<input type="text" placeholder="邮箱" v-model="form.email" required>
						</div>
						<div>
							<span>密码:  &nbsp;&nbsp;&nbsp; </span>
							<input type="password" placeholder="密码" v-model="form.password" required>
						</div>
						<div>
							<span>身份:  &nbsp;&nbsp;&nbsp;</span>
							  <el-select v-model="form.role" placeholder="请选择您的身份信息" style="width: 264px">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
						</div>
            <div>
							<span>电话:  &nbsp;&nbsp;&nbsp;</span>
							<input type="text" placeholder="电话" v-model="form.phone" required>
						</div>
            <div>
							
            <div class="my-2 flex items-center text-sm">
              <span style="position:relative; left:-81px">性别: &nbsp;&nbsp;&nbsp;</span>
              <el-radio-group v-model="form.sex" class="ml-4">
                <el-radio value="男">男</el-radio>
                <el-radio value="女">女</el-radio>
              </el-radio-group>
           </div>
						</div>
					</div>
						<button class="bbutton" ><el-button  @click="submitRegister">注册</el-button></button>
				</div>
			</div>
			<!-- 切换表单 -->
			<div class="small-box" :class="{active:change}">
				<div class="small-contain" key="smallContainRegister" v-if="change">
					<div class="stitle">你好，朋友!</div>
					<p class="scontent">开始注册，和我们一起进入布考的世界吧</p>
					<button class="sbutton" @click="changeType">注册</button>
				</div>
				<div class="small-contain" key="smallContainLogin" v-else>
					<div class="stitle">欢迎回来!</div>
					<p class="scontent">耀考试来布考，布考欢迎您的到来</p>
					<button class="sbutton" @click="changeType">登录</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { useRoute,useRouter } from "vue-router";
import { onMounted, reactive, ref , toRefs, toRaw, h } from "vue";
import { userLogin, createUser } from "@/api/examUser.js";
import {useStore} from "vuex"
import { ElMessage } from "element-plus";
import {setStorage,getStorage} from "@/utils/storage.js";

const regEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const phoneRegex = /^(\+\d{1,3}[- ]?)?\(?(\d{3})\)?[-. ]?(\d{3})[-. ]?(\d{5})$/;
let route = useRoute();
let router = useRouter();
let store = useStore();

let change = ref(true);
const options = [
  {
    value: 'student',
    label: '学生',
  },
  {
    value: 'teacher',
    label: '教师',
  },
  {
    value: 'admin',
    label: '管理员',
  }
]
let form = reactive({
  username: "",
  email: "",
  password: "",
  role: "",
  phone: "",
  sex: "",
  account:""
});
//转换视图
function changeType() {
  // console.log(change);
  change.value = !change.value;
  form.username = "";
  form.email = "";
  form.password = "";
  form.role = "";
  form.phone = "";
  form.sex = "";
  form.account="";
}


// let loginSuccessful = computed(() => store.state.user.loginSuccessful);

let k = false;
let loginSuccessfulInfo = {};
// 用户登录
async function submitLogin() {
  if(form.account != "" && form.password!= ""){
     const userInfo = toRaw(form);
     const {account,password} = userInfo;
       loginSuccessfulInfo = await userLogin({account,password}).then(res =>{
      if(res.data == ""){
        ElMessage("账号不存在");
        k = false;
        return null;
      }else{
        ElMessage("登录成功");
        k = true;
        return res.data;
      }
      }).catch(err=>{
        // console.log(err);
        k = false;
        return null;
      })
  }else{
    ElMessage("用户信息不完全");
    k = false;
  }
}

async function login(){ 
    await submitLogin();
    if(k){
      console.log("k为ture");
      store.dispatch("user/loginSuccessful",loginSuccessfulInfo);
    }else{
      ElMessage("登录失败");
      return;
    }
}


//用户注册
function submitRegister() {

  if(form.email == "" ||(form.email != "" && !regEmail.test(form.email))){
    ElMessage("邮箱输入错误");
    return;
  }
  else if(form.role != "teacher" && form.role != "student" && form.role!="admin" && form.role != "" || form.role == ""){
    ElMessage("身份信息只能是教师或学生，且不为空");
  } 
  else if(form.phone == "" ||(form.phone != "" && !phoneRegex.test(form.phone))){
    ElMessage("电话输入错误");
    return;
  }
  else if(form.sex != "男" && form.sex != "女" && form.sex != "" || form.sex == ""){
    ElMessage("性别只能是男或女，且不为空");
    return;
  }
  else if(form.password == "" || form.username == "" || form.account == ""){
    ElMessage("信息未完成");
    return;
  }else{
    console.log(form);
    const userInfo = toRaw(form);
    
    console.log(userInfo);
    createUser(userInfo).then(res=>{
      console.log(res);
      ElMessage(res.data);
      changeType();
    }).catch(err=>{
      ElMessage("注册失败");
      console.log(err);
    })
    
    
    return;
  }
}
</script>

<style scoped="scoped">
.el-button {
  background-color: rgb(181, 154, 254);
  border: none;
  --el-button-hover-bg-color: "";
}

.login-register {
  width: 100vw;
  height: 100vh;
  box-sizing: border-box;
  background-image: url(../assets/loginBg.jpg);
  background-size: cover;
}
.contain {
  width: 60%;
  height: 60%;
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  border-radius: 20px;
  box-shadow: 0 0 3px #f0f0f0, 0 0 6px #f0f0f0;
  opacity: 0.99;
}
.big-box {
  width: 70%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 30%;
  transform: translateX(0%);
  transition: all 1s;
}
.big-contain {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.btitle {
  font-size: 1.5em;
  font-weight: bold;
  color: rgb(181, 154, 254);
}
.bform {
  width: 100%;
  height: 40%;
  padding: 2em 0;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.bform .errTips {
  display: block;
  width: 50%;
  text-align: left;
  color: red;
  font-size: 0.7em;
  margin-left: 1em;
}
.bform > div {
  width: 480px;
  margin-bottom: 5px;
}
.bform input {
  width: 50%;
  height: 30px;
  border: none;
  outline: none;
  border-radius: 10px;
  padding-left: 2em;
  background-color: #f0f0f0;
}
.bbutton {
  width: 20%;
  height: 40px;
  border-radius: 24px;
  border: none;
  outline: none;
  background-color: rgb(181, 154, 254);
  color: #fff;
  font-size: 0.9em;
  cursor: pointer;
}
.small-box {
  width: 30%;
  height: 100%;
  background: linear-gradient(45deg, rgb(181, 154, 254), rgb(245, 189, 253));
  position: absolute;
  top: 0;
  left: 0;
  transform: translateX(0%);
  transition: all 1s;
  border-top-left-radius: inherit;
  border-bottom-left-radius: inherit;
}
.small-contain {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.stitle {
  font-size: 1.7em;
  font-weight: bold;
  color: #fff;
}
.scontent {
  font-size: 1em;
  color: #fff;
  text-align: center;
  padding: 2em 4em;
  line-height: 1.7em;
}
.sbutton {
  width: 60%;
  height: 40px;
  border-radius: 24px;
  border: 1px solid #fff;
  outline: none;
  background-color: transparent;
  color: #fff;
  font-size: 0.9em;
  cursor: pointer;
}

.big-box.active {
  left: 0;
  transition: all 0.5s;
}
.small-box.active {
  left: 100%;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
  border-top-right-radius: inherit;
  border-bottom-right-radius: inherit;
  transform: translateX(-100%);
  transition: all 1s;
}
.el-radio-group {
    position: relative;
    left: -78px;
    top: 2px;
}
.login-register{
  text-align: center
}
</style>
