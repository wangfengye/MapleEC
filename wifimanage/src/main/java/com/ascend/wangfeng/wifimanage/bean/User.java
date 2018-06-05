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
    private Long bid2; // 预留bid;
    private Long bid3;

    private String bmac;// vo box mac

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

    public Long getBid2() {
        return bid2;
    }

    public void setBid2(Long bid2) {
        this.bid2 = bid2;
    }

    public Long getBid3() {
        return bid3;
    }

    public void setBid3(Long bid3) {
        this.bid3 = bid3;
    }

    public String getBmac() {
        return bmac;
    }

    public void setBmac(String bmac) {
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
                ", bid2=" + bid2 +
                ", bid3=" + bid3 +
                '}';
    }
}
