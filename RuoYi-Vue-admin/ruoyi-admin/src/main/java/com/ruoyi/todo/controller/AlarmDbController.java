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
import com.ruoyi.todo.domain.AlarmDb;
import com.ruoyi.todo.service.IAlarmDbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 故障统计Controller
 * 
 * @author ruoyi
 * @date 2024-12-25
 */
@RestController
@RequestMapping("/todo/todo_alarm")
public class AlarmDbController extends BaseController
{
    @Autowired
    private IAlarmDbService alarmDbService;

    /**
     * 查询故障统计列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_alarm:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlarmDb alarmDb)
    {
        startPage();
        List<AlarmDb> list = alarmDbService.selectAlarmDbList(alarmDb);
        return getDataTable(list);
    }

    /**
     * 导出故障统计列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_alarm:export')")
    @Log(title = "故障统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlarmDb alarmDb)
    {
        List<AlarmDb> list = alarmDbService.selectAlarmDbList(alarmDb);
        ExcelUtil<AlarmDb> util = new ExcelUtil<AlarmDb>(AlarmDb.class);
        util.exportExcel(response, list, "故障统计数据");
    }

    /**
     * 获取故障统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_alarm:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alarmDbService.selectAlarmDbById(id));
    }

    /**
     * 新增故障统计
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_alarm:add')")
    @Log(title = "故障统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlarmDb alarmDb)
    {
        return toAjax(alarmDbService.insertAlarmDb(alarmDb));
    }

    /**
     * 修改故障统计
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_alarm:edit')")
    @Log(title = "故障统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlarmDb alarmDb)
    {
        return toAjax(alarmDbService.updateAlarmDb(alarmDb));
    }

    /**
     * 删除故障统计
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_alarm:remove')")
    @Log(title = "故障统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alarmDbService.deleteAlarmDbByIds(ids));
    }
}
