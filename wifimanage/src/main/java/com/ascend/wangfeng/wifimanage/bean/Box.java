package com.ascend.wangfeng.wifimanage.bean;

/**
 * Created by fengye on 2018/5/24.
 * email 1040441325@qq.com
 * 盒子
 */

public class Box {
    private Long id;
    private String mac;
    private String version;
    private double longtitude;
    private double latitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", version='" + version + '\'' +
                ", longtitude=" + longtitude +
                ", latitude=" + latitude +
                '}';
    }
}
