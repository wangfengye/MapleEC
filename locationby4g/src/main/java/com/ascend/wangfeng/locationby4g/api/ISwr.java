package com.ascend.wangfeng.locationby4g.api;

/**
 * Created by fengye on 2018/3/20.
 * email 1040441325@qq.com
 * 命令接口
 */

public interface ISwr {
    /**
     * 获取侦码状态
     */
    void getEquimentState();
    /**
     * 侦码重启
     */
    void restart();

    /**
     * 侦码扫描
     */
    void scan();

    /**
     * 设置侦码功率
     * @param type
     */
    void setPower(int type);

    /**
     * 模式切换
     * @param type
     */
    void setMode(int type);

    /**
     * 开启
     */
    void start();

    /**
     * 停止
     */
    void stop();
}
