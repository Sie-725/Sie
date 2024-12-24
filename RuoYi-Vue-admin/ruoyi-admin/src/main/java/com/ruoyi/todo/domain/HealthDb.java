package com.ruoyi.todo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 机房健康对象 health_db
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
public class HealthDb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 时间 */
    @Excel(name = "时间")
    private String date;

    /** 温湿度 */
    @Excel(name = "温湿度")
    private String temperature;

    /** 硬件 */
    @Excel(name = "硬件")
    private String hardware;

    /** 台账 */
    @Excel(name = "台账")
    private String ledger;

    /** 告警 */
    @Excel(name = "告警")
    private String alarm;

    /** 故障 */
    @Excel(name = "故障")
    private String malfunction;

    /** 指标 */
    @Excel(name = "指标")
    private String indicators;

    /** 年限 */
    @Excel(name = "年限")
    private String life;

    /** 权重 */
    @Excel(name = "权重")
    private String weight;

    /** 总分 */
    @Excel(name = "总分")
    private String total;

    public void setDate(String date) 
    {
        this.date = date;
    }

    public String getDate() 
    {
        return date;
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
            .append("date", getDate())
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
