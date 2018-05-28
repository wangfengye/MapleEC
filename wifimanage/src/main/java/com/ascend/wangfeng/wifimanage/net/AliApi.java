package com.ascend.wangfeng.wifimanage.net;

import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Liveness;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fengye on 2018/5/18.
 * email 1040441325@qq.com
 */

public interface AliApi {
    @GET("devices/curretn")
    Observable<Response<List<Device>>> getCurrentDevices();

    @GET("devices/pid")
    Observable<Response<List<Device>>> getDevicesByPId(@Query("id")Long id);

    @GET("person/{id}")
    Observable<Response<Person>> getPersonById(@Path("id") Long id);

    @GET("persons")
    Observable<Response<List<Person>>> getPersons();
    @POST("person/")
    Observable<Response<Person>> addPerson(@Body Person person);
    @PUT("person")
    Observable<Response<Person>> updatePerson(@Body Person person) ;

    @GET("liveness")
    Observable<Response<List<Liveness>>> getLivenessesByPId(Long id);

}
