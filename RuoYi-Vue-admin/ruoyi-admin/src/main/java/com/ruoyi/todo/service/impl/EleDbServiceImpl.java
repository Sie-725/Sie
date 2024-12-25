package com.ruoyi.todo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.todo.mapper.EleDbMapper;
import com.ruoyi.todo.domain.EleDb;
import com.ruoyi.todo.service.IEleDbService;

/**
 * 电池测试Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-12-26
 */
@Service
public class EleDbServiceImpl implements IEleDbService 
{
    @Autowired
    private EleDbMapper eleDbMapper;

    /**
     * 查询电池测试
     * 
     * @param id 电池测试主键
     * @return 电池测试
     */
    @Override
    public EleDb selectEleDbById(Long id)
    {
        return eleDbMapper.selectEleDbById(id);
    }

    /**
     * 查询电池测试列表
     * 
     * @param eleDb 电池测试
     * @return 电池测试
     */
    @Override
    public List<EleDb> selectEleDbList(EleDb eleDb)
    {
        return eleDbMapper.selectEleDbList(eleDb);
    }

    /**
     * 新增电池测试
     * 
     * @param eleDb 电池测试
     * @return 结果
     */
    @Override
    public int insertEleDb(EleDb eleDb)
    {
        return eleDbMapper.insertEleDb(eleDb);
    }

    /**
     * 修改电池测试
     * 
     * @param eleDb 电池测试
     * @return 结果
     */
    @Override
    public int updateEleDb(EleDb eleDb)
    {
        return eleDbMapper.updateEleDb(eleDb);
    }

    /**
     * 批量删除电池测试
     * 
     * @param ids 需要删除的电池测试主键
     * @return 结果
     */
    @Override
    public int deleteEleDbByIds(Long[] ids)
    {
        return eleDbMapper.deleteEleDbByIds(ids);
    }

    /**
     * 删除电池测试信息
     * 
     * @param id 电池测试主键
     * @return 结果
     */
    @Override
    public int deleteEleDbById(Long id)
    {
        return eleDbMapper.deleteEleDbById(id);
    }
}
