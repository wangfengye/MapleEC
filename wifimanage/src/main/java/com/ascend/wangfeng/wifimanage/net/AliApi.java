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
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @GET("dev/online")
    Observable<Response<List<Device>>> getCurrentDevices();

    /**
     * 获取所有 有主设备(即和person表关联)
     *
     * @return
     */
    @GET("dev/controlled")
    Observable<Response<List<Device>>> getTagDevices();

    /**
     * 获取 无主设备
     *
     * @return
     */
    @GET("dev/uncontrolled")
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
    @GET("dev/pid")
    Observable<Response<List<Device>>> getDevicesByPId(@Query("pid") Long id);

    /**
     * 新增设备
     *
     * @param device
     * @return
     */
    @POST("dev/save")
    Observable<Response<Device>> addDevice(@Body Device device);

    /**
     * 更新设备
     *
     * @param device
     * @return
     */
    @PUT("dev/update")
    Observable<Response<Device>> updateDevice(@Body Device device);

    /**
     * 查询 person
     *
     * @param id peson_id
     * @return
     */
    @GET("person")
    Observable<Response<Person>> getPersonById(@Query("pid") Long id);
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
    @GET("person/findAll")
    Observable<Response<List<Person>>> getPersons();

    /**
     * 新增 person
     *
     * @param person
     * @return
     */
    @POST("person/save")
    Observable<Response<Person>> addPerson(@Body Person person);

    /**
     * 更新person
     *
     * @param person
     * @return
     */
    @PUT("person/update")
    Observable<Response<Person>> updatePerson(@Body Person person);

    /**
     * 获取活跃度,最近一周(维度:person,同一person下多设备,去最高值)
     *
     * @param pid 人员id
     * @return
     */
    @GET("activity/lastweek")
    Observable<Response<List<Liveness>>> getLivenessesByPId(@Query("pid") Long pid);

    /**
     * 查询所有plan
     *
     * @return
     */
    @Deprecated
    @GET("plans")
    Observable<Response<List<Plan>>> getPlans();

    /**
     * 查询某设备相关plans
     *
     * @param DId: 设备id
     * @return
     */
    @GET("plan/device")
    Observable<Response<List<Plan>>> getPlansByDId(@Query("did") Long DId);

    /**
     * 新增plan
     *
     * @param plan
     * @return
     */
    @POST("plan/save")
    Observable<Response<Plan>> addPlan(@Body Plan plan);

    /**
     * 更新plan
     *
     * @param plan
     * @return
     */
    @PUT("plan/update")
    Observable<Response<Plan>> updatePlan(@Body Plan plan);

    /**
     * 删除 plan
     *
     * @param plan
     * @return
     */
    @DELETE("plan/del")
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
    Observable<Response<User>> login(@Field("mac") String mac, @Field("upasswd") String password);

    /**
     * 创建用户
     *
     * @return
     */
    @FormUrlEncoded
    @POST("usr/create")
    Observable<Response<User>> createUser(@Field("mac") String mac,@Field("upasswd") String password
            , @Field("blng") double longitude, @Field("blat") double latitude);

}
