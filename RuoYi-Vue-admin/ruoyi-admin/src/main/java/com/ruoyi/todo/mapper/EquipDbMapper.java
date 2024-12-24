package com.ruoyi.todo.mapper;

import java.util.List;
import com.ruoyi.todo.domain.EquipDb;

/**
 * 设备统计Mapper接口
 * 
 * @author ruoyi
 * @date 2024-12-25
 */
public interface EquipDbMapper 
{
    /**
     * 查询设备统计
     * 
     * @param id 设备统计主键
     * @return 设备统计
     */
    public EquipDb selectEquipDbById(Long id);

    /**
     * 查询设备统计列表
     * 
     * @param equipDb 设备统计
     * @return 设备统计集合
     */
    public List<EquipDb> selectEquipDbList(EquipDb equipDb);

    /**
     * 新增设备统计
     * 
     * @param equipDb 设备统计
     * @return 结果
     */
    public int insertEquipDb(EquipDb equipDb);

    /**
     * 修改设备统计
     * 
     * @param equipDb 设备统计
     * @return 结果
     */
    public int updateEquipDb(EquipDb equipDb);

    /**
     * 删除设备统计
     * 
     * @param id 设备统计主键
     * @return 结果
     */
    public int deleteEquipDbById(Long id);

    /**
     * 批量删除设备统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipDbByIds(Long[] ids);
}
