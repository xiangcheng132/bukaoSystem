<template>
    <div>
    <header>
      <a href="#" class="brand">Logo</a>
      <div class="menu-btn" @click="toggleMenu"></div>
      <div class="navigation" :class="{ active: isMenuActive }">
        <div class="navigation-items">
          <a href="#/login">登录</a>
        </div>
      </div>
    </header>
    <section class="home">
        <video v-for="(slide, index) in slides" 
           :key="index" 
           class="video-slide" 
           :class="{ active: index === currentSlide }" 
           :src="slide.src" 
           autoplay muted loop>
        </video>
      <div v-for="(content, index) in contents" :key="index" class="content" :class="{ active: currentSlide === index }">
        <h1>{{ content.title }}<br><span>{{ content.subtitle }}</span></h1>
        <p>{{ content.text }}</p>
        <a href="#/login">登录</a>
      </div>
      <div class="slider-navigation">
      <div
        v-for="(btn, index) in slides" 
        :key="index"
        class="nav-btn"
        :class="{ active: currentSlide === index }"
        @click="sliderNav(index)"
      ></div>
    </div>
    </section>
  </div>
</template>
  
<script setup>

import { ref } from 'vue';
import '@/styles/style.css'; 

const isMenuActive = ref(false);
const currentSlide = ref(0);
const slides = ref([
  { src: require('@/assets/home.mp4') },
  { src: require('@/assets/home2.mp4') },
]);
const contents = ref([
  { title: '欢迎使用我们的线上考试平台', subtitle: '布考线上考试平台', text: '我们的线上考试平台让您能够在任何地点、任何时间参加考试，无需排队或奔波，轻松应对各种测试需求。无论是选择题、填空题，还是主观题，我们的平台都能满足您的需求。通过多种题型，全面评估考生的知识和能力。' },
  { title: 'Welcome to Our Online Exam Platform.', subtitle: 'BuKao Online Exam Platform', text: 'Our online exam platform allows you to take tests from any location at any time, eliminating the need to queue or rush around, making it easy to tackle various testing needs.' },
]);

const toggleMenu = () => {
  isMenuActive.value = !isMenuActive.value;
};

const sliderNav = (index) => {
  currentSlide.value = index;
};
</script>

  
  <style>
 
  </style>
  