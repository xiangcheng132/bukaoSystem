import { createRouter, createWebHashHistory } from 'vue-router'
import Login from "@/views/login.vue"
const routes = [
  {
    path: '/',
    redirect:'/login',
    name: 'index',
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/login',
    name: 'login',
    component: () => import( '@/views/register.vue')
  },
  {
    path: '/student',
    name: 'student',
    component:() => import( '@/views/student/index.vue')
  },
  {
    path: '/teacher',
    name: 'teacher',
    component:() => import( '@/views/teacher/index.vue')
  },
  {
    path: '/manager',
    name: 'manager',
    component:() => import( '@/views/manager/index.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
