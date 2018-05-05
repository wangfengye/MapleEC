package com.ascend.wangfeng.wifimanage.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fengye on 2018/4/26.
 * email 1040441325@qq.com
 * 人员
 */
@Entity
public class Person {
    @Id
    private long id;
    private String name;
    private String imgUrl;
    private boolean sex;//true;man,false:woman
    @Generated(hash = 726698455)
    public Person(long id, String name, String imgUrl, boolean sex) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.sex = sex;
    }
    @Generated(hash = 1024547259)
    public Person() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public boolean getSex() {
        return this.sex;
    }
    public void setSex(boolean sex) {
        this.sex = sex;
    }

}
