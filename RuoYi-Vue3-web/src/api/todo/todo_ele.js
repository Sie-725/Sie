import request from '@/utils/request'

// 查询电池测试列表
export function listTodo_ele(query) {
  return request({
    url: '/todo/todo_ele/list',
    method: 'get',
    params: query
  })
}

// 查询电池测试详细
export function getTodo_ele(id) {
  return request({
    url: '/todo/todo_ele/' + id,
    method: 'get'
  })
}

// 新增电池测试
export function addTodo_ele(data) {
  return request({
    url: '/todo/todo_ele',
    method: 'post',
    data: data
  })
}

// 修改电池测试
export function updateTodo_ele(data) {
  return request({
    url: '/todo/todo_ele',
    method: 'put',
    data: data
  })
}

// 删除电池测试
export function delTodo_ele(id) {
  return request({
    url: '/todo/todo_ele/' + id,
    method: 'delete'
  })
}
