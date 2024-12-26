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
import com.ruoyi.todo.domain.OptDb;
import com.ruoyi.todo.service.IOptDbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 光缆测试Controller
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/todo/todo_opt")
public class OptDbController extends BaseController
{
    @Autowired
    private IOptDbService optDbService;

    /**
     * 查询光缆测试列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_opt:list')")
    @GetMapping("/list")
    public TableDataInfo list(OptDb optDb)
    {
        startPage();
        List<OptDb> list = optDbService.selectOptDbList(optDb);
        return getDataTable(list);
    }

    /**
     * 导出光缆测试列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_opt:export')")
    @Log(title = "光缆测试", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OptDb optDb)
    {
        List<OptDb> list = optDbService.selectOptDbList(optDb);
        ExcelUtil<OptDb> util = new ExcelUtil<OptDb>(OptDb.class);
        util.exportExcel(response, list, "光缆测试数据");
    }

    /**
     * 获取光缆测试详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_opt:query')")
    @GetMapping(value = "/{optName}")
    public AjaxResult getInfo(@PathVariable("optName") String optName)
    {
        return success(optDbService.selectOptDbByOptName(optName));
    }

    /**
     * 新增光缆测试
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_opt:add')")
    @Log(title = "光缆测试", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OptDb optDb)
    {
        return toAjax(optDbService.insertOptDb(optDb));
    }

    /**
     * 修改光缆测试
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_opt:edit')")
    @Log(title = "光缆测试", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OptDb optDb)
    {
        return toAjax(optDbService.updateOptDb(optDb));
    }

    /**
     * 删除光缆测试
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_opt:remove')")
    @Log(title = "光缆测试", businessType = BusinessType.DELETE)
	@DeleteMapping("/{optNames}")
    public AjaxResult remove(@PathVariable String[] optNames)
    {
        return toAjax(optDbService.deleteOptDbByOptNames(optNames));
    }
}
