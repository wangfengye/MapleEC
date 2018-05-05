package com.ascend.wangfeng.wifimanage.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 人员 设备s 映射表
 */
@Entity
public class PersonDevicesMap {
    @Id
    private long id;
    private long pId;//人员id;
    private long dId;//设备id;
    @Generated(hash = 898955644)
    public PersonDevicesMap(long id, long pId, long dId) {
        this.id = id;
        this.pId = pId;
        this.dId = dId;
    }
    @Generated(hash = 1504450817)
    public PersonDevicesMap() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
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
}
