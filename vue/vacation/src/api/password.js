import request from '@/utils/request'


// Obtaining verification code
export function getCodes(phone, dealType, templateType) {
    return request({
        url: '/sendSMS?phone=' + phone + '&dealType=' + dealType + '&templateType=' + templateType,
        method: 'post'
    })
}

// Forgot password
export function forgetPassword(data) {
    return request({
        url: '/forgetPassword',
        method: 'post',
        data: data
    })
}