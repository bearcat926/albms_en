import request from '@/utils/request'

// To get the routing
export const getRouters = () => {
  return request({
    url: '/getRouters',
    method: 'get'
  })
}