package com.ascend.wangfeng.wifimanage.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 事件表
 */
@Entity
public class Event {
    @Id
    private long id;
    private long time;
    private long pId;//人员id
    private long dId;//设备id
    private int event;//事件
    @Generated(hash = 1187009342)
    public Event(long id, long time, long pId, long dId, int event) {
        this.id = id;
        this.time = time;
        this.pId = pId;
        this.dId = dId;
        this.event = event;
    }
    @Generated(hash = 344677835)
    public Event() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
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
