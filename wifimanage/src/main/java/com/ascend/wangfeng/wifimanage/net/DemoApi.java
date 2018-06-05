package com.ascend.wangfeng.wifimanage.net;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ascend.wangfeng.latte.util.FileUtil;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Event;
import com.ascend.wangfeng.wifimanage.bean.Liveness;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Plan;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.bean.User;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by fengye on 2018/5/25.
 * email 1040441325@qq.com
 */

public class DemoApi implements AliApi {
    public DemoApi() {
    }

    @Override
    public Observable<Response<List<Device>>> getCurrentDevices() {
        return Observable.create(e -> {
            String jsonStr = FileUtil.getRawFile(R.raw.current_devices);
            Response<List<Device>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Device>>>() {
            });
            e.onNext(response);
        });

    }

    @Override
    public Observable<Response<List<Device>>> getTagDevices() {
        return Observable.create(e -> {
            String jsonStr = FileUtil.getRawFile(R.raw.devices);
            Response<List<Device>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Device>>>() {
            });
            e.onNext(response);
        });
    }

    @Override
    public Observable<Response<List<Device>>> getUnknownDevices() {
        return Observable.create(e->{
                String jsonStr = FileUtil.getRawFile(R.raw.unknown_devices);
                Response<List<Device>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Device>>>() {
                });
                e.onNext(response);
        });
    }

    @Override
    public Observable<Response<List<Device>>> getDeviecseWithPlan() {
        return Observable.create(e->{
                String jsonStr = FileUtil.getRawFile(R.raw.devices);
                Response<List<Device>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Device>>>() {
                });
                e.onNext(response);
        });
    }

    @Override
    public Observable<Response<List<Device>>> getDevicesByPId(Long id) {
        return Observable.create(e->{
                String jsonStr = FileUtil.getRawFile(R.raw.devices);
                Response<List<Device>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Device>>>() {
                });
                e.onNext(response);
        });
    }

    @Override
    public Observable<Response<Device>> addDevice(Device device) {
        return Observable.create(e->e.onError(new Exception()));
    }

    @Override
    public Observable<Response<Device>> updateDevice(Device device) {
        return Observable.create(e->e.onError(new Exception()));
    }


    @Override
    public Observable<Response<Person>> getPersonById(Long id) {
        return Observable.create(e->{
                String jsonStr = FileUtil.getRawFile(R.raw.persons_ids);
                Response<Person> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<Person>>() {
                });
                e.onNext(response);
        });
    }

    @Override
    public Observable<Response<Person>> getPersonWithAttention() {
        return Observable.create(e->{
            String jsonStr = FileUtil.getRawFile(R.raw.persons_ids);
            Response<Person> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<Person>>() {
            });
            e.onNext(response);
        });
    }

    @Override
    public Observable<Response<Person>> updatePersonWithAttention(Person person) {
        return Observable.create(e->e.onError(new Exception()));
    }

    @Override
    public Observable<Response<List<Person>>> getPersons() {

        return Observable.create(e->{
                String jsonStr = FileUtil.getRawFile(R.raw.persons);
                Response<List<Person>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Person>>>() {
                });
                e.onNext(response);
        });
    }

    @Override
    public Observable<Response<Person>> addPerson(Person person) {
        return Observable.create(e->e.onError(new Exception()));
    }

    @Override
    public Observable<Response<Person>> updatePerson(Person person) {
        return Observable.create(e->e.onError(new Exception()));
    }

    @Override
    public Observable<Response<List<Liveness>>> getLivenessesByPId(Long id) {
        return Observable.create(e->{
                String jsonStr = FileUtil.getRawFile(R.raw.livenesses_pid);
                Response<List<Liveness>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Liveness>>>() {
                });
                e.onNext(response);
        });
    }

    @Override
    public Observable<Response<List<Plan>>> getPlans() {
        return null;
    }

    @Override
    public Observable<Response<List<Plan>>> getPlansByDId(Long DId) {
        return Observable.create(e->{
                String jsonStr = FileUtil.getRawFile(R.raw.plans_did);
                Response<List<Plan>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Plan>>>() {
                });
                e.onNext(response);
        });
    }

    @Override
    public Observable<Response<Plan>> addPlan(Plan plan) {
        return Observable.create(e->e.onError(new Exception()));
    }

    @Override
    public Observable<Response<Plan>> updatePlan(Plan plan) {
        return Observable.create(e->e.onError(new Exception()));
    }

    @Override
    public Observable<Response<String>> deletePlan(Plan plan) {
        return Observable.create(e->e.onError(new Exception()));
    }

    @Override
    public Observable<Response<List<Event>>> getEvents(Long time) {
        return Observable.create(e->{
                String jsonStr = FileUtil.getRawFile(R.raw.event_history
                );
                Response<List<Event>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Event>>>() {
                });
                e.onNext(response);
        });
    }

    @Override
    public Observable<Response<User>> login(String mac, String password) {
        return Observable.create(e -> e.onError(new Exception()));
    }

    @Override
    public Observable<Response<User>> createUser(String mac, String password, double longitude, double latitude) {
        return Observable.create(e -> e.onError(new Exception()));
    }
}
