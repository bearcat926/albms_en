import router from './router'
import store from './store'
import {
    Message
} from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {
    getToken
} from '@/utils/auth'

NProgress.configure({
    showSpinner: false
})

const whiteList = ['/login', '/auth-redirect', '/bind', '/register', '/password']

router.beforeEach((to, from, next) => {
    NProgress.start()

    if (getToken()) {
        /* has token*/
        if (to.path === '/login') {
            next({
                path: '/'
            })
            NProgress.done()
        } else {
            if (store.getters.roles.length === 0) {
                // Check whether the current user has finished pullinguser_infoinformation
                store.dispatch('GetInfo').then(res => {
                        // pulluser_info
                        const roles = res.roles
                        store.dispatch('GenerateRoutes', {
                            roles
                        }).then(accessRoutes => {
                            // test Default static page
                            // store.dispatch('permission/generateRoutes', { roles }).then(accessRoutes => {
                            // According to therolesPermissions generate an accessible routing table
                            router.addRoutes(accessRoutes) // Dynamically add an accessible routing table
                            next({
                                    ...to,
                                    replace: true
                                }) // hackmethods Make sure thataddRoutesHas been completed
                        })
                    })
                    .catch(err => {
                        store.dispatch('FedLogOut').then(() => {
                            Message.error(err)
                            next({
                                path: '/'
                            })
                        })
                    })
            } else {
                next()
                    // There is no need to dynamically change permissions directlynext() Delete the lower permission judgment ↓
                    // if (hasPermission(store.getters.roles, to.meta.roles)) {
                    //   next()
                    // } else {
                    //   next({ path: '/401', replace: true, query: { noGoBack: true }})
                    // }
                    // Can delete ↑
            }
        }
    } else {
        // There is notoken
        if (whiteList.indexOf(to.path) !== -1) {
            // On the sign-on free whitelist,Direct access to
            next()
        } else {
            next(`/login?redirect=${to.fullPath}`) // Otherwise, all are redirected to the login page
            NProgress.done()
        }
    }
})

router.afterEach(() => {
    NProgress.done()
})