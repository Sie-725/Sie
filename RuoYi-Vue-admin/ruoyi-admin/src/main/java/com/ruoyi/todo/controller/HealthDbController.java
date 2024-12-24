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
import com.ruoyi.todo.domain.HealthDb;
import com.ruoyi.todo.service.IHealthDbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机房健康Controller
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@RestController
@RequestMapping("/todo/todo_health")
public class HealthDbController extends BaseController
{
    @Autowired
    private IHealthDbService healthDbService;

    /**
     * 查询机房健康列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_health:list')")
    @GetMapping("/list")
    public TableDataInfo list(HealthDb healthDb)
    {
        startPage();
        List<HealthDb> list = healthDbService.selectHealthDbList(healthDb);
        return getDataTable(list);
    }

    /**
     * 导出机房健康列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_health:export')")
    @Log(title = "机房健康", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HealthDb healthDb)
    {
        List<HealthDb> list = healthDbService.selectHealthDbList(healthDb);
        ExcelUtil<HealthDb> util = new ExcelUtil<HealthDb>(HealthDb.class);
        util.exportExcel(response, list, "机房健康数据");
    }

    /**
     * 获取机房健康详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_health:query')")
    @GetMapping(value = "/{date}")
    public AjaxResult getInfo(@PathVariable("date") String date)
    {
        return success(healthDbService.selectHealthDbByDate(date));
    }

    /**
     * 新增机房健康
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_health:add')")
    @Log(title = "机房健康", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HealthDb healthDb)
    {
        return toAjax(healthDbService.insertHealthDb(healthDb));
    }

    /**
     * 修改机房健康
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_health:edit')")
    @Log(title = "机房健康", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HealthDb healthDb)
    {
        return toAjax(healthDbService.updateHealthDb(healthDb));
    }

    /**
     * 删除机房健康
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_health:remove')")
    @Log(title = "机房健康", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dates}")
    public AjaxResult remove(@PathVariable String[] dates)
    {
        return toAjax(healthDbService.deleteHealthDbByDates(dates));
    }
}
