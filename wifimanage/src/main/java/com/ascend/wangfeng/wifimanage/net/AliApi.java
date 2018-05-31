package com.ascend.wangfeng.wifimanage.net;

import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Event;
import com.ascend.wangfeng.wifimanage.bean.Liveness;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Plan;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    /**
     * 获取所有在线设备(lasttime<=15min)
     *
     * @return
     */
    @GET("devices/current")
    Observable<Response<List<Device>>> getCurrentDevices();

    /**
     * 获取所有 有主设备(即和person表关联)
     *
     * @return
     */
    @GET("devices/old")
    Observable<Response<List<Device>>> getTagDevices();

    /**
     * 获取 无主设备
     *
     * @return
     */
    @GET("devices/new")
    Observable<Response<List<Device>>> getUnknownDevices();

    /**
     * 有监管计划的所有设备
     *
     * @return
     */
    @GET("plans")
    Observable<Response<List<Device>>> getDeviecseWithPlan();

    /**
     * 获取 某人的所有设备
     *
     * @param id person_id
     * @return
     */
    @GET("devices/pid")
    Observable<Response<List<Device>>> getDevicesByPId(@Query("id") Long id);

    /**
     * 新增设备
     *
     * @param device
     * @return
     */
    @POST("devices")
    Observable<Response<Device>> addDevice(@Body Device device);

    /**
     * 更新设备
     *
     * @param device
     * @return
     */
    @PUT("devices")
    Observable<Response<Device>> updateDevice(@Body Device device);

    /**
     * 查询 person
     *
     * @param id peson_id
     * @return
     */
    @GET("person/{id}")
    Observable<Response<Person>> getPersonById(@Path("id") Long id);
    /**
     * 查询  关注的 person
     *
     * @return
     */
    @GET("person/attention")
    Observable<Response<Person>> getPersonWithAttention();

    /**
     * 查询所有 person
     *
  * @return
     */
    @GET("persons")
    Observable<Response<List<Person>>> getPersons();

    /**
     * 新增 person
     *
     * @param person
     * @return
     */
    @POST("person/")
    Observable<Response<Person>> addPerson(@Body Person person);

    /**
     * 更新person
     *
     * @param person
     * @return
     */
    @PUT("person")
    Observable<Response<Person>> updatePerson(@Body Person person);

    /**
     * 获取活跃度,最近一周(维度:person,同一person下多设备,去最高值)
     *
     * @param id 人员id
     * @return
     */
    @GET("liveness")
    Observable<Response<List<Liveness>>> getLivenessesByPId(Long id);

    /**
     * 查询所有plan
     *
     * @return
     */

    @GET("plans")
    Observable<Response<List<Plan>>> getPlans();

    /**
     * 查询某设备相关plans
     *
     * @param DId: 设备id
     * @return
     */
    @GET("plans")
    Observable<Response<List<Plan>>> getPlansByDId(@Query("dId") Long DId);

    /**
     * 新增plan
     *
     * @param plan
     * @return
     */
    @POST("plans")
    Observable<Response<Plan>> addPlan(@Body Plan plan);

    /**
     * 更新plan
     *
     * @param plan
     * @return
     */
    @PUT("plans")
    Observable<Response<Plan>> updatePlan(@Body Plan plan);

    /**
     * 删除 plan
     *
     * @param plan
     * @return
     */
    @PUT("plans")
    Observable<Response<String>> deletePlan(@Body Plan plan);

    /**
     * 获取当天的日志
     *
     * @param time 当天凌晨的时间戳(秒级)
     * @return
     */
    @GET("events")
    Observable<Response<List<Event>>> getEvents(@Query("time") Long time);

    /**
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<Response<String>> login(@Field("mac") String mac, @Field("password") String password);

    /**
     * 创建用户
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user")
    Observable<Response<User>> createUser(@Field("mac") String mac,@Field("password") String password
            , @Field("longitude") double longitude, @Field("latitude") double latitude);

}
