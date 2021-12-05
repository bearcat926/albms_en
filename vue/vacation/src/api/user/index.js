import request from '@/utils/request'

// Query the generated table data
export function getAllSubsidiaryCompany() {
    return request({
        url: '/system/dept/getAllSubsidiaryCompany',
        method: 'get',
    })
}
// Subsidiary list data
export function subsidiaryCompanyList(data) {
    console.log(data)
    return request({
        url: '/system/dept/subsidiaryCompanyList',
        method: 'get',
        params: data
    })
}
// Export subsidiary list data
export function subsidiaryCompanyListExport(data) {
    console.log(data)
    return request({
        url: '/system/dept/subsidiaryCompanyListExport',
        method: 'get',
        params: data
    })
}

// Download the subsidiary import template
export function importTemplate() {
    return request({
        url: '/system/dept/importTemplate',
        method: 'get',
    })
}

// Subsidiary Details
export function subsidiaryCompanyInfo(deptId) {
    return request({
        url: '/system/dept/subsidiaryCompanyInfo',
        method: 'get',
        params: { deptId }
    })
}
// Querying User Details
export function userInfo(userId) {
    return request({
        url: '/system/user/userInfo',
        method: 'get',
        params: { userId }
    })
}


// According to the subsidiaryidQuery store list
export function getShop(ids) {
    return request({
        url: '/commonApi/getShopListByCompanyIds?ids=' + ids,
        method: 'get'
    })
}




// Store interface
// Get store list
export function shopList(data) {
    return request({
        url: '/system/dept/shopList',
        method: 'get',
        params: data
    })
}

// Querying store information
export function shopQuery(deptId) {
    return request({
        url: '/system/dept/' + deptId,
        method: 'get',
    })
}

// Store Details
export function shopInfo(deptId) {
    return request({
        url: '/system/dept/shopInfo',
        method: 'get',
        params: { deptId }
    })
}
// The new store
export function addShop(data) {
    return request({
        url: '/system/dept',
        method: 'post',
        data: data
    })
}

// Modify the stores
export function updateShop(data) {
    return request({
        url: '/system/dept',
        method: 'put',
        data: data
    })
}
// Delete stores
export function delShop(id) {
    return request({
        url: '/system/dept/' + id,
        method: 'delete'
    })
}
// Download the store import template
export function importShopTemplate(data) {
    return request({
        url: '/system/dept/importShopTemplate',
        method: 'get',
        params: data
    })
}
// Store data Export
export function shopListExport(data) {
    console.log(data)
    return request({
        url: '/system/dept/shopListExport',
        method: 'get',
        params: data
    })
}


// Obtain the list of employees in the department
export function deptEmployeesList(deptId) {
    return request({
        url: '/system/dept/deptEmployeesList?deptId=' + deptId,
        method: 'get',
    })
}