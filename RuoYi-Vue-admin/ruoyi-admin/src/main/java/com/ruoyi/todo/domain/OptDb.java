package com.ruoyi.todo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 光缆测试对象 opt_db
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
public class OptDb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 光缆名称 */
    @Excel(name = "光缆名称")
    private String optName;

    /** 光缆序号 */
    private String optNum;

    /** 光缆区间 */
    @Excel(name = "光缆区间")
    private String optStage;

    /** 光缆距离 */
    @Excel(name = "光缆距离")
    private String optLength;

    /** 光缆衰耗 */
    @Excel(name = "光缆衰耗")
    private String optDec;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String optRemark;

    /**  */
    private String optDec1;

    /** $column.columnComment */
    private String optDec2;

    /** $column.columnComment */
    private String optDec3;

    /** $column.columnComment */
    private String optDec4;

    /** $column.columnComment */
    private String optDec5;

    public void setOptName(String optName) 
    {
        this.optName = optName;
    }

    public String getOptName() 
    {
        return optName;
    }
    public void setOptNum(String optNum) 
    {
        this.optNum = optNum;
    }

    public String getOptNum() 
    {
        return optNum;
    }
    public void setOptStage(String optStage) 
    {
        this.optStage = optStage;
    }

    public String getOptStage() 
    {
        return optStage;
    }
    public void setOptLength(String optLength) 
    {
        this.optLength = optLength;
    }

    public String getOptLength() 
    {
        return optLength;
    }
    public void setOptDec(String optDec) 
    {
        this.optDec = optDec;
    }

    public String getOptDec() 
    {
        return optDec;
    }
    public void setOptRemark(String optRemark) 
    {
        this.optRemark = optRemark;
    }

    public String getOptRemark() 
    {
        return optRemark;
    }
    public void setOptDec1(String optDec1) 
    {
        this.optDec1 = optDec1;
    }

    public String getOptDec1() 
    {
        return optDec1;
    }
    public void setOptDec2(String optDec2) 
    {
        this.optDec2 = optDec2;
    }

    public String getOptDec2() 
    {
        return optDec2;
    }
    public void setOptDec3(String optDec3) 
    {
        this.optDec3 = optDec3;
    }

    public String getOptDec3() 
    {
        return optDec3;
    }
    public void setOptDec4(String optDec4) 
    {
        this.optDec4 = optDec4;
    }

    public String getOptDec4() 
    {
        return optDec4;
    }
    public void setOptDec5(String optDec5) 
    {
        this.optDec5 = optDec5;
    }

    public String getOptDec5() 
    {
        return optDec5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("optName", getOptName())
            .append("optNum", getOptNum())
            .append("optStage", getOptStage())
            .append("optLength", getOptLength())
            .append("optDec", getOptDec())
            .append("optRemark", getOptRemark())
            .append("optDec1", getOptDec1())
            .append("optDec2", getOptDec2())
            .append("optDec3", getOptDec3())
            .append("optDec4", getOptDec4())
            .append("optDec5", getOptDec5())
            .toString();
    }
}
