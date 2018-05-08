package com.ascend.wangfeng.wifimanage.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.util.FileUtil;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Event;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.User;

import java.util.List;

/**
 * Created by fengye on 2018/5/8.
 * email 1040441325@qq.com
 */

public class DemoApi implements Api{
    @Override
    public void createAccount(User user, Callback<Boolean> callback) {

    }

    @Override
    public void login(String dId, String password, Callback<User> callback) {

    }

    @Override
    public void addDevice(Device device, Callback<Boolean> callback) {

    }

    @Override
    public void updateDevice(Device device, Callback<Boolean> callback) {

    }

    @Override
    public void getDevices(Callback<List<Device>> callback) {

    }

    @Override
    public void getCurrentDevices(Callback<List<Device>> callback) {
        String jsonStr = FileUtil.getRawFile(R.raw.current_devices);
        JSONObject object = JSONObject.parseObject(jsonStr);
        String array = object.getString("data");
        List<Device> devices = JSONArray.parseArray(array,Device.class);
        callback.callback(devices);
    }

    @Override
    public void addPerson(Person person, Callback<Boolean> callback) {

    }

    @Override
    public void updatePersion(Person person, Callback<Boolean> callback) {

    }

    @Override
    public void addRelation(long pId, long dId, Callback<Boolean> callback) {

    }

    @Override
    public void removeRelation(long id, Callback<Boolean> callback) {

    }

    @Override
    public void getEvents(int count, int page, long pId, long dId, Callback<List<Event>> callback) {

    }

    @Override
    public void getEvents(Callback<List<Event>> callback) {

    }

    @Override
    public void setBlock(long id, long starttime, long endtime, int[] cycle, Callback<Boolean> callback) {

    }
}
