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
import com.ruoyi.todo.domain.EquipDb;
import com.ruoyi.todo.service.IEquipDbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备统计Controller
 * 
 * @author ruoyi
 * @date 2024-12-25
 */
@RestController
@RequestMapping("/todo/todo_equip")
public class EquipDbController extends BaseController
{
    @Autowired
    private IEquipDbService equipDbService;

    /**
     * 查询设备统计列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_equip:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipDb equipDb)
    {
        startPage();
        List<EquipDb> list = equipDbService.selectEquipDbList(equipDb);
        return getDataTable(list);
    }

    /**
     * 导出设备统计列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_equip:export')")
    @Log(title = "设备统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipDb equipDb)
    {
        List<EquipDb> list = equipDbService.selectEquipDbList(equipDb);
        ExcelUtil<EquipDb> util = new ExcelUtil<EquipDb>(EquipDb.class);
        util.exportExcel(response, list, "设备统计数据");
    }

    /**
     * 获取设备统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_equip:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipDbService.selectEquipDbById(id));
    }

    /**
     * 新增设备统计
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_equip:add')")
    @Log(title = "设备统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipDb equipDb)
    {
        return toAjax(equipDbService.insertEquipDb(equipDb));
    }

    /**
     * 修改设备统计
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_equip:edit')")
    @Log(title = "设备统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipDb equipDb)
    {
        return toAjax(equipDbService.updateEquipDb(equipDb));
    }

    /**
     * 删除设备统计
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_equip:remove')")
    @Log(title = "设备统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipDbService.deleteEquipDbByIds(ids));
    }
}
