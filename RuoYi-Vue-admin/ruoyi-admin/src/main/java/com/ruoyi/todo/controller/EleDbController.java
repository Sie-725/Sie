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
import com.ruoyi.todo.domain.EleDb;
import com.ruoyi.todo.service.IEleDbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 电池测试Controller
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/todo/todo_ele")
public class EleDbController extends BaseController
{
    @Autowired
    private IEleDbService eleDbService;

    /**
     * 查询电池测试列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_ele:list')")
    @GetMapping("/list")
    public TableDataInfo list(EleDb eleDb)
    {
        startPage();
        List<EleDb> list = eleDbService.selectEleDbList(eleDb);
        return getDataTable(list);
    }

    /**
     * 导出电池测试列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_ele:export')")
    @Log(title = "电池测试", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EleDb eleDb)
    {
        List<EleDb> list = eleDbService.selectEleDbList(eleDb);
        ExcelUtil<EleDb> util = new ExcelUtil<EleDb>(EleDb.class);
        util.exportExcel(response, list, "电池测试数据");
    }

    /**
     * 获取电池测试详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_ele:query')")
    @GetMapping(value = "/{eleNum}")
    public AjaxResult getInfo(@PathVariable("eleNum") String eleNum)
    {
        return success(eleDbService.selectEleDbByEleNum(eleNum));
    }

    /**
     * 新增电池测试
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_ele:add')")
    @Log(title = "电池测试", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EleDb eleDb)
    {
        return toAjax(eleDbService.insertEleDb(eleDb));
    }

    /**
     * 修改电池测试
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_ele:edit')")
    @Log(title = "电池测试", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EleDb eleDb)
    {
        return toAjax(eleDbService.updateEleDb(eleDb));
    }

    /**
     * 删除电池测试
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_ele:remove')")
    @Log(title = "电池测试", businessType = BusinessType.DELETE)
	@DeleteMapping("/{eleNums}")
    public AjaxResult remove(@PathVariable String[] eleNums)
    {
        return toAjax(eleDbService.deleteEleDbByEleNums(eleNums));
    }
}
