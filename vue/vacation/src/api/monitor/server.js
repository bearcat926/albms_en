import request from '@/utils/request'

// Querying Server Details
export function getServer() {
  return request({
    url: '/monitor/server',
    method: 'get'
  })
}