package com.ascend.wangfeng.wifimanage.bean;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 人员
 */

public class Person {

    private Long id;
    private String name;
    private int imgUrl;
    private boolean sex;//true;man,false:woman

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



}
