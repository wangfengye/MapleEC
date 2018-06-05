package com.ascend.wangfeng.wifimanage.bean;

/**
 * Created by fengye on 2018/5/5.
 * email 1040441325@qq.com
 * 用户(t_usr)
 */

public class User {
    private Long uid;// 设备编号
    private String umac;// 手机mac
    private String uname;//姓名
    private String umobile;// 手机
    private String upasswd;
    private Long bid;

    private Long bmac;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUmac() {
        return umac;
    }

    public void setUmac(String umac) {
        this.umac = umac;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUmobile() {
        return umobile;
    }

    public void setUmobile(String umobile) {
        this.umobile = umobile;
    }

    public String getUpasswd() {
        return upasswd;
    }

    public void setUpasswd(String upasswd) {
        this.upasswd = upasswd;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }


    public Long getBmac() {
        return bmac;
    }

    public void setBmac(Long bmac) {
        this.bmac = bmac;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", umac='" + umac + '\'' +
                ", uname='" + uname + '\'' +
                ", umobile='" + umobile + '\'' +
                ", upasswd='" + upasswd + '\'' +
                ", bid=" + bid +
                '}';
    }
}
