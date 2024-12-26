package com.ruoyi.todo.service;

import java.util.List;
import com.ruoyi.todo.domain.EleDb;

/**
 * 电池测试Service接口
 * 
 * @author ruoyi
 * @date 2024-12-26
 */
public interface IEleDbService 
{
    /**
     * 查询电池测试
     * 
     * @param id 电池测试主键
     * @return 电池测试
     */
    public EleDb selectEleDbById(Long id);

    /**
     * 查询电池测试列表
     * 
     * @param eleDb 电池测试
     * @return 电池测试集合
     */
    public List<EleDb> selectEleDbList(EleDb eleDb);

    /**
     * 新增电池测试
     * 
     * @param eleDb 电池测试
     * @return 结果
     */
    public int insertEleDb(EleDb eleDb);

    /**
     * 修改电池测试
     * 
     * @param eleDb 电池测试
     * @return 结果
     */
    public int updateEleDb(EleDb eleDb);

    /**
     * 批量删除电池测试
     * 
     * @param ids 需要删除的电池测试主键集合
     * @return 结果
     */
    public int deleteEleDbByIds(Long[] ids);

    /**
     * 删除电池测试信息
     * 
     * @param id 电池测试主键
     * @return 结果
     */
    public int deleteEleDbById(Long id);
}
