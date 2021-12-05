import request from '@/utils/request'

// Querying the Bulletin List
export function listNotice(query) {
    return request({
        url: '/system/notice/list',
        method: 'get',
        params: query
    })
}
// Querying the Bulletin List
export function listNotice1() {
    return request({
        url: '/system/notice/listIndex?status=0',
        method: 'get',
    })
}

// Query Announcement Details
export function getNotice(noticeId) {
    return request({
        url: '/system/notice/' + noticeId,
        method: 'get'
    })
}

// The new announcement
export function addNotice(data) {
    return request({
        url: '/system/notice',
        method: 'post',
        data: data
    })
}

// Modify bulletin
export function updateNotice(data) {
    return request({
        url: '/system/notice',
        method: 'put',
        data: data
    })
}

// Delete the announcement
export function delNotice(noticeId) {
    return request({
        url: '/system/notice/' + noticeId,
        method: 'delete'
    })
}