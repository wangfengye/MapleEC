package com.ascend.wangfeng.wifimanage.net;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ascend.wangfeng.latte.util.FileUtil;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by fengye on 2018/5/25.
 * email 1040441325@qq.com
 */

public class DemoApi implements AliApi{
    @Override
    public Observable<Response<List<Device>>> getCurrentDevices() {
        return Observable.create(new ObservableOnSubscribe<Response<List<Device>>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<List<Device>>> e) throws Exception {
                String jsonStr = FileUtil.getRawFile(R.raw.current_devices);
                Response<List<Device>> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<List<Device>>>() {
                });
                e.onNext(response);
            }
        });

    }

    @Override
    public Observable<Response<Person>> getPersonsById(Long id) {
        return Observable.create(new ObservableOnSubscribe<Response<Person>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<Person>> e) throws Exception {
                String jsonStr = FileUtil.getRawFile(R.raw.persons_ids);
                Response<Person> response = JSONObject.parseObject(jsonStr, new TypeReference<Response<Person>>() {
                });
                e.onNext(response);
            }
        });
    }
}
