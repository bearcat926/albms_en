import request from '@/utils/request'

// Query bulletin View the record list
export function listNoticeRecord(query) {
    return request({
        url: '/glasses/noticeRecord/list',
        method: 'get',
        params: query
    })
}

// Query bulletins To view details
export function getNoticeRecord(id) {
    return request({
        url: '/glasses/noticeRecord/' + id,
        method: 'get'
    })
}

// Added bulletin viewing records
export function addNoticeRecord(data) {
    return request({
        url: '/glasses/noticeRecord',
        method: 'post',
        data: data
    })
}

// Modify bulletin viewing records
export function updateNoticeRecord(data) {
    return request({
        url: '/glasses/noticeRecord',
        method: 'put',
        data: data
    })
}

// Delete bulletin viewing records
export function delNoticeRecord(id) {
    return request({
        url: '/glasses/noticeRecord/' + id,
        method: 'delete'
    })
}

// Export bulletin viewing records
export function exportNoticeRecord(query) {
    return request({
        url: '/glasses/noticeRecord/export',
        method: 'get',
        params: query
    })
}