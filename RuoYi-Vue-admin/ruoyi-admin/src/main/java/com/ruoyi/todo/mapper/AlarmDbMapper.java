package com.ruoyi.todo.mapper;

import java.util.List;
import com.ruoyi.todo.domain.AlarmDb;

/**
 * 故障统计Mapper接口
 * 
 * @author ruoyi
 * @date 2024-12-25
 */
public interface AlarmDbMapper 
{
    /**
     * 查询故障统计
     * 
     * @param id 故障统计主键
     * @return 故障统计
     */
    public AlarmDb selectAlarmDbById(Long id);

    /**
     * 查询故障统计列表
     * 
     * @param alarmDb 故障统计
     * @return 故障统计集合
     */
    public List<AlarmDb> selectAlarmDbList(AlarmDb alarmDb);

    /**
     * 新增故障统计
     * 
     * @param alarmDb 故障统计
     * @return 结果
     */
    public int insertAlarmDb(AlarmDb alarmDb);

    /**
     * 修改故障统计
     * 
     * @param alarmDb 故障统计
     * @return 结果
     */
    public int updateAlarmDb(AlarmDb alarmDb);

    /**
     * 删除故障统计
     * 
     * @param id 故障统计主键
     * @return 结果
     */
    public int deleteAlarmDbById(Long id);

    /**
     * 批量删除故障统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAlarmDbByIds(Long[] ids);
}
