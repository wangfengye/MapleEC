package com.ascend.wangfeng.wifimanage.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ascend.wangfeng.latte.util.FileUtil;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Event;
import com.ascend.wangfeng.wifimanage.bean.Liveness;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Plan;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.bean.User;

import java.util.List;
import java.util.Set;


/**
 * Created by fengye on 2018/5/8.
 * email 1040441325@qq.com
 */

public class DemoApi implements Api {
    @Override
    public void createAccount(User user, Callback<Response<User>> callback) {
        String jsonStr = FileUtil.getRawFile(R.raw.user);
        Response<User> userResponse = JSONObject.parseObject(jsonStr, new TypeReference<Response<User>>() {
        });
        callback.callback(userResponse);
    }

    @Override
    public void login(User user, Callback<Response<User>> callback) {

    }

    @Override
    public void addDevice(Device device, Callback<Boolean> callback) {

    }


    @Override
    public void updateDevice(Device device, Callback<Boolean> callback) {
        MainApp.toast(R.string.demo_hint);
    }

    @Override
    public void getDevices(Callback<List<Device>> callback) {

    }

    @Override
    public void getCurrentDevices(Callback<List<Device>> callback) {
        String jsonStr = FileUtil.getRawFile(R.raw.current_devices);
        Response<List<Device>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Device>>>() {
        });
        callback.callback(response.getData());
    }

    @Override
    public void addPerson(Person person, Callback<Boolean> callback) {
        MainApp.toast(R.string.demo_hint);
    }

    @Override
    public void updatePerson(Person person, Callback<Boolean> callback) {
        MainApp.toast(R.string.demo_hint);
    }

    @Override
    public void getPersons(Set<Long> ids, Callback<List<Person>> callback) {
        String jsonStr = FileUtil.getRawFile(R.raw.persons_ids);
        Response<List<Person>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Person>>>() {
        });
        callback.callback(response.getData());
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
        String jsonStr = FileUtil.getRawFile(R.raw.event_history);
        JSONObject object = JSONObject.parseObject(jsonStr);
        String array = object.getString("data");
        List<Event> events = JSONArray.parseArray(array, Event.class);
        callback.callback(events);
    }

    @Override
    public void setBlock(Plan plan, Callback<Boolean> callback) {

    }

    @Override
    public void getActivities(long dId, long startDate, long endDate, Callback<List<Liveness>> callback) {

    }

}
