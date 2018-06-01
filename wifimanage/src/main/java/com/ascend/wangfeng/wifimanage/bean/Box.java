package com.ascend.wangfeng.wifimanage.bean;

/**
 * Created by fengye on 2018/5/24.
 * email 1040441325@qq.com
 * 盒子
 */

public class Box {
    private Long bid;
    private String bmac;
    private String bver;
    private double blng;
    private double blat;
    private String routerMac;// 路由器mac;

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getBmac() {
        return bmac;
    }

    public void setBmac(String bmac) {
        this.bmac = bmac;
    }

    public String getBver() {
        return bver;
    }

    public void setBver(String bver) {
        this.bver = bver;
    }

    public double getBlng() {
        return blng;
    }

    public void setBlng(double blng) {
        this.blng = blng;
    }

    public double getBlat() {
        return blat;
    }

    public void setBlat(double blat) {
        this.blat = blat;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    @Override
    public String toString() {
        return "Box{" +
                "bid=" + bid +
                ", bmac='" + bmac + '\'' +
                ", bver='" + bver + '\'' +
                ", blng=" + blng +
                ", blat=" + blat +
                ", routerMac='" + routerMac + '\'' +
                '}';
    }
}
