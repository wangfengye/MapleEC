package com.ascend.wangfeng.locationby4g.delegates.imsi;

import com.ascend.wangfeng.locationby4g.services.bean.CellMeaureAck;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 */

public class CellMeaureAckEntry extends CellMeaureAck implements MultiItemEntity{
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_IMSI_RESULT = 1;
    // 是否监控
    private boolean isLocation = false;
    // 用于碰撞分析,记录多少文件采集到
    private int crashCounter = 1;
    private String fileName;
    private int type;
    @Override
    public int getItemType() {
        return type;
    }
    public void setItemType(int type){
        this.type = type;
    }

    public int getCrashCounter() {
        return crashCounter;
    }

    public void setCrashCounter(int crashCounter) {
        this.crashCounter = crashCounter;
    }
    public void addCrash() {
        this.crashCounter = crashCounter +1;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static CellMeaureAckEntry copyStatic(CellMeaureAck ack){
        CellMeaureAckEntry entry = new CellMeaureAckEntry();
        entry.setImsi(ack.getImsi());
        entry.setCarrier(ack.getCarrier());
        entry.setFieldIntensity(ack.getFieldIntensity());
        entry.setTimestamp(ack.getTimestamp());
        entry.setUpFieldIntensity(ack.getUpFieldIntensity());
        return entry;
    }
    public CellMeaureAckEntry copy(CellMeaureAck ack){
        if (ack.getTimestamp()<= this.getTimestamp()){
            //去除时间重复的数据
            return this;
        }
        this.addFieldIntensitys(this.getFieldIntensity());
        this.addTimeStamps(this.getTimestamp());
        this.setCarrier(ack.getCarrier());
        this.setFieldIntensity(ack.getFieldIntensity());
        this.setTimestamp(ack.getTimestamp());
        this.setUpFieldIntensity(ack.getUpFieldIntensity());
        return this;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }
}
