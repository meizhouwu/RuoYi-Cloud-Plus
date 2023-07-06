import request from '@/utils/request'

// 查询老师管理列表
export function listTeacher(query) {
  return request({
    url: '/teacher/teacher/list',
    method: 'get',
    params: query
  })
}

// 查询老师管理详细
export function getTeacher(id) {
  return request({
    url: '/teacher/teacher/' + id,
    method: 'get'
  })
}

// 新增老师管理
export function addTeacher(data) {
  return request({
    url: '/teacher/teacher',
    method: 'post',
    data: data
  })
}

// 修改老师管理
export function updateTeacher(data) {
  return request({
    url: '/teacher/teacher',
    method: 'put',
    data: data
  })
}

// 删除老师管理
export function delTeacher(id) {
  return request({
    url: '/teacher/teacher/' + id,
    method: 'delete'
  })
}

// 查询所有班主任
export function teacherList() {
  return request({
    url: '/teacher/teacher/teacherList',
    method: 'get',
  })
}

// 查询所有就业老师
export function empTeacherList() {
  return request({
    url: '/teacher/teacher/empTeacherList',
    method: 'get',
  })
}

// 查询所有助教老师
export function helpTeacherList() {
  return request({
    url: '/teacher/teacher/helpTeacherList',
    method: 'get',
  })
}

// 查询所有讲师
export function talkTeacherList() {
  return request({
    url: '/teacher/teacher/talkTeacherList',
    method: 'get',
  })
}

// 查询所有技术老师
export function techTeacherList() {
  return request({
    url: '/teacher/teacher/techTeacherList',
    method: 'get',
  })
}



