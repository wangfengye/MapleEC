package com.ascend.wangfeng.wifimanage.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 设备
 */
public class Device implements MultiItemEntity,Serializable {

    private static final long serialVersionUID = -1251039796477336552L;

    private Long id;
    private Long pId;
    private String name;//设备名称
    private int type;// 设备类型
    private String mac;//mac地址
    private String ip;
    private String dhcp;//主机名
    private String bonjour;
    private String netbios;
    private String brand;//厂商
    private String model;//型号
    private long firsttime;//首次出现时间
    private long lasttime;//最近更新时间
    private int status; //活跃状态
    private String apBssid;//归属网络

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    @Override
    public int getItemType() {
        return 0;
    }


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


    public int getType() {
        return this.type;
    }


    public void setType(int type) {
        this.type = type;
    }


    public String getMac() {
        return this.mac;
    }


    public void setMac(String mac) {
        this.mac = mac;
    }


    public String getIp() {
        return this.ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }


    public String getDhcp() {
        return this.dhcp;
    }

    public void setDhcp(String dhcp) {
        this.dhcp = dhcp;
    }

    public String getBonjour() {
        return this.bonjour;
    }

    public void setBonjour(String bonjour) {
        this.bonjour = bonjour;
    }

    public String getNetbios() {
        return this.netbios;
    }

    public void setNetbios(String netbios) {
        this.netbios = netbios;
    }

    public String getBrand() {
        return this.brand;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getFirsttime() {
        return this.firsttime;
    }

    public void setFirsttime(long firsttime) {
        this.firsttime = firsttime;
    }

    public long getLasttime() {
        return this.lasttime;
    }

    public void setLasttime(long lasttime) {
        this.lasttime = lasttime;
    }


    public int getStatus() {
        return this.status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public String getApBssid() {
        return this.apBssid;
    }


    public void setApBssid(String apBssid) {
        this.apBssid = apBssid;
    }
}
