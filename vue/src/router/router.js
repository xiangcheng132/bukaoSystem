import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/Home/Home'

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/login',
        },
        {
            path: '/',
            name: '',
            component:1
        },
        {
            path: '/home',
            name: 'home',
            component: Home,
            children: [
                {path: '', component: 1},
            ]
        }
    ]
})
