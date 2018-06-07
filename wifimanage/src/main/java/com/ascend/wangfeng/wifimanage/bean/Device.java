package com.ascend.wangfeng.wifimanage.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 设备表(b_dev)
 */
public class Device implements MultiItemEntity, Serializable {
    private static final long serialVersionUID = -1251039796477336552L;

    private Long did;
    private String dname;
    private int dtype;
    private Long dmac;
    private Long bmac;
    private Long pid;
    private String oui;
    private String hostname;
    private String vendor;
    private String netbios;
    private String devIp;
    private Long firsttime;
    private Long lasttime;
    // vo
    private Person person;
    private boolean online;
    private boolean selected;

    @Override
    public int getItemType() {
        return 0;
    }
    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getOui() {
        return oui;
    }

    public void setOui(String oui) {
        this.oui = oui;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getNetbios() {
        return netbios;
    }

    public void setNetbios(String netbios) {
        this.netbios = netbios;
    }

    public String getDevIp() {
        return devIp;
    }

    public void setDevIp(String devIp) {
        this.devIp = devIp;
    }

    public Long getFirsttime() {
        return firsttime;
    }

    public void setFirsttime(Long firsttime) {
        this.firsttime = firsttime;
    }

    public Long getLasttime() {
        return lasttime;
    }

    public void setLasttime(Long lasttime) {
        this.lasttime = lasttime;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public int getDtype() {
        return dtype;
    }

    public void setDtype(int dtype) {
        this.dtype = dtype;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Device{" +
                "did=" + did +
                ", dname='" + dname + '\'' +
                ", dtype='" + dtype + '\'' +
                ", dmac='" + dmac + '\'' +
                ", bmac=" + bmac +
                ", pid=" + pid +
                ", oui='" + oui + '\'' +
                ", hostname='" + hostname + '\'' +
                ", vendor='" + vendor + '\'' +
                ", netbios='" + netbios + '\'' +
                ", devIp='" + devIp + '\'' +
                ", firsttime=" + firsttime +
                ", lasttime=" + lasttime +
                '}';
    }
}
