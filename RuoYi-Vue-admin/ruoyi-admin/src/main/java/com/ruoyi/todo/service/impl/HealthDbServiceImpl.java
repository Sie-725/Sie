package com.ruoyi.todo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.todo.mapper.HealthDbMapper;
import com.ruoyi.todo.domain.HealthDb;
import com.ruoyi.todo.service.IHealthDbService;

/**
 * 机房健康Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@Service
public class HealthDbServiceImpl implements IHealthDbService 
{
    @Autowired
    private HealthDbMapper healthDbMapper;

    /**
     * 查询机房健康
     * 
     * @param date 机房健康主键
     * @return 机房健康
     */
    @Override
    public HealthDb selectHealthDbByDate(String date)
    {
        return healthDbMapper.selectHealthDbByDate(date);
    }

    /**
     * 查询机房健康列表
     * 
     * @param healthDb 机房健康
     * @return 机房健康
     */
    @Override
    public List<HealthDb> selectHealthDbList(HealthDb healthDb)
    {
        return healthDbMapper.selectHealthDbList(healthDb);
    }

    /**
     * 新增机房健康
     * 
     * @param healthDb 机房健康
     * @return 结果
     */
    @Override
    public int insertHealthDb(HealthDb healthDb)
    {
        return healthDbMapper.insertHealthDb(healthDb);
    }

    /**
     * 修改机房健康
     * 
     * @param healthDb 机房健康
     * @return 结果
     */
    @Override
    public int updateHealthDb(HealthDb healthDb)
    {
        return healthDbMapper.updateHealthDb(healthDb);
    }

    /**
     * 批量删除机房健康
     * 
     * @param dates 需要删除的机房健康主键
     * @return 结果
     */
    @Override
    public int deleteHealthDbByDates(String[] dates)
    {
        return healthDbMapper.deleteHealthDbByDates(dates);
    }

    /**
     * 删除机房健康信息
     * 
     * @param date 机房健康主键
     * @return 结果
     */
    @Override
    public int deleteHealthDbByDate(String date)
    {
        return healthDbMapper.deleteHealthDbByDate(date);
    }
}
