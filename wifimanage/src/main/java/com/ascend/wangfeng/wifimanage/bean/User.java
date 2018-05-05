package com.ascend.wangfeng.wifimanage.bean;

/**
 * Created by fengye on 2018/5/5.
 * email 1040441325@qq.com
 * 用户
 */

public class User {
    private String dId;// 设备编号
    private String password;
    private String name;//姓名
    private boolean sex;//性别,默认false,男
    private String icon;// 头像url
    private String address; //地址
    private double latitude;//纬度;
    private double longitude;//经度;

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
