package com.ascend.wangfeng.wifimanage.bean;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 人员 设备s 映射表
 */

public class PersonDevicesMap {

    private Long id;
    private long pId;//人员id;
    private long dId;//设备id;

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
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
