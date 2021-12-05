import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: Route configuration item
 *
 * hidden: true                   // When setting true The route will no longer appear in the sidebar Such as401,loginSuch as the page,或者Such as一些编辑页面/edit/1
 * alwaysShow: true               // When you have a route below children The declared route is greater than1When a,Automatically becomes a nested mode--Such as component pages
 *                                // There is only one time,The child route is displayed in the sidebar as the root route--Such as the boot page
 *                                // If you don't want the pipe to go from the bottom children The number of declared routes shows your root route
 *                                // You can set alwaysShow: true,It then ignores the previously defined rule,The root route is always displayed
 * redirect: noRedirect           // When setting noRedirect The route is not clickable in breadcrumb navigation
 * name:'router-name'             // Set the name of the route,Be sure to fill it out or use it<keep-alive>All kinds of problems can arise
 * meta : {
    roles: ['admin','editor']    // Set the access permission of the route,Multiple permissions can be added
    title: 'title'               // Sets the name of the route displayed in the sidebar and breadcrumbs
    icon: 'svg-name'             // Sets the icon of the route,Corresponding pathsrc/icons/svg
    breadcrumb: false            // If set tofalse,Is not inbreadcrumbAs shown in the bread crumbs
  }
 */

// Public routing
export const constantRoutes = [{
  path: '/redirect',
  component: Layout,
  hidden: true,
  meta: { requireAuth: true },
  children: [{
    path: '/redirect/:path(.*)',
    component: (resolve) => require(['@/views/redirect'], resolve)
  }]
},
  {
    path: '/login',
    component: (resolve) => require(['@/views/login'], resolve),
    hidden: true
  },
  {
    path: '/password',
    component: (resolve) => require(['@/views/password1'], resolve),
    meta: {
      requireAuth: false // Configure this,Determine whether you need to log in before entering the page
    },
    hidden: true
  },
  {
    path: '/404',
    component: (resolve) => require(['@/views/error/404'], resolve),
    hidden: true
  },
  {
    path: '/401',
    component: (resolve) => require(['@/views/error/401'], resolve),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: 'index',
    children: [{
      path: 'index',
      component: (resolve) => require(['@/views/index'], resolve),
      name: 'Overview',
      meta: { title: 'Overview', icon: 'dashboard', noCache: true, affix: true }
    }]
  },
  {
    path: '/information',
    component: Layout,
    // redirect: 'info',
    hidden: true,
    children: [{
      path: 'info',
      component: (resolve) => require(['@/views/information/info/index'], resolve),
      name: 'The message',
      meta: { title: 'The message', icon: 'dashboard' }
    }]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [{
      path: 'profile',
      component: (resolve) => require(['@/views/system/user/profile/index'], resolve),
      name: 'Profile',
      meta: { title: 'Personal center', icon: 'user' }
    }]
  },
  {
    path: '/dict',
    component: Layout,
    hidden: true,
    children: [{
      path: 'type/data/:dictId(\\d+)',
      component: (resolve) => require(['@/views/system/dict/data'], resolve),
      name: 'Data',
      meta: { title: 'The data dictionary', icon: '' }
    }]
  },
  {
    path: '/job',
    component: Layout,
    hidden: true,
    children: [{
      path: 'log',
      component: (resolve) => require(['@/views/monitor/job/log'], resolve),
      name: 'JobLog',
      meta: { title: 'Operation log' }
    }]
  },
  {
    path: '/gen',
    component: Layout,
    hidden: true,
    children: [{
      path: 'edit/:tableId(\\d+)',
      component: (resolve) => require(['@/views/tool/gen/editTable'], resolve),
      name: 'GenEdit',
      meta: { title: 'Modifying the Build Configuration' }
    }]
  },
  {
    path: '/vacation',
    component: Layout,
    hidden: true,
    children: [{
      path: 'holiday',
      component: (resolve) => require(['@/views/vacation/holiday/index'], resolve),
      name: 'Vacation Management',
      meta: { title: 'Vacation Management'}
    },{
      path: 'approval',
      component: (resolve) => require(['@/views/vacation/approval/index'], resolve),
      name: 'Vacation approval',
      meta: { title: 'Vacation approval'}
    },{
      path: 'approvalSetting',
      component: (resolve) => require(['@/views/vacation/approval/setting'], resolve),
      name: 'Approval settings',
      meta: { title: 'Approval settings'}
    }]
  },
]

export default new Router({
  // mode: 'history', // To get rid ofurlIn the#
  mode: 'hash', // hashmodel
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
