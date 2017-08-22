package com.ascend.wangfeng.latte.ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fengye on 2017/8/22.
 * email 1040441325@qq.com
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    private String account ;
    private String password;
    @Generated(hash = 602562219)
    public User(Long id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
