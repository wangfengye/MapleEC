package com.ascend.wangfeng.wifimanage.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 * 时间规划(t_plan)
 */

public class Plan implements Serializable,MultiItemEntity{

    private static final long serialVersionUID = -1314258825409184663L;

    private Long pid;
    private Long dmac;
    private Long bmac;
    private Integer ptype;
    private Long starttime;
    private Long endtime;
    private Boolean power;

    @Override
    public int getItemType() {
        return 0;
    }
    private static final String[] items = {"每日", "工作日(周一至周五)", "假日(周末)"};
    public String getTypeStr(){
        return items[getPtype()];
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getDmac() {
        return dmac;
    }

    public void setDmac(Long dmac) {
        this.dmac = dmac;
    }

    public Long getBmac() {
        return bmac;
    }

    public void setBmac(Long bmac) {
        this.bmac = bmac;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public Boolean getPower() {
        return power;
    }

    public void setPower(Boolean power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "pid=" + pid +
                ", dmac='" + dmac + '\'' +
                ", bmac='" + bmac + '\'' +
                ", ptype=" + ptype +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", power=" + power +
                '}';
    }
}
