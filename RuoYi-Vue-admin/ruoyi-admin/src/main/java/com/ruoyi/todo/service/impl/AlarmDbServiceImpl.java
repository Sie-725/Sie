package com.ruoyi.todo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.todo.mapper.AlarmDbMapper;
import com.ruoyi.todo.domain.AlarmDb;
import com.ruoyi.todo.service.IAlarmDbService;

/**
 * 故障统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-12-25
 */
@Service
public class AlarmDbServiceImpl implements IAlarmDbService 
{
    @Autowired
    private AlarmDbMapper alarmDbMapper;

    /**
     * 查询故障统计
     * 
     * @param id 故障统计主键
     * @return 故障统计
     */
    @Override
    public AlarmDb selectAlarmDbById(Long id)
    {
        return alarmDbMapper.selectAlarmDbById(id);
    }

    /**
     * 查询故障统计列表
     * 
     * @param alarmDb 故障统计
     * @return 故障统计
     */
    @Override
    public List<AlarmDb> selectAlarmDbList(AlarmDb alarmDb)
    {
        return alarmDbMapper.selectAlarmDbList(alarmDb);
    }

    /**
     * 新增故障统计
     * 
     * @param alarmDb 故障统计
     * @return 结果
     */
    @Override
    public int insertAlarmDb(AlarmDb alarmDb)
    {
        return alarmDbMapper.insertAlarmDb(alarmDb);
    }

    /**
     * 修改故障统计
     * 
     * @param alarmDb 故障统计
     * @return 结果
     */
    @Override
    public int updateAlarmDb(AlarmDb alarmDb)
    {
        return alarmDbMapper.updateAlarmDb(alarmDb);
    }

    /**
     * 批量删除故障统计
     * 
     * @param ids 需要删除的故障统计主键
     * @return 结果
     */
    @Override
    public int deleteAlarmDbByIds(Long[] ids)
    {
        return alarmDbMapper.deleteAlarmDbByIds(ids);
    }

    /**
     * 删除故障统计信息
     * 
     * @param id 故障统计主键
     * @return 结果
     */
    @Override
    public int deleteAlarmDbById(Long id)
    {
        return alarmDbMapper.deleteAlarmDbById(id);
    }
}
