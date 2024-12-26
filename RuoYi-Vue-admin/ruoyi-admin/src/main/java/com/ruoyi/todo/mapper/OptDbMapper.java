package com.ruoyi.todo.mapper;

import java.util.List;
import com.ruoyi.todo.domain.OptDb;

/**
 * 光缆测试Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
public interface OptDbMapper 
{
    /**
     * 查询光缆测试
     * 
     * @param optName 光缆测试主键
     * @return 光缆测试
     */
    public OptDb selectOptDbByOptName(String optName);

    /**
     * 查询光缆测试列表
     * 
     * @param optDb 光缆测试
     * @return 光缆测试集合
     */
    public List<OptDb> selectOptDbList(OptDb optDb);

    /**
     * 新增光缆测试
     * 
     * @param optDb 光缆测试
     * @return 结果
     */
    public int insertOptDb(OptDb optDb);

    /**
     * 修改光缆测试
     * 
     * @param optDb 光缆测试
     * @return 结果
     */
    public int updateOptDb(OptDb optDb);

    /**
     * 删除光缆测试
     * 
     * @param optName 光缆测试主键
     * @return 结果
     */
    public int deleteOptDbByOptName(String optName);

    /**
     * 批量删除光缆测试
     * 
     * @param optNames 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOptDbByOptNames(String[] optNames);
}
