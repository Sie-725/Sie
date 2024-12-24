import request from '@/utils/request'

// 查询故障统计列表
export function listTodo_alarm(query) {
  return request({
    url: '/todo/todo_alarm/list',
    method: 'get',
    params: query
  })
}

// 查询故障统计详细
export function getTodo_alarm(id) {
  return request({
    url: '/todo/todo_alarm/' + id,
    method: 'get'
  })
}

// 新增故障统计
export function addTodo_alarm(data) {
  return request({
    url: '/todo/todo_alarm',
    method: 'post',
    data: data
  })
}

// 修改故障统计
export function updateTodo_alarm(data) {
  return request({
    url: '/todo/todo_alarm',
    method: 'put',
    data: data
  })
}

// 删除故障统计
export function delTodo_alarm(id) {
  return request({
    url: '/todo/todo_alarm/' + id,
    method: 'delete'
  })
}
