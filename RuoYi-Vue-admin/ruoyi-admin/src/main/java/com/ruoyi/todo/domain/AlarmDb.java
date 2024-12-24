package com.ruoyi.todo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 故障统计对象 todo_alarm_db
 * 
 * @author ruoyi
 * @date 2024-12-25
 */
public class AlarmDb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 系统 */
    @Excel(name = "系统")
    private String system;

    /** 线路 */
    @Excel(name = "线路")
    private String line;

    /** 设备 */
    @Excel(name = "设备")
    private String name;

    /** 端口 */
    @Excel(name = "端口")
    private String port;

    /** 等级 */
    @Excel(name = "等级")
    private String lever;

    /** 描述 */
    @Excel(name = "描述")
    private String des;

    /** 日期 */
    @Excel(name = "日期")
    private String date;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSystem(String system) 
    {
        this.system = system;
    }

    public String getSystem() 
    {
        return system;
    }
    public void setLine(String line) 
    {
        this.line = line;
    }

    public String getLine() 
    {
        return line;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPort(String port) 
    {
        this.port = port;
    }

    public String getPort() 
    {
        return port;
    }
    public void setLever(String lever) 
    {
        this.lever = lever;
    }

    public String getLever() 
    {
        return lever;
    }
    public void setDes(String des) 
    {
        this.des = des;
    }

    public String getDes() 
    {
        return des;
    }
    public void setDate(String date) 
    {
        this.date = date;
    }

    public String getDate() 
    {
        return date;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("system", getSystem())
            .append("line", getLine())
            .append("name", getName())
            .append("port", getPort())
            .append("lever", getLever())
            .append("des", getDes())
            .append("date", getDate())
            .append("status", getStatus())
            .toString();
    }
}
