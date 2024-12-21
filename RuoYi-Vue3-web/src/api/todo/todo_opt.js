import request from '@/utils/request'

// 查询光缆测试列表
export function listTodo_opt(query) {
  return request({
    url: '/todo/todo_opt/list',
    method: 'get',
    params: query
  })
}

// 查询光缆测试详细
export function getTodo_opt(optName) {
  return request({
    url: '/todo/todo_opt/' + optName,
    method: 'get'
  })
}

// 新增光缆测试
export function addTodo_opt(data) {
  return request({
    url: '/todo/todo_opt',
    method: 'post',
    data: data
  })
}

// 修改光缆测试
export function updateTodo_opt(data) {
  return request({
    url: '/todo/todo_opt',
    method: 'put',
    data: data
  })
}

// 删除光缆测试
export function delTodo_opt(optName) {
  return request({
    url: '/todo/todo_opt/' + optName,
    method: 'delete'
  })
}
