package com.ascend.wangfeng.wifimanage.bean;

/**
 * Created by fengye on 2018/5/5.
 * email 1040441325@qq.com
 * 用户
 */

public class User {
    private Long id;// 设备编号
    private String password;
    private String name;//姓名
    private String icon;// 头像url
    private Long bId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getbId() {
        return bId;
    }

    public void setbId(Long bId) {
        this.bId = bId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", bId=" + bId +
                '}';
    }
}
