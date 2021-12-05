import request from '@/utils/request'

// Query the frame parameter list
export function listMirrorBracketParams(query) {
    return request({
        url: '/glasses/mirrorBracketParams/list',
        method: 'get',
        params: query
    })
}

// Query details of frame parameters
export function getMirrorBracketParams(type) {
    return request({
        url: '/glasses/mirrorBracketParams/getListByType?type=' + type,
        method: 'get'
    })
}

// Added frame parameters
export function addMirrorBracketParams(data) {
    return request({
        url: '/glasses/mirrorBracketParams',
        method: 'post',
        data: data
    })
}

// Modify frame parameters
export function updateMirrorBracketParams(data) {
    return request({
        url: '/glasses/mirrorBracketParams',
        method: 'put',
        data: data
    })
}

// Delete frame parameters
export function delMirrorBracketParams(id) {
    return request({
        url: '/glasses/mirrorBracketParams/' + id,
        method: 'delete'
    })
}

// Export frame parameters
export function exportMirrorBracketParams(query) {
    return request({
        url: '/glasses/mirrorBracketParams/export',
        method: 'get',
        params: query
    })
}