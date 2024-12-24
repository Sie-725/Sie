package com.ruoyi.todo.mapper;

import java.util.List;
import com.ruoyi.todo.domain.RoomDb;

/**
 * 机房信息Mapper接口
 * 
 * @author ruoyi
 * @date 2024-12-10
 */
public interface RoomDbMapper 
{
    /**
     * 查询机房信息
     * 
     * @param name 机房信息主键
     * @return 机房信息
     */
    public RoomDb selectRoomDbByName(String name);

    /**
     * 查询机房信息列表
     * 
     * @param roomDb 机房信息
     * @return 机房信息集合
     */
    public List<RoomDb> selectRoomDbList(RoomDb roomDb);

    /**
     * 新增机房信息
     * 
     * @param roomDb 机房信息
     * @return 结果
     */
    public int insertRoomDb(RoomDb roomDb);

    /**
     * 修改机房信息
     * 
     * @param roomDb 机房信息
     * @return 结果
     */
    public int updateRoomDb(RoomDb roomDb);

    /**
     * 删除机房信息
     * 
     * @param name 机房信息主键
     * @return 结果
     */
    public int deleteRoomDbByName(String name);

    /**
     * 批量删除机房信息
     * 
     * @param names 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRoomDbByNames(String[] names);
}
