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


  //学生端路由
  {
    path: '/student',
    name: 'student',
    redirect:'/student/notice',
    component:() => import( '@/views/student'),
    meta:{
      title:'学生管理页'
    },
    children:[
      {
        path:'/student/notice',
        name:'notice',
        component:()=>import("@/views/student/notice"),
        meta:{
          title:'通知页'
        },
      },
      {
        path: '/student/scourse',
        name: 'scourse',
        redirect:'/student/scourse/exam',
        component:() => import( '@/views/student/scourse'),
        meta:{
          title:'学生课程页'
        },
        children:[
          {
            path: '/student/scourse/exam',
            name: 'exam',
            component:() => import( '@/views/student/scourse/exam'),
            meta:{
              title:'学生考试页'
            },
          },
          {
            path: '/student/scourse/resource',
            name: 'resource',
            component:() => import( '@/views/student/scourse/resource'),
            meta:{
              title:'学生课程资源页'
            },
          }
        ]
      },
      {
        path: '/student/spcenter',
        name: 'spcenter',
        component:() => import( '@/views/student/spcenter'),
        meta:{
          title:'个人中心页'
        }
      } 
    ]
  },
  //教师端路由
  {
    path: '/teacher',
    name: 'teacher',
    redirect:'/teacher/tCourseManager',
    component:() => import( '@/views/teacher'),
    meta:{
      title:'教师管理页'
    },
    children:[
      {
        path:'/teacher/textManager',
        name:'textManager',
        redirect:"/teacher/textManager/textModify",
        component:()=>import("@/views/teacher/textManager"),
        meta:{
          title:'试卷管理页'
        },
        children:[
          {
            path: '/teacher/textManager/textModify',
            name: 'textModify',
            component:() => import( '@/views/teacher/textManager/textModify'),
            meta:{
              title:'试卷修改页'
            }
          },
            {
              path: '/teacher/textManager/textUp',
              name: 'textUp',
              component:() => import( '@/views/teacher/textManager/textUp'),
              meta:{
                title:'试卷上传页'
              }
            }
        ]
      },
      {
        path: '/teacher/tCourseManager',
        name: 'tCourseManager',
        component:() => import( '@/views/teacher/tCourseManager'),
        meta:{
          title:'课程管理页'
        }
      },
      {
        path: '/teacher/tResourceManager',
        name: 'tResourceManager',
        component:() => import( '@/views/teacher/tResourceManager'),
        meta:{
          title:'资源管理页'
        }
      },
      {
        path: '/teacher/addResource',
        name: 'addResource',
        component:() => import( '@/views/teacher/tResourceManager/addResource'),
        meta:{
          title:'资源添加页'
        }
      }
    ]
  },

  //管理端路由
  {
    path: '/manager',
    name: 'manager',
    redirect:'/manager/userManager',
    component:() => import( '@/views/manager/index.vue'),
    meta:{
      title:'管理端首页'
    },
    children:[
      {
        path: '/manager/userManager',
        name: 'userManager',
        component:() => import( '@/views/manager/userManager/list.vue'),
        meta:{
          title:'用户列表页'
        }
      },
      {
        path: '/manager/userModify',
        name: 'userModify',
        component:() => import( '@/views/manager/userModify/edit.vue'),
        meta:{
          title:'用户管理页'
        }
      },
      {
        path: '/manager/mCourseManager',
        name: 'mCourseManager',
        component:() => import( '@/views/manager/mCourseManager/index.vue'),
        meta:{
          title:'管理端课程管理页'
        }
      },
      {
        path: '/manager/classManager',
        name: 'classManager',
        component:() => import( '@/views/manager/classManager'),
        meta:{
          title:'班级管理页'
        }
      },
      {
        path: '/manager/dataStatistics',
        name: 'dataStatistics',
        component:() => import( '@/views/manager/dataStatistics'),
        meta:{
          title:'数据统计页'
        }
      }
    ]
    
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
