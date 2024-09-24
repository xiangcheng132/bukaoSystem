<template>
  <div class="home">
    <nav class="navbar">
      <div class="container">
        <span class="title">学在布考</span>
      </div>
    </nav>
    <div class="main-content">
      <aside class="sidebar">
        <router-link to="/student/notice" class="sidebar-link">考试通知</router-link>
        <!-- <div class="dropdown" @mouseover="showDropdown = true" @mouseleave="showDropdown = false">
          <router-link to="/student/scourse" class="sidebar-link">课程中心</router-link>
          <div v-if="showDropdown" class="dropdown-content">
            <router-link to="/student/scourse/exam" class="dropdown-item">考试页</router-link>
            <router-link to="/student/scourse/resource" class="dropdown-item">资源页</router-link>
          </div>
        </div>
        <router-link to="/student/course" class="sidebar-link">我的课程</router-link> -->
        <router-link to="/student/spcenter" class="sidebar-link">个人中心</router-link>
      </aside>
      <main class="content">
        <router-view></router-view>
      </main>

    </div>
  </div>
  <footer>@227总裁基地版权所有</footer>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      showDropdown: false,
    };
  },
};
</script>

<style>
.home {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.navbar {
  background-color: #007bff;
  color: #fff;
  padding: 1em 2em;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.navbar .title {
  font-size: 1.5em;
  font-weight: bold;
  float: left;
}

.main-content {
  display: flex;
  flex: 1;
}

.sidebar {
  width: 240px;
  background-color: whitesmoke;
  border-right: 1px solid grey;
  padding: 1em;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
  position: relative;
}

.sidebar-link {
  display: block;
  color: #007bff;
  padding: 12px;
  text-decoration: none;
  border-radius: 4px;
  font-weight: 500;
  margin-bottom: 10px;
}

.sidebar-link:hover {
  background-color: #f0f2f5;
}

.dropdown {
  position: relative;
}

.dropdown-content {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 100%;
  z-index: 1000;
}

.dropdown-item {
  display: block;
  padding: 12px;
  color: #007bff;
  text-decoration: none;
  font-weight: 500;
}

.dropdown-item:hover {
  background-color: #f0f2f5;
}

.content {
  flex: 1;
  padding: 1em 2em;
  overflow-y: auto;
  background-color: #fff;
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

</style>
