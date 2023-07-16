import request from '@/utils/request'

// 查询帮扶报表中心列表
export function listHelpReport(query) {
  return request({
    url: '/help/helpReport/list',
    method: 'get',
    params: query
  })
}

// 查询帮扶报表中心详细
export function getHelpReport(id) {
  return request({
    url: '/help/helpReport/' + id,
    method: 'get'
  })
}

// 新增帮扶报表中心
export function addHelpReport(data) {
  return request({
    url: '/help/helpReport',
    method: 'post',
    data: data
  })
}

// 修改帮扶报表中心
export function updateHelpReport(data) {
  return request({
    url: '/help/helpReport',
    method: 'put',
    data: data
  })
}

// 删除帮扶报表中心
export function delHelpReport(id) {
  return request({
    url: '/help/helpReport/' + id,
    method: 'delete'
  })
}
