import request from '@/utils/request'

// 查询机房健康列表
export function listTodo_roomhealth(query) {
  return request({
    url: '/todo/todo_roomhealth/list',
    method: 'get',
    params: query
  })
}

// 查询机房健康详细
export function getTodo_roomhealth(name) {
  return request({
    url: '/todo/todo_roomhealth/' + name,
    method: 'get'
  })
}

// 新增机房健康
export function addTodo_roomhealth(data) {
  return request({
    url: '/todo/todo_roomhealth',
    method: 'post',
    data: data
  })
}

// 修改机房健康
export function updateTodo_roomhealth(data) {
  return request({
    url: '/todo/todo_roomhealth',
    method: 'put',
    data: data
  })
}

// 删除机房健康
export function delTodo_roomhealth(name) {
  return request({
    url: '/todo/todo_roomhealth/' + name,
    method: 'delete'
  })
}
