package com.ascend.wangfeng.wifimanage.api;

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
 * Created by fengye on 2018/5/5.
 * email 1040441325@qq.com
 * 数据交互接口
 */

public interface Api {
    /**
     *  创建账号
     * @param callback 是否创建成功
     */
    void createAccount(User user,Callback<Response<User>> callback);

    /**
     * 登录
     * @param user 用户
     * @param callback user信息;
     */
    void login(User user,Callback<Response<User>> callback);

    /**
     * 上传设备信息;
     * @param device 设备
     *
     */
    void addDevice(Device device,Callback<Boolean> callback);

    /**
     * 更新设备信息;
     * @param device 设备
     * @param callback  是否成功
     */
    void updateDevice(Device device,Callback<Boolean> callback);

    /**
     *  获取 存储的设备信息
     * @param callback 设备列表
     */
    void getDevices(Callback<List<Device>> callback);
    /**
     *  从设备获取设备信息
     * @return 设备列表
     */
    void getCurrentDevices(Callback<List<Device>> callback);

    /**
     * 新增人员
     * @param person 人员信息
     * @param callback 是否成功
     */
    void addPerson(Person person,Callback<Boolean> callback);

    /**
     * 更新人员信息
     * @param person 人员信息
     * @param callback 是否成功
     */
    void updatePerson(Person person,Callback<Boolean> callback);

    void getPersons(Set<Long> i, Callback<List<Person>> callback);

    /**
     *  新增人员-设备关联
     * @param pId 人员id
     * @param dId 设备id
     * @param callback 是否成功
     */
    void addRelation(long pId, long dId,Callback<Boolean> callback);

    /**
     * 取消人员-设备关联
     * @param id 关系id
     * @param callback 是否成功
     */
    void removeRelation(long id, Callback<Boolean> callback);

    /**
     * 获取上网记录
     * @param count 每页条数
     * @param page 第几页
     * @param pId 人员id
     * @param dId 设备 id;
     * @param callback 事件记录集合
     */
    void getEvents(int count,int page,long pId,long dId,Callback<List<Event>> callback);
    void getEvents(Callback<List<Event>> callback);

    /**
     * 网路限制设置
     * @param callback 是否设置成功
     */
    void setBlock(Plan plan, Callback<Boolean> callback);

    void getActivities(long dId, long startDate, long endDate, Callback<List<Liveness>> callback);
}
