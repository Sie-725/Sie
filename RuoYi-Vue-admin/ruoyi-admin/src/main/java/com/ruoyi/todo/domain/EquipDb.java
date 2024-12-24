package com.ruoyi.todo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备统计对象 todo_equip_db
 * 
 * @author ruoyi
 * @date 2024-12-25
 */
public class EquipDb extends BaseEntity
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

    /** 运行时间 */
    @Excel(name = "运行时间")
    private String life;

    /** 地区 */
    @Excel(name = "地区")
    private String area;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 厂家 */
    @Excel(name = "厂家")
    private String factory;

    /** 型号 */
    @Excel(name = "型号")
    private String model;

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
    public void setLife(String life) 
    {
        this.life = life;
    }

    public String getLife() 
    {
        return life;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setFactory(String factory) 
    {
        this.factory = factory;
    }

    public String getFactory() 
    {
        return factory;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("system", getSystem())
            .append("line", getLine())
            .append("name", getName())
            .append("life", getLife())
            .append("area", getArea())
            .append("status", getStatus())
            .append("type", getType())
            .append("factory", getFactory())
            .append("model", getModel())
            .toString();
    }
}
