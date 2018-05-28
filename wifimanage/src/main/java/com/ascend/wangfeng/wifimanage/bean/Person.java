package com.ascend.wangfeng.wifimanage.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 人员
 */

public class Person implements MultiItemEntity,Serializable {

    private static final long serialVersionUID = -8680204258925672590L;
    private Long id;
    private String name;
    private int imgUrl;
    private boolean sex;//true;man,false:woman

    private boolean chosed;// 是否选中,选择列表中使用
    private boolean online;// 是否在线

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }
    public boolean getSex() {
        return this.sex;
    }
    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public boolean isChosed() {
        return chosed;
    }

    public void setChosed(boolean chosed) {
        this.chosed = chosed;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
