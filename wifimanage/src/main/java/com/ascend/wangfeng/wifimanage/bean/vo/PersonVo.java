package com.ascend.wangfeng.wifimanage.bean.vo;

import com.alibaba.fastjson.JSON;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 */

public class PersonVo extends Person implements MultiItemEntity,Serializable{
    private static final long serialVersionUID = -6663300915671491564L;
    private boolean mIsChecked;

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }

    @Override
    public int getItemType() {
        return 0;
    }
    public static PersonVo get(Person person){
        String json = JSON.toJSONString(person);
        PersonVo personVo = JSON.parseObject(json,PersonVo.class);
        return personVo;
    }
}
