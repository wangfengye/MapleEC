package com.ascend.wangfeng.wifimanage.bean.vo;

import com.alibaba.fastjson.JSON;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Event;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by fengye on 2018/5/16.
 * email 1040441325@qq.com
 */

public class EventVo extends Event implements MultiItemEntity {
    private Person mPerson;
    private Device mDevice;

    public Person getPerson() {
      /*  if (mPerson == null) {
            PersonDao dao = MainApp.getContent().getDaoSession().getPersonDao();
            mPerson = dao.load(getPId());
        }*/
        return mPerson;
    }

    public Device getDevice() {
    /*    if (mDevice == null) {
            DeviceDao dao = MainApp.getContent().getDaoSession().getDeviceDao();
            mDevice = dao.load(getDId());
        }*/
        return mDevice;
    }

    public String getEventStr() {
        switch (getEvent()) {
            case 1:
                return "上线";
            case 0:
                return "下线";
            default:
                return "未知";
        }
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public static EventVo getVo(Event event) {
        String json = JSON.toJSONString(event);
        EventVo eventVo = JSON.parseObject(json, EventVo.class);
        return eventVo;
    }
}
