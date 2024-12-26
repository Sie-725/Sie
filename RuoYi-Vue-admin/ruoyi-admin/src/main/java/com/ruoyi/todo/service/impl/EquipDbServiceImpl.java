package com.ruoyi.todo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.todo.mapper.EquipDbMapper;
import com.ruoyi.todo.domain.EquipDb;
import com.ruoyi.todo.service.IEquipDbService;

/**
 * 设备统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-12-25
 */
@Service
public class EquipDbServiceImpl implements IEquipDbService 
{
    @Autowired
    private EquipDbMapper equipDbMapper;

    /**
     * 查询设备统计
     * 
     * @param id 设备统计主键
     * @return 设备统计
     */
    @Override
    public EquipDb selectEquipDbById(Long id)
    {
        return equipDbMapper.selectEquipDbById(id);
    }

    /**
     * 查询设备统计列表
     * 
     * @param equipDb 设备统计
     * @return 设备统计
     */
    @Override
    public List<EquipDb> selectEquipDbList(EquipDb equipDb)
    {
        return equipDbMapper.selectEquipDbList(equipDb);
    }

    /**
     * 新增设备统计
     * 
     * @param equipDb 设备统计
     * @return 结果
     */
    @Override
    public int insertEquipDb(EquipDb equipDb)
    {
        return equipDbMapper.insertEquipDb(equipDb);
    }

    /**
     * 修改设备统计
     * 
     * @param equipDb 设备统计
     * @return 结果
     */
    @Override
    public int updateEquipDb(EquipDb equipDb)
    {
        return equipDbMapper.updateEquipDb(equipDb);
    }

    /**
     * 批量删除设备统计
     * 
     * @param ids 需要删除的设备统计主键
     * @return 结果
     */
    @Override
    public int deleteEquipDbByIds(Long[] ids)
    {
        return equipDbMapper.deleteEquipDbByIds(ids);
    }

    /**
     * 删除设备统计信息
     * 
     * @param id 设备统计主键
     * @return 结果
     */
    @Override
    public int deleteEquipDbById(Long id)
    {
        return equipDbMapper.deleteEquipDbById(id);
    }
}
