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
import com.ruoyi.todo.domain.HealthallDb;
import com.ruoyi.todo.service.IHealthallDbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机房健康Controller
 * 
 * @author ruoyi
 * @date 2024-12-10
 */
@RestController
@RequestMapping("/todo/todo_roomhealth")
public class HealthallDbController extends BaseController
{
    @Autowired
    private IHealthallDbService healthallDbService;

    /**
     * 查询机房健康列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_roomhealth:list')")
    @GetMapping("/list")
    public TableDataInfo list(HealthallDb healthallDb)
    {
        startPage();
        List<HealthallDb> list = healthallDbService.selectHealthallDbList(healthallDb);
        return getDataTable(list);
    }

    /**
     * 导出机房健康列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_roomhealth:export')")
    @Log(title = "机房健康", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HealthallDb healthallDb)
    {
        List<HealthallDb> list = healthallDbService.selectHealthallDbList(healthallDb);
        ExcelUtil<HealthallDb> util = new ExcelUtil<HealthallDb>(HealthallDb.class);
        util.exportExcel(response, list, "机房健康数据");
    }

    /**
     * 获取机房健康详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_roomhealth:query')")
    @GetMapping(value = "/{name}")
    public AjaxResult getInfo(@PathVariable("name") String name)
    {
        return success(healthallDbService.selectHealthallDbByName(name));
    }

    /**
     * 新增机房健康
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_roomhealth:add')")
    @Log(title = "机房健康", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HealthallDb healthallDb)
    {
        return toAjax(healthallDbService.insertHealthallDb(healthallDb));
    }

    /**
     * 修改机房健康
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_roomhealth:edit')")
    @Log(title = "机房健康", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HealthallDb healthallDb)
    {
        return toAjax(healthallDbService.updateHealthallDb(healthallDb));
    }

    /**
     * 删除机房健康
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_roomhealth:remove')")
    @Log(title = "机房健康", businessType = BusinessType.DELETE)
	@DeleteMapping("/{names}")
    public AjaxResult remove(@PathVariable String[] names)
    {
        return toAjax(healthallDbService.deleteHealthallDbByNames(names));
    }
}
