import request from '@/utils/request'

// Example Query the parameter list
export function listParamsData(query) {
  return request({
    url: '/vacation/paramsData/list',
    method: 'get',
    params: query
  })
}

// Query parameter Settings in detail
export function getParamsData(id) {
  return request({
    url: '/vacation/paramsData/' + id,
    method: 'get'
  })
}

// Query parameter Settings in detail
export function getBackGroundPic(id) {
  return request({
    url: '/vacation/paramsData/backGroundPic/' + id,
    method: 'get'
  })
}

// New Parameter Settings
export function addParamsData(data) {
  return request({
    url: '/vacation/paramsData',
    method: 'post',
    data: data
  })
}

// Modifying Parameter Settings
export function updateParamsData(data) {
  return request({
    url: '/vacation/paramsData',
    method: 'put',
    data: data
  })
}

// Query parameter Settings in detail
export function updateBackGroundPic(data) {
  return request({
    url: '/vacation/paramsData/backGroundPic',
    method: 'put',
    data: data
  })
}

// Delete parameter Settings
export function delParamsData(id) {
  return request({
    url: '/vacation/paramsData/' + id,
    method: 'delete'
  })
}

// Export Parameter Settings
export function exportParamsData(query) {
  return request({
    url: '/vacation/paramsData/export',
    method: 'get',
    params: query
  })
}
