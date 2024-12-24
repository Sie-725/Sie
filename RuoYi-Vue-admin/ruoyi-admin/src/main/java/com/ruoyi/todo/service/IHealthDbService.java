package com.ruoyi.todo.service;

import java.util.List;
import com.ruoyi.todo.domain.HealthDb;

/**
 * 机房健康Service接口
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
public interface IHealthDbService 
{
    /**
     * 查询机房健康
     * 
     * @param date 机房健康主键
     * @return 机房健康
     */
    public HealthDb selectHealthDbByDate(String date);

    /**
     * 查询机房健康列表
     * 
     * @param healthDb 机房健康
     * @return 机房健康集合
     */
    public List<HealthDb> selectHealthDbList(HealthDb healthDb);

    /**
     * 新增机房健康
     * 
     * @param healthDb 机房健康
     * @return 结果
     */
    public int insertHealthDb(HealthDb healthDb);

    /**
     * 修改机房健康
     * 
     * @param healthDb 机房健康
     * @return 结果
     */
    public int updateHealthDb(HealthDb healthDb);

    /**
     * 批量删除机房健康
     * 
     * @param dates 需要删除的机房健康主键集合
     * @return 结果
     */
    public int deleteHealthDbByDates(String[] dates);

    /**
     * 删除机房健康信息
     * 
     * @param date 机房健康主键
     * @return 结果
     */
    public int deleteHealthDbByDate(String date);
}
