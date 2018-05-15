package com.ascend.wangfeng.wifimanage.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 * 时间规划
 */
@Entity
public class Plan implements Serializable{
    @Transient
    private static final long serialVersionUID = -1314258825409184663L;
    @Id(autoincrement = true)
    private Long id;
    private Long Did;
    private int type;// 规划类型,即每周哪几天执行
    private Long starttime;
    private Long endtime;
    @Generated(hash = 928908224)
    public Plan(Long id, Long Did, int type, Long starttime, Long endtime) {
        this.id = id;
        this.Did = Did;
        this.type = type;
        this.starttime = starttime;
        this.endtime = endtime;
    }
    @Generated(hash = 592612124)
    public Plan() {
    }
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
