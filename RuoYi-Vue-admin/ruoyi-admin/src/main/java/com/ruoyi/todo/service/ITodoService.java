package com.ruoyi.todo.service;

import java.util.List;
import com.ruoyi.todo.domain.Todo;

/**
 * 地址统计Service接口
 * 
 * @author ruoyi
 * @date 2024-10-17
 */
public interface ITodoService 
{
    /**
     * 查询地址统计
     * 
     * @param id 地址统计主键
     * @return 地址统计
     */
    public Todo selectTodoById(Long id);

    /**
     * 查询地址统计列表
     * 
     * @param todo 地址统计
     * @return 地址统计集合
     */
    public List<Todo> selectTodoList(Todo todo);

    /**
     * 新增地址统计
     * 
     * @param todo 地址统计
     * @return 结果
     */
    public int insertTodo(Todo todo);

    /**
     * 修改地址统计
     * 
     * @param todo 地址统计
     * @return 结果
     */
    public int updateTodo(Todo todo);

    /**
     * 批量删除地址统计
     * 
     * @param ids 需要删除的地址统计主键集合
     * @return 结果
     */
    public int deleteTodoByIds(Long[] ids);

    /**
     * 删除地址统计信息
     * 
     * @param id 地址统计主键
     * @return 结果
     */
    public int deleteTodoById(Long id);
}
