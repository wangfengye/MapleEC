package com.ascend.wangfeng.wifimanage.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 事件表
 */

public class Event {

    private Long id;
    private long time;
    private long pId;//人员id
    private long dId;//设备id
    private int event;//事件

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public long getPId() {
        return this.pId;
    }
    public void setPId(long pId) {
        this.pId = pId;
    }
    public long getDId() {
        return this.dId;
    }
    public void setDId(long dId) {
        this.dId = dId;
    }
    public int getEvent() {
        return this.event;
    }
    public void setEvent(int event) {
        this.event = event;
    }
}