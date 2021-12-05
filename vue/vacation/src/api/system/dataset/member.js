import request from '@/utils/request'

// Query the membership level setting list
export function listMemberLevel(query) {
    return request({
        url: '/glasses/memberLevel/list',
        method: 'get',
        params: query
    })
}

// Query membership level setting details
export function getMemberLevel(id) {
    return request({
        url: '/glasses/memberLevel/' + id,
        method: 'get'
    })
}

// New member level setting
export function addMemberLevel(data) {
    return request({
        url: '/glasses/memberLevel',
        method: 'post',
        data: data
    })
}
// Modify membership level Settings
export function updateMemberLevel(data) {
    return request({
        url: '/glasses/memberLevel',
        method: 'put',
        data: data
    })
}

// Delete membership level Settings
export function delMemberLevel(id) {
    return request({
        url: '/glasses/memberLevel/' + id,
        method: 'delete'
    })
}

// Export membership level Settings
export function exportMemberLevel(query) {
    return request({
        url: '/glasses/memberLevel/export',
        method: 'get',
        params: query
    })
}


// Generate qr code
export function getQrCode(query) {
    return request({
        url: '/vacation/paramsData/getQrCode',
        method: 'get',
        params: query
    })
}
