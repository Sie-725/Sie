package com.ruoyi.todo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 机房信息对象 room_db
 * 
 * @author ruoyi
 * @date 2024-12-10
 */
public class RoomDb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 经度 */
    @Excel(name = "经度")
    private Long gisLo;

    /** 纬度 */
    @Excel(name = "纬度")
    private String gisLa;

    /** 线路 */
    @Excel(name = "线路")
    private String line;

    /** $column.columnComment */
    private String jk;

    /** 地区 */
    @Excel(name = "地区")
    private String area;

    /** 等级 */
    @Excel(name = "等级")
    private String lever;

    /** $column.columnComment */
    private String temperature;

    /** $column.columnComment */
    private String hardware;

    /** $column.columnComment */
    private String ledger;

    /** $column.columnComment */
    private String alarm;

    /** $column.columnComment */
    private String malfunction;

    /** $column.columnComment */
    private String indicators;

    /** $column.columnComment */
    private String life;

    /** $column.columnComment */
    private String weight;

    /** $column.columnComment */
    private String total;

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setGisLo(Long gisLo) 
    {
        this.gisLo = gisLo;
    }

    public Long getGisLo() 
    {
        return gisLo;
    }
    public void setGisLa(String gisLa) 
    {
        this.gisLa = gisLa;
    }

    public String getGisLa() 
    {
        return gisLa;
    }
    public void setLine(String line) 
    {
        this.line = line;
    }

    public String getLine() 
    {
        return line;
    }
    public void setJk(String jk) 
    {
        this.jk = jk;
    }

    public String getJk() 
    {
        return jk;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setLever(String lever) 
    {
        this.lever = lever;
    }

    public String getLever() 
    {
        return lever;
    }
    public void setTemperature(String temperature) 
    {
        this.temperature = temperature;
    }

    public String getTemperature() 
    {
        return temperature;
    }
    public void setHardware(String hardware) 
    {
        this.hardware = hardware;
    }

    public String getHardware() 
    {
        return hardware;
    }
    public void setLedger(String ledger) 
    {
        this.ledger = ledger;
    }

    public String getLedger() 
    {
        return ledger;
    }
    public void setAlarm(String alarm) 
    {
        this.alarm = alarm;
    }

    public String getAlarm() 
    {
        return alarm;
    }
    public void setMalfunction(String malfunction) 
    {
        this.malfunction = malfunction;
    }

    public String getMalfunction() 
    {
        return malfunction;
    }
    public void setIndicators(String indicators) 
    {
        this.indicators = indicators;
    }

    public String getIndicators() 
    {
        return indicators;
    }
    public void setLife(String life) 
    {
        this.life = life;
    }

    public String getLife() 
    {
        return life;
    }
    public void setWeight(String weight) 
    {
        this.weight = weight;
    }

    public String getWeight() 
    {
        return weight;
    }
    public void setTotal(String total) 
    {
        this.total = total;
    }

    public String getTotal() 
    {
        return total;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("name", getName())
            .append("gisLo", getGisLo())
            .append("gisLa", getGisLa())
            .append("line", getLine())
            .append("jk", getJk())
            .append("area", getArea())
            .append("lever", getLever())
            .append("temperature", getTemperature())
            .append("hardware", getHardware())
            .append("ledger", getLedger())
            .append("alarm", getAlarm())
            .append("malfunction", getMalfunction())
            .append("indicators", getIndicators())
            .append("life", getLife())
            .append("weight", getWeight())
            .append("total", getTotal())
            .toString();
    }
}
