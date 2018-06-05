package com.ascend.wangfeng.wifimanage.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 人员(t_person)
 */

public class Person implements MultiItemEntity,Serializable {

    private static final long serialVersionUID = -8680204258925672590L;
    private Long pid;
    private Long bmac;
    private String pname;
    private Integer pimage;
    // 表现属性
    private boolean selected; // 选中
    private boolean online;// 在线

    @Override
    public int getItemType() {
        return 0;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getBmac() {
        return bmac;
    }

    public void setBmac(Long bmac) {
        this.bmac = bmac;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getPimage() {
        return pimage;
    }

    public void setPimage(Integer pimage) {
        this.pimage = pimage;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "Person{" +
                "pid=" + pid +
                ", bmac=" + bmac +
                ", pname='" + pname + '\'' +
                ", pimage=" + pimage +
                '}';
    }
}
