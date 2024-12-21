import request from '@/utils/request'

// 查询地址统计列表
export function listTodo(query) {
  return request({
    url: '/todo/todo/list',
    method: 'get',
    params: query
  })
}

// 查询地址统计详细
export function getTodo(id) {
  return request({
    url: '/todo/todo/' + id,
    method: 'get'
  })
}

// 新增地址统计
export function addTodo(data) {
  return request({
    url: '/todo/todo',
    method: 'post',
    data: data
  })
}

// 修改地址统计
export function updateTodo(data) {
  return request({
    url: '/todo/todo',
    method: 'put',
    data: data
  })
}

// 删除地址统计
export function delTodo(id) {
  return request({
    url: '/todo/todo/' + id,
    method: 'delete'
  })
}
