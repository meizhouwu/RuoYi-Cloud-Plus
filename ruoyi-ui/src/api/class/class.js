import request from '@/utils/request'

// 查询班级管理列表
export function listClass(query) {
  return request({
    url: '/class/class/list',
    method: 'get',
    params: query
  })
}

// 查询班级管理详细
export function getClass(id) {
  return request({
    url: '/class/class/' + id,
    method: 'get'
  })
}

// 新增班级管理
export function addClass(data) {
  return request({
    url: '/class/class',
    method: 'post',
    data: data
  })
}

// 修改班级管理
export function updateClass(data) {
  return request({
    url: '/class/class',
    method: 'put',
    data: data
  })
}

// 删除班级管理
export function delClass(id) {
  return request({
    url: '/class/class/' + id,
    method: 'delete'
  })
}

// 查询班级列表
export function classList() {
  return request({
    url: '/class/class/classList',
    method: 'get',
  })
}
