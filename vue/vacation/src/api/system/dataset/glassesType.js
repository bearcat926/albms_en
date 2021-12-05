import request from '@/utils/request'

// Query the frame model list
export function listType(query) {
    return request({
        url: '/glasses/type/list',
        method: 'get',
        params: query
    })
}

// Query the frame model list
export function getListByType(query) {
    return request({
        url: '/glasses/mirrorBracketParams/getListByType?type=' + query,
        method: 'get',
    })
}

// Query mirror frame model details
export function getType(id) {
    return request({
        url: '/glasses/type/' + id,
        method: 'get'
    })
}

// New frame model
export function addType(data) {
    return request({
        url: '/glasses/type',
        method: 'post',
        data: data
    })
}

// Modify the frame model
export function updateType(data) {
    return request({
        url: '/glasses/type',
        method: 'put',
        data: data
    })
}

// Delete the mirror frame model
export function delType(id) {
    return request({
        url: '/glasses/type/' + id,
        method: 'delete'
    })
}

// Export the frame model
export function exportType(query) {
    return request({
        url: '/glasses/type/export',
        method: 'get',
        params: query
    })
}