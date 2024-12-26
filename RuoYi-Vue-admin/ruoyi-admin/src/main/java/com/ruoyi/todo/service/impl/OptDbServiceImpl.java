package com.ruoyi.todo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.todo.mapper.OptDbMapper;
import com.ruoyi.todo.domain.OptDb;
import com.ruoyi.todo.service.IOptDbService;

/**
 * 光缆测试Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
@Service
public class OptDbServiceImpl implements IOptDbService 
{
    @Autowired
    private OptDbMapper optDbMapper;

    /**
     * 查询光缆测试
     * 
     * @param optName 光缆测试主键
     * @return 光缆测试
     */
    @Override
    public OptDb selectOptDbByOptName(String optName)
    {
        return optDbMapper.selectOptDbByOptName(optName);
    }

    /**
     * 查询光缆测试列表
     * 
     * @param optDb 光缆测试
     * @return 光缆测试
     */
    @Override
    public List<OptDb> selectOptDbList(OptDb optDb)
    {
        return optDbMapper.selectOptDbList(optDb);
    }

    /**
     * 新增光缆测试
     * 
     * @param optDb 光缆测试
     * @return 结果
     */
    @Override
    public int insertOptDb(OptDb optDb)
    {
        return optDbMapper.insertOptDb(optDb);
    }

    /**
     * 修改光缆测试
     * 
     * @param optDb 光缆测试
     * @return 结果
     */
    @Override
    public int updateOptDb(OptDb optDb)
    {
        return optDbMapper.updateOptDb(optDb);
    }

    /**
     * 批量删除光缆测试
     * 
     * @param optNames 需要删除的光缆测试主键
     * @return 结果
     */
    @Override
    public int deleteOptDbByOptNames(String[] optNames)
    {
        return optDbMapper.deleteOptDbByOptNames(optNames);
    }

    /**
     * 删除光缆测试信息
     * 
     * @param optName 光缆测试主键
     * @return 结果
     */
    @Override
    public int deleteOptDbByOptName(String optName)
    {
        return optDbMapper.deleteOptDbByOptName(optName);
    }
}
