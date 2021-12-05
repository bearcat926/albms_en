import axios from 'axios'
import {
    Notification,
    MessageBox,
    Message
} from 'element-ui'
import store from '@/store'
import {
    getToken
} from '@/utils/auth'
import errorCode from '@/utils/errorCode'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
    // createaxiosThe instance
const service = axios.create({
        // axiosThe request is configured inbaseURLoptions,Said the requestURLPublic sector
        baseURL: process.env.VUE_APP_BASE_API,
        // baseURL: 'http://47.101.183.57:8080',
        // timeout
        timeout: 60000
    })
    // requestThe interceptor
service.interceptors.request.use(config => {
    // Do I need to set it? token
    const isToken = (config.headers || {}).isToken === false
    if (getToken() && !isToken) {
        config.headers['Authorization'] = 'Bearer ' + getToken() // Let each request carry a customtoken Modify the parameters based on the actual situation
    }
    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// Response interceptor
service.interceptors.response.use(res => {
        // If no status code is set, the status is successful by default
        const code = res.data.code || 200;
        // Get error information
        const message = errorCode[code] || res.data.msg || errorCode['default']
        if (code === 401) {
            MessageBox.confirm(
                'The login status has expired,You can stay on the page,Or log in again',
                'The system prompt', {
                    confirmButtonText: 'Log back in',
                    cancelButtonText: 'cancel',
                    type: 'warning'
                }
            ).then(() => {
                store.dispatch('LogOut').then(() => {
                    location.reload() // For re-instantiationvue-routerobject avoidbug
                })
            })
        } else if (code === 500) {
            Message({
                message: message,
                // message: 'Service exceptions',
                type: 'error'
            })
            return Promise.reject(new Error(message))
                // return Promise.reject('Service exceptions')
        } else if (code !== 200) {
            Notification.error({
                title: message
            })
            return Promise.reject('error')
        } else {
            return res.data
        }
    },
    error => {
        console.log('err' + error)
        Message({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service
