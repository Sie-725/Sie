package com.ruoyi.todo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.todo.mapper.HealthallDbMapper;
import com.ruoyi.todo.domain.HealthallDb;
import com.ruoyi.todo.service.IHealthallDbService;

/**
 * 机房健康Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-12-10
 */
@Service
public class HealthallDbServiceImpl implements IHealthallDbService 
{
    @Autowired
    private HealthallDbMapper healthallDbMapper;

    /**
     * 查询机房健康
     * 
     * @param name 机房健康主键
     * @return 机房健康
     */
    @Override
    public HealthallDb selectHealthallDbByName(String name)
    {
        return healthallDbMapper.selectHealthallDbByName(name);
    }

    /**
     * 查询机房健康列表
     * 
     * @param healthallDb 机房健康
     * @return 机房健康
     */
    @Override
    public List<HealthallDb> selectHealthallDbList(HealthallDb healthallDb)
    {
        return healthallDbMapper.selectHealthallDbList(healthallDb);
    }

    /**
     * 新增机房健康
     * 
     * @param healthallDb 机房健康
     * @return 结果
     */
    @Override
    public int insertHealthallDb(HealthallDb healthallDb)
    {
        return healthallDbMapper.insertHealthallDb(healthallDb);
    }

    /**
     * 修改机房健康
     * 
     * @param healthallDb 机房健康
     * @return 结果
     */
    @Override
    public int updateHealthallDb(HealthallDb healthallDb)
    {
        return healthallDbMapper.updateHealthallDb(healthallDb);
    }

    /**
     * 批量删除机房健康
     * 
     * @param names 需要删除的机房健康主键
     * @return 结果
     */
    @Override
    public int deleteHealthallDbByNames(String[] names)
    {
        return healthallDbMapper.deleteHealthallDbByNames(names);
    }

    /**
     * 删除机房健康信息
     * 
     * @param name 机房健康主键
     * @return 结果
     */
    @Override
    public int deleteHealthallDbByName(String name)
    {
        return healthallDbMapper.deleteHealthallDbByName(name);
    }
}
