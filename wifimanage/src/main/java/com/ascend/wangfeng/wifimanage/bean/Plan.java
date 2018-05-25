package com.ascend.wangfeng.wifimanage.bean;

import java.io.Serializable;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 * 时间规划
 */

public class Plan implements Serializable{

    private static final long serialVersionUID = -1314258825409184663L;

    private Long id;
    private Long Did;
    private int type;// 规划类型,即每周哪几天执行
    private Long starttime;
    private Long endtime;

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Long getStarttime() {
        return this.starttime;
    }
    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }
    public Long getEndtime() {
        return this.endtime;
    }
    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }
    public Long getDid() {
        return this.Did;
    }
    public void setDid(Long Did) {
        this.Did = Did;
    }
}
