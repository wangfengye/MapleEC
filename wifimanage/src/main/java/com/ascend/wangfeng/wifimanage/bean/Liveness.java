package com.ascend.wangfeng.wifimanage.bean;

/**
 * Created by fengye on 2018/5/24.
 * email 1040441325@qq.com
 * 活跃度
 */

public class Liveness {
    private Long id;
    private Long dId;
    private Long time;
    private int hour;
    private int avalue;// 活跃度,0-60

    public Liveness() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getdId() {
        return dId;
    }

    public void setdId(Long dId) {
        this.dId = dId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getAvalue() {
        return avalue;
    }

    public void setAvalue(int avalue) {
        this.avalue = avalue;
    }
}
