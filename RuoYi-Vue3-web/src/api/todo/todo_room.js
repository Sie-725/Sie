import request from '@/utils/request'

// 查询机房信息列表
export function listTodo_room(query) {
  return request({
    url: '/todo/todo_room/list',
    method: 'get',
    params: query
  })
}

// 查询机房信息详细
export function getTodo_room(name) {
  return request({
    url: '/todo/todo_room/' + name,
    method: 'get'
  })
}

// 新增机房信息
export function addTodo_room(data) {
  return request({
    url: '/todo/todo_room',
    method: 'post',
    data: data
  })
}

// 修改机房信息
export function updateTodo_room(data) {
  return request({
    url: '/todo/todo_room',
    method: 'put',
    data: data
  })
}

// 删除机房信息
export function delTodo_room(name) {
  return request({
    url: '/todo/todo_room/' + name,
    method: 'delete'
  })
}
