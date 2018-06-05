package com.ascend.wangfeng.wifimanage.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 上网事件表(t)
 */

public class Event implements MultiItemEntity {

    private Long id;
    private Long dmac;
    private Long bmac;
    private Long time;
    private Integer online;
    // vo 属性
    private Person person;
    private Device device;

    @Override
    public int getItemType() {
        return 0;
    }

    public String getEventStr() {
        switch (getOnline()) {
            case 1:
                return "上线";
            case 0:
                return "下线";
            default:
                return "未知";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", dmac='" + dmac + '\'' +
                ", bmac='" + bmac + '\'' +
                ", time=" + time +
                ", online=" + online +
                ", person=" + person +
                ", device=" + device +
                '}';
    }
}