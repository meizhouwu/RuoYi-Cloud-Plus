import request from '@/utils/request'

// 查询帮扶学生评价列表
export function listHelpAppraise(query) {
  return request({
    url: '/help/helpAppraise/list',
    method: 'get',
    params: query
  })
}

// 查询帮扶学生评价详细
export function getHelpAppraise(id) {
  return request({
    url: '/help/helpAppraise/' + id,
    method: 'get'
  })
}

// 新增帮扶学生评价
export function addHelpAppraise(data) {
  return request({
    url: '/help/helpAppraise',
    method: 'post',
    data: data
  })
}

// 修改帮扶学生评价
export function updateHelpAppraise(data) {
  return request({
    url: '/help/helpAppraise',
    method: 'put',
    data: data
  })
}

// 删除帮扶学生评价
export function delHelpAppraise(id) {
  return request({
    url: '/help/helpAppraise/' + id,
    method: 'delete'
  })
}
