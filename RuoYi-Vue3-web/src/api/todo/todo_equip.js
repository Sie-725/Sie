import request from '@/utils/request'

// 查询设备统计列表
export function listTodo_equip(query) {
  return request({
    url: '/todo/todo_equip/list',
    method: 'get',
    params: query
  })
}

// 查询设备统计详细
export function getTodo_equip(id) {
  return request({
    url: '/todo/todo_equip/' + id,
    method: 'get'
  })
}

// 新增设备统计
export function addTodo_equip(data) {
  return request({
    url: '/todo/todo_equip',
    method: 'post',
    data: data
  })
}

// 修改设备统计
export function updateTodo_equip(data) {
  return request({
    url: '/todo/todo_equip',
    method: 'put',
    data: data
  })
}

// 删除设备统计
export function delTodo_equip(id) {
  return request({
    url: '/todo/todo_equip/' + id,
    method: 'delete'
  })
}
