import request from '@/utils/request'


// Home page above statistics
export function getTopData() {
    return request({
        url: '/index/topData',
        method: 'get'
    })
}


// Sales analysis
export function salePriceAnalyse() {
    return request({
        url: '/index/salePriceAnalyse',
        method: 'get'
    })
}


// Sales record analysis
export function saleNumAnalyse() {
    return request({
        url: '/index/saleNumAnalyse',
        method: 'get'
    })
}


// Member analysis
export function clientMemberLevelAnalyse() {
    return request({
        url: '/index/clientMemberLevelAnalyse',
        method: 'get'
    })
}

//After-sale orders to be reviewed
export function afterSalesOrderToBeReviewed() {
    return request({
        url: '/index/afterSalesOrderToBeReviewed',
        method: 'get'
    })
}

//Changes in credits to be reviewed
export function afterIntegralOrderToBeReviewed() {
    return request({
        url: '/index/afterIntegralOrderToBeReviewed',
        method: 'get'
    })
}


//Inventory transfer to be audited
export function afterAllotOrderToBeReviewed() {
    return request({
        url: '/index/afterAllotOrderToBeReviewed',
        method: 'get'
    })
}

//Pending matters
export function pendingMatters() {
    return request({
        url: '/index/pendingMatters',
        method: 'get'
    })
}

//Pending matters
export function indexPopUpWindows() {
    return request({
        url: '/index/indexPopUpWindows',
        method: 'get'
    })
}

//Gets the number of unread entries based on the condition
export function getNoReadNum() {
    return request({
        url: '/glasses/messages/getNoReadNum',
        method: 'get'
    })
}