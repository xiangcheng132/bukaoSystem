<template>
  <div>
    <h2>个人中心</h2>
    <p>根据用户id，返回用户所有个人信息 实现</p>
    <p>根据用户输入信息修改，返回修改结果</p>
  </div>
  <h1>User Information</h1>
  <div v-if="user!=''" style="line-height: 25px">
    <p><strong>ID:</strong> {{ user.id }}</p>
    <p><strong>Name:</strong> {{ user.username }}</p>
    <p><strong>Email:</strong> {{ user.email }}</p>
    <p><strong>Phone:</strong> {{ user.phone }}</p>
    <p><strong>Sex:</strong> {{ user.sex }}</p>
    <!-- 其他用户信息 -->
  </div>
  <div v-else>
    <p>Loading...</p>
  </div>

</template>

<script>
import {getAllUserById} from '@/api/examUser';
import {getStorage} from "@/utils/storage";
import store from "@/store";
import {mapMutations} from "vuex";

export default {
  name: 'SpCenter',
  data() {
    return {
      user: ""
    };
  },
  methods: {
    fetchUserInfo(id) {
      getAllUserById(id)
          .then(response => {
            console.log(response);
            this.user = response.data;
            this.setUserInfo(response.data)

          })
          .catch(error => {
            console.error('Error fetching user info:', error);
          });
    }
  },
  created() {
    // const userId = this.$route.params.id; // Assume you're getting the user ID from the route params
    // this.fetchUserInfo(getStorage("token"))

  },
  mounted() {
    // console.log(getStorage("token"))
    this.fetchUserInfo(getStorage("token"))
  }
};
</script>

<style>
/* 确保个人中心页内容不会被其他内容覆盖 */
main {
  padding-top: 1em;
}
</style>
