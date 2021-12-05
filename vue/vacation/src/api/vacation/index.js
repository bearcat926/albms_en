import request from '@/utils/request'


// The holiday list
export function holidayList(data) {
  return request({
    url: '/vacation/holiday/list',
    method: 'get',
    params: data
  })
}

// Leave Approval List
export function holidayListNeedApproval(data) {
  return request({
    url: '/vacation/holiday/approvalList',
    method: 'get',
    params: data
  })
}

// Holiday Details
export function getHolidayInfo(id) {
    return request({
        url: '/vacation/holiday/' + id,
        method: 'get'
    })
}

// The new holiday
export function holidayAdd(data) {
    return request({
        url: '/vacation/holiday',
        method: 'post',
        data: data
    })
}

// Modify the holiday
export function holidayUpdate(data) {
    return request({
        url: '/vacation/holiday',
        method: 'put',
        data: data
    })
}

// Remove the holiday
export function holidayRemove(id) {
    return request({
        url: '/vacation/holiday/' + id,
        method: 'delete'
    })
}

export function userListForVacation(data) {
  return request({
    url: '/vacation/holiday/user/list',
    method: 'get',
    params: data
  })
}

export function hasNextApproved(id) {
  return request({
    url: '/vacation/holiday/next/' + id,
    method: 'get'
  })
}


