import request from '@/utils/request'

// Leave Approval List
export function holidayApprovalList(data) {
  return request({
    url: '/vacation/holiday/approval/list',
    method: 'get',
    params: data
  })
}

// The role list
export function roleList() {
  return request({
    url: '/vacation/holiday/approval/roleList',
    method: 'get'
  })
}

// Leave approval details
export function getHolidayApprovalInfo(id) {
  return request({
    url: '/vacation/holiday/approval/' + id,
    method: 'get'
  })
}

// Approval of new leave
export function holidayApprovalAdd(data) {
  return request({
    url: '/vacation/holiday/approval',
    method: 'post',
    data: data
  })
}

// Modify leave approval
export function holidayApprovalUpdate(data) {
  return request({
    url: '/vacation/holiday/approval',
    method: 'put',
    data: data
  })
}

// Delete leave approval
export function holidayApprovalRemove(id) {
  return request({
    url: '/vacation/holiday/approval/' + id,
    method: 'delete'
  })
}
