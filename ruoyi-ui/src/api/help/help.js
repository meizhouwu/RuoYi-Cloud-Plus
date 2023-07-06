import request from '@/utils/request'

// 查询帮扶管理列表
export function listHelp(query) {
  return request({
    url: '/help/help/list',
    method: 'get',
    params: query
  })
}

// 查询帮扶管理详细
export function getHelp(id) {
  return request({
    url: '/help/help/' + id,
    method: 'get'
  })
}

// 新增帮扶管理
export function addHelp(data) {
  return request({
    url: '/help/help',
    method: 'post',
    data: data
  })
}

// 修改帮扶管理
export function updateHelp(data) {
  return request({
    url: '/help/help',
    method: 'put',
    data: data
  })
}

// 删除帮扶管理
export function delHelp(id) {
  return request({
    url: '/help/help/' + id,
    method: 'delete'
  })
}
