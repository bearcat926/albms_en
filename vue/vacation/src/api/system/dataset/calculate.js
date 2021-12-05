import request from '@/utils/request'

// Query the list of performance assessment calculation Settings
export function listCalculate(query) {
    return request({
        url: '/glasses/calculate/list',
        method: 'get',
        params: query
    })
}

// Query performance assessment calculation set details
export function getCalculate(id) {
    return request({
        url: '/glasses/calculate/' + id,
        method: 'get'
    })
}

// New performance assessment calculation Settings
export function addCalculate(data) {
    return request({
        url: '/glasses/calculate',
        method: 'post',
        data: data
    })
}

// Modify performance assessment calculation Settings
export function updateCalculate(data) {
    return request({
        url: '/glasses/calculate',
        method: 'put',
        data: data
    })
}

// Delete performance appraisal calculation Settings
export function delCalculate(id) {
    return request({
        url: '/glasses/calculate/' + id,
        method: 'delete'
    })
}

// Export performance appraisal calculation Settings
export function exportCalculate(query) {
    return request({
        url: '/glasses/calculate/export',
        method: 'get',
        params: query
    })
}
