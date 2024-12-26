package com.ruoyi.todo.service;

import java.util.List;
import com.ruoyi.todo.domain.HealthallDb;

/**
 * 机房健康Service接口
 * 
 * @author ruoyi
 * @date 2024-12-10
 */
public interface IHealthallDbService 
{
    /**
     * 查询机房健康
     * 
     * @param name 机房健康主键
     * @return 机房健康
     */
    public HealthallDb selectHealthallDbByName(String name);

    /**
     * 查询机房健康列表
     * 
     * @param healthallDb 机房健康
     * @return 机房健康集合
     */
    public List<HealthallDb> selectHealthallDbList(HealthallDb healthallDb);

    /**
     * 新增机房健康
     * 
     * @param healthallDb 机房健康
     * @return 结果
     */
    public int insertHealthallDb(HealthallDb healthallDb);

    /**
     * 修改机房健康
     * 
     * @param healthallDb 机房健康
     * @return 结果
     */
    public int updateHealthallDb(HealthallDb healthallDb);

    /**
     * 批量删除机房健康
     * 
     * @param names 需要删除的机房健康主键集合
     * @return 结果
     */
    public int deleteHealthallDbByNames(String[] names);

    /**
     * 删除机房健康信息
     * 
     * @param name 机房健康主键
     * @return 结果
     */
    public int deleteHealthallDbByName(String name);
}
