import request from '@/utils/request'



//Gets the number of unread entries based on the condition
export function getNoReadNumStatus(status) {
    return request({
        url: '/glasses/messages/getNoReadNum?status=' + status,
        method: 'get'
    })
}

// Querying message list
export function messagesList(status) {
    return request({
        url: '/glasses/messages/list',
        method: 'get'
    })
}


// Querying message list
export function clearMessages(ids) {
    return request({
        url: '/glasses/messages/' + ids,
        method: 'put'
    })
}