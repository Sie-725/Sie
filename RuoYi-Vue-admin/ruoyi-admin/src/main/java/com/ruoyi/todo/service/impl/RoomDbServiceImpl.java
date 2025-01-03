package com.ruoyi.todo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.todo.mapper.RoomDbMapper;
import com.ruoyi.todo.domain.RoomDb;
import com.ruoyi.todo.service.IRoomDbService;

/**
 * 机房信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-12-26
 */
@Service
public class RoomDbServiceImpl implements IRoomDbService 
{
    @Autowired
    private RoomDbMapper roomDbMapper;

    /**
     * 查询机房信息
     * 
     * @param id 机房信息主键
     * @return 机房信息
     */
    @Override
    public RoomDb selectRoomDbById(Long id)
    {
        return roomDbMapper.selectRoomDbById(id);
    }

    /**
     * 查询机房信息列表
     * 
     * @param roomDb 机房信息
     * @return 机房信息
     */
    @Override
    public List<RoomDb> selectRoomDbList(RoomDb roomDb)
    {
        return roomDbMapper.selectRoomDbList(roomDb);
    }

    /**
     * 新增机房信息
     * 
     * @param roomDb 机房信息
     * @return 结果
     */
    @Override
    public int insertRoomDb(RoomDb roomDb)
    {
        return roomDbMapper.insertRoomDb(roomDb);
    }

    /**
     * 修改机房信息
     * 
     * @param roomDb 机房信息
     * @return 结果
     */
    @Override
    public int updateRoomDb(RoomDb roomDb)
    {
        return roomDbMapper.updateRoomDb(roomDb);
    }

    /**
     * 批量删除机房信息
     * 
     * @param ids 需要删除的机房信息主键
     * @return 结果
     */
    @Override
    public int deleteRoomDbByIds(Long[] ids)
    {
        return roomDbMapper.deleteRoomDbByIds(ids);
    }

    /**
     * 删除机房信息信息
     * 
     * @param id 机房信息主键
     * @return 结果
     */
    @Override
    public int deleteRoomDbById(Long id)
    {
        return roomDbMapper.deleteRoomDbById(id);
    }
}
