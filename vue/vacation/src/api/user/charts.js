import request from '@/utils/request'

// User data analysis
export function userTopData() {
    return request({
        url: '/system/user/topData',
        method: 'get',
    })
}