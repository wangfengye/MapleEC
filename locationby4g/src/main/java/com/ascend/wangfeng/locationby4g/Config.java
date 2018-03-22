package com.ascend.wangfeng.locationby4g;

import com.ascend.wangfeng.locationby4g.services.bean.CellSysAck;

import java.util.ArrayList;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 * 全局配置
 */

public class Config {
    public static final int POWER_HEIGHT = 10;
    public static final int POWER_MIDDLE = 5;
    public static final int POWER_LOW = 1;
    private int mPower = POWER_HEIGHT;
    private ArrayList<CellSysAck> mAcks = new ArrayList<>();
    public static Config getInstance(){
        return HOLDER.CONFIG;
    }

    public int getPower() {
        return mPower;
    }

    public void setPower(int power) {
        mPower = power;
    }

    public ArrayList<CellSysAck> getAcks() {
        return mAcks;
    }

    public void setAcks(ArrayList<CellSysAck> acks) {
        mAcks = acks;
    }

    private static class HOLDER{
        private static final Config CONFIG = new Config();
        static {
            CONFIG.mAcks.add(new CellSysAck("10.88.120.241",8888,"中国移动"));
            CONFIG.mAcks.add(new CellSysAck("10.88.120.242",8888,"中国电信"));
        }
    }
}
