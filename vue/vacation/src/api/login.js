import request from '@/utils/request'

// Login method
export function login(username, password, code, uuid) {
    const data = {
        username,
        password,
        code,
        uuid
    }
    return request({
        url: '/login',
        method: 'post',
        data: data
    })
}
//Get the department based on the login name

export function getUserName(user) {
    return request({
        url: '/getDeptNameByUserName?userName=' + user,
        method: 'get'
    })
}




// Get user details
export function getInfo() {
    return request({
        url: '/getInfo',
        method: 'get'
    })
}


//Obtain the login page picture and technical customer service number
export function getLoginInfo() {
    return request({
        url: '/getLoginBackGroundPicAndTechnicalPhone',
        method: 'get'
    })
}


// Exit the method
export function logout() {
    return request({
        url: '/logout',
        method: 'post'
    })
}

// Obtaining verification code
export function getCodeImg() {
    return request({
        url: '/captchaImage',
        method: 'get'
    })
}