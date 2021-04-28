import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'
import LayoutView from '../views/layout/LayoutView'

/**
* hidden: true                   if 'hidden:true' will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if 'redirect:noredirect' will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
**/
export const constantRouterMap = [
  { path: '/404', component: () => import('@/views/404'), hidden: true },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    children: [{
      path: '/dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
  {
    path: '/doc',
    component: Layout,
    name: 'doc',
    children: [
      {
        path: '/docManager',
        name: 'docManager',
        component: () => import('@/views/pages/docManager')
      }
    ]
  },
  {
    path: '/addDoc',
    component: Layout,
    name: 'addDoc',
    children: [
      {
        path: '/newDoc',
        name: 'newDoc',
        component: () => import('@/views/pages/newDoc'),
        meta: { title: '添加文档' }
      }
    ]
  },
  {
    path: '/addProject',
    component: Layout,
    name: 'addProject',
    children: [
      {
        path: '/newProject',
        name: 'newProject',
        component: () => import('@/views/pages/newProject'),
        meta: { title: '创建项目' }
      }
    ]
  },
  // view
  {
    path: '/view',
    component: LayoutView,
    redirect: '/view',
    name: 'View',
    children: [{
      path: '/view',
      component: () => import('@/views/dashboard/view'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
  {
    path: '/view',
    component: LayoutView,
    name: 'ViewHome',
    children: [
      {
        path: '/docView',
        name: 'DocView',
        component: () => import('@/views/pages/docView'),
        meta: { title: '接口文档' }
      }
    ]
  }
]

export default new Router({
  // mode: 'history', // 如果是单独部署，可以打开；如果是放入到服务端程序里面，不能开，否则会出现空白页面
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
