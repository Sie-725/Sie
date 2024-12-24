package com.ruoyi.todo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 电池测试对象 ele_db
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
public class EleDb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private String eleNum;

    /** 所属设备 */
    @Excel(name = "所属设备")
    private String eleName;

    /** 电池型号 */
    @Excel(name = "电池型号")
    private String eleType;

    /** 组别 */
    @Excel(name = "组别")
    private String eleGroup;

    /** 单体电池号 */
    @Excel(name = "单体电池号")
    private String eleGronum;

    /** 内阻 */
    @Excel(name = "内阻")
    private String eleResis;

    /** 电压 */
    @Excel(name = "电压")
    private String eleVoltage;

    /** 内阻2024春 */
    private String eleResis1;

    /** 电压2024春 */
    private String eleVoltage1;

    /** 内阻2023秋 */
    private String eleResis2;

    /** 电压2023秋 */
    private String eleVoltage2;

    /** 内阻2023春 */
    private String eleResis3;

    /** 电压2023春 */
    private String eleVoltage3;

    /** 内阻2022秋 */
    private String eleResis4;

    /** 电压2022秋 */
    private String eleVoltage4;

    /** 内阻2022春 */
    private String eleResis5;

    /** 电压2022春 */
    private String eleVoltage5;

    /** 内阻2021秋 */
    private String eleResis6;

    /** 电压2021秋 */
    private String eleVoltage6;

    public void setEleNum(String eleNum) 
    {
        this.eleNum = eleNum;
    }

    public String getEleNum() 
    {
        return eleNum;
    }
    public void setEleName(String eleName) 
    {
        this.eleName = eleName;
    }

    public String getEleName() 
    {
        return eleName;
    }
    public void setEleType(String eleType) 
    {
        this.eleType = eleType;
    }

    public String getEleType() 
    {
        return eleType;
    }
    public void setEleGroup(String eleGroup) 
    {
        this.eleGroup = eleGroup;
    }

    public String getEleGroup() 
    {
        return eleGroup;
    }
    public void setEleGronum(String eleGronum) 
    {
        this.eleGronum = eleGronum;
    }

    public String getEleGronum() 
    {
        return eleGronum;
    }
    public void setEleResis(String eleResis) 
    {
        this.eleResis = eleResis;
    }

    public String getEleResis() 
    {
        return eleResis;
    }
    public void setEleVoltage(String eleVoltage) 
    {
        this.eleVoltage = eleVoltage;
    }

    public String getEleVoltage() 
    {
        return eleVoltage;
    }
    public void setEleResis1(String eleResis1) 
    {
        this.eleResis1 = eleResis1;
    }

    public String getEleResis1() 
    {
        return eleResis1;
    }
    public void setEleVoltage1(String eleVoltage1) 
    {
        this.eleVoltage1 = eleVoltage1;
    }

    public String getEleVoltage1() 
    {
        return eleVoltage1;
    }
    public void setEleResis2(String eleResis2) 
    {
        this.eleResis2 = eleResis2;
    }

    public String getEleResis2() 
    {
        return eleResis2;
    }
    public void setEleVoltage2(String eleVoltage2) 
    {
        this.eleVoltage2 = eleVoltage2;
    }

    public String getEleVoltage2() 
    {
        return eleVoltage2;
    }
    public void setEleResis3(String eleResis3) 
    {
        this.eleResis3 = eleResis3;
    }

    public String getEleResis3() 
    {
        return eleResis3;
    }
    public void setEleVoltage3(String eleVoltage3) 
    {
        this.eleVoltage3 = eleVoltage3;
    }

    public String getEleVoltage3() 
    {
        return eleVoltage3;
    }
    public void setEleResis4(String eleResis4) 
    {
        this.eleResis4 = eleResis4;
    }

    public String getEleResis4() 
    {
        return eleResis4;
    }
    public void setEleVoltage4(String eleVoltage4) 
    {
        this.eleVoltage4 = eleVoltage4;
    }

    public String getEleVoltage4() 
    {
        return eleVoltage4;
    }
    public void setEleResis5(String eleResis5) 
    {
        this.eleResis5 = eleResis5;
    }

    public String getEleResis5() 
    {
        return eleResis5;
    }
    public void setEleVoltage5(String eleVoltage5) 
    {
        this.eleVoltage5 = eleVoltage5;
    }

    public String getEleVoltage5() 
    {
        return eleVoltage5;
    }
    public void setEleResis6(String eleResis6) 
    {
        this.eleResis6 = eleResis6;
    }

    public String getEleResis6() 
    {
        return eleResis6;
    }
    public void setEleVoltage6(String eleVoltage6) 
    {
        this.eleVoltage6 = eleVoltage6;
    }

    public String getEleVoltage6() 
    {
        return eleVoltage6;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("eleNum", getEleNum())
            .append("eleName", getEleName())
            .append("eleType", getEleType())
            .append("eleGroup", getEleGroup())
            .append("eleGronum", getEleGronum())
            .append("eleResis", getEleResis())
            .append("eleVoltage", getEleVoltage())
            .append("eleResis1", getEleResis1())
            .append("eleVoltage1", getEleVoltage1())
            .append("eleResis2", getEleResis2())
            .append("eleVoltage2", getEleVoltage2())
            .append("eleResis3", getEleResis3())
            .append("eleVoltage3", getEleVoltage3())
            .append("eleResis4", getEleResis4())
            .append("eleVoltage4", getEleVoltage4())
            .append("eleResis5", getEleResis5())
            .append("eleVoltage5", getEleVoltage5())
            .append("eleResis6", getEleResis6())
            .append("eleVoltage6", getEleVoltage6())
            .toString();
    }
}
