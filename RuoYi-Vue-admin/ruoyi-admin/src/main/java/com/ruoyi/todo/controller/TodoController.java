package com.ruoyi.todo.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.todo.domain.Todo;
import com.ruoyi.todo.service.ITodoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 地址统计Controller
 * 
 * @author ruoyi
 * @date 2024-10-17
 */
@RestController
@RequestMapping("/todo/todo")
public class TodoController extends BaseController
{
    @Autowired
    private ITodoService todoService;

    /**
     * 查询地址统计列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Todo todo)
    {
        startPage();
        List<Todo> list = todoService.selectTodoList(todo);
        return getDataTable(list);
    }

    /**
     * 导出地址统计列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:export')")
    @Log(title = "地址统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Todo todo)
    {
        List<Todo> list = todoService.selectTodoList(todo);
        ExcelUtil<Todo> util = new ExcelUtil<Todo>(Todo.class);
        util.exportExcel(response, list, "地址统计数据");
    }

    /**
     * 获取地址统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(todoService.selectTodoById(id));
    }

    /**
     * 新增地址统计
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:add')")
    @Log(title = "地址统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Todo todo)
    {
        return toAjax(todoService.insertTodo(todo));
    }

    /**
     * 修改地址统计
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:edit')")
    @Log(title = "地址统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Todo todo)
    {
        return toAjax(todoService.updateTodo(todo));
    }

    /**
     * 删除地址统计
     */
    @PreAuthorize("@ss.hasPermi('todo:todo:remove')")
    @Log(title = "地址统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(todoService.deleteTodoByIds(ids));
    }
}
