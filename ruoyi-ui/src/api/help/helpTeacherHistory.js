import request from '@/utils/request'

// 查询帮扶移交历史列表
export function listHelpTeacherHistory(query) {
  return request({
    url: '/help/helpTeacherHistory/list',
    method: 'get',
    params: query
  })
}

// 查询帮扶移交历史详细
export function getHelpTeacherHistory(id) {
  return request({
    url: '/help/helpTeacherHistory/' + id,
    method: 'get'
  })
}

// 新增帮扶移交历史
export function addHelpTeacherHistory(data) {
  return request({
    url: '/help/helpTeacherHistory',
    method: 'post',
    data: data
  })
}

// 修改帮扶移交历史
export function updateHelpTeacherHistory(data) {
  return request({
    url: '/help/helpTeacherHistory',
    method: 'put',
    data: data
  })
}

// 删除帮扶移交历史
export function delHelpTeacherHistory(id) {
  return request({
    url: '/help/helpTeacherHistory/' + id,
    method: 'delete'
  })
}
