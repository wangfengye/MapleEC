package com.ascend.wangfeng.wifimanage.net;

import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fengye on 2018/5/18.
 * email 1040441325@qq.com
 */

public interface AliApi {
    @GET("devices/curretn")
    Observable<Response<List<Device>>> getCurrentDevices();

    @GET("persons")
    Observable<Response<Person>> getPersonsById(@Query("id") Long id);
}
