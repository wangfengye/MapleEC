package com.ascend.wangfeng.wifimanage.bean;

/**
 * Created by fengye on 2018/5/24.
 * email 1040441325@qq.com
 * 活跃度(t_activity)
 */

public class Liveness {
    private Long aid;
    private Long bmac;
    private Long dmac;
    private Long aYear;
    private Long aHour;
    private Integer avalue;// 活跃度,0-60

    public Long getTimeStamp() {
        return aYear + aHour * 60 * 60 * 1000;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getBmac() {
        return bmac;
    }

    public void setBmac(Long bmac) {
        this.bmac = bmac;
    }

    public Long getDmac() {
        return dmac;
    }

    public void setDmac(Long dmac) {
        this.dmac = dmac;
    }

    public Long getaYear() {
        return aYear;
    }

    public void setaYear(Long aYear) {
        this.aYear = aYear;
    }

    public Long getaHour() {
        return aHour;
    }

    public void setaHour(Long aHour) {
        this.aHour = aHour;
    }

    public Integer getAvalue() {
        return avalue;
    }

    public void setAvalue(Integer avalue) {
        this.avalue = avalue;
    }

    @Override
    public String toString() {
        return "Liveness{" +
                "aid=" + aid +
                ", bmac=" + bmac +
                ", dmac=" + dmac +
                ", aYear=" + aYear +
                ", aHour=" + aHour +
                ", avalue=" + avalue +
                '}';
    }
}
