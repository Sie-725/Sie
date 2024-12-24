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
import com.ruoyi.todo.domain.RoomDb;
import com.ruoyi.todo.service.IRoomDbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机房信息Controller
 * 
 * @author ruoyi
 * @date 2024-12-10
 */
@RestController
@RequestMapping("/todo/todo_room")
public class RoomDbController extends BaseController
{
    @Autowired
    private IRoomDbService roomDbService;

    /**
     * 查询机房信息列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_room:list')")
    @GetMapping("/list")
    public TableDataInfo list(RoomDb roomDb)
    {
        startPage();
        List<RoomDb> list = roomDbService.selectRoomDbList(roomDb);
        return getDataTable(list);
    }

    /**
     * 导出机房信息列表
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_room:export')")
    @Log(title = "机房信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RoomDb roomDb)
    {
        List<RoomDb> list = roomDbService.selectRoomDbList(roomDb);
        ExcelUtil<RoomDb> util = new ExcelUtil<RoomDb>(RoomDb.class);
        util.exportExcel(response, list, "机房信息数据");
    }

    /**
     * 获取机房信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_room:query')")
    @GetMapping(value = "/{name}")
    public AjaxResult getInfo(@PathVariable("name") String name)
    {
        return success(roomDbService.selectRoomDbByName(name));
    }

    /**
     * 新增机房信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_room:add')")
    @Log(title = "机房信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoomDb roomDb)
    {
        return toAjax(roomDbService.insertRoomDb(roomDb));
    }

    /**
     * 修改机房信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_room:edit')")
    @Log(title = "机房信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RoomDb roomDb)
    {
        return toAjax(roomDbService.updateRoomDb(roomDb));
    }

    /**
     * 删除机房信息
     */
    @PreAuthorize("@ss.hasPermi('todo:todo_room:remove')")
    @Log(title = "机房信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{names}")
    public AjaxResult remove(@PathVariable String[] names)
    {
        return toAjax(roomDbService.deleteRoomDbByNames(names));
    }
}
