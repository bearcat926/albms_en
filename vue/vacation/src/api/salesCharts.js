import request from '@/utils/request'


// Obtaining verification code
export function salesTopData(type) {
    return request({
        url: '/glasses/salesOrder/topData?type=' + type,
        method: 'get'
    })
}