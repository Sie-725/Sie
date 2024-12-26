package com.ruoyi.todo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.todo.mapper.TodoMapper;
import com.ruoyi.todo.domain.Todo;
import com.ruoyi.todo.service.ITodoService;

/**
 * 地址统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-10-17
 */
@Service
public class TodoServiceImpl implements ITodoService 
{
    @Autowired
    private TodoMapper todoMapper;

    /**
     * 查询地址统计
     * 
     * @param id 地址统计主键
     * @return 地址统计
     */
    @Override
    public Todo selectTodoById(Long id)
    {
        return todoMapper.selectTodoById(id);
    }

    /**
     * 查询地址统计列表
     * 
     * @param todo 地址统计
     * @return 地址统计
     */
    @Override
    public List<Todo> selectTodoList(Todo todo)
    {
        return todoMapper.selectTodoList(todo);
    }

    /**
     * 新增地址统计
     * 
     * @param todo 地址统计
     * @return 结果
     */
    @Override
    public int insertTodo(Todo todo)
    {
        return todoMapper.insertTodo(todo);
    }

    /**
     * 修改地址统计
     * 
     * @param todo 地址统计
     * @return 结果
     */
    @Override
    public int updateTodo(Todo todo)
    {
        return todoMapper.updateTodo(todo);
    }

    /**
     * 批量删除地址统计
     * 
     * @param ids 需要删除的地址统计主键
     * @return 结果
     */
    @Override
    public int deleteTodoByIds(Long[] ids)
    {
        return todoMapper.deleteTodoByIds(ids);
    }

    /**
     * 删除地址统计信息
     * 
     * @param id 地址统计主键
     * @return 结果
     */
    @Override
    public int deleteTodoById(Long id)
    {
        return todoMapper.deleteTodoById(id);
    }
}
