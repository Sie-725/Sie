import request from '@/utils/request'

// 查询机房健康列表
export function listTodo_health(query) {
  return request({
    url: '/todo/todo_health/list',
    method: 'get',
    params: query
  })
}

// 查询机房健康详细
export function getTodo_health(date) {
  return request({
    url: '/todo/todo_health/' + date,
    method: 'get'
  })
}

// 新增机房健康
export function addTodo_health(data) {
  return request({
    url: '/todo/todo_health',
    method: 'post',
    data: data
  })
}

// 修改机房健康
export function updateTodo_health(data) {
  return request({
    url: '/todo/todo_health',
    method: 'put',
    data: data
  })
}

// 删除机房健康
export function delTodo_health(date) {
  return request({
    url: '/todo/todo_health/' + date,
    method: 'delete'
  })
}
