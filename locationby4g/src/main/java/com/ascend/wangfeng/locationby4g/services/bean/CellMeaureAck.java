package com.ascend.wangfeng.locationby4g.services.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengye on 2018/3/19.
 * email 1040441325@qq.com
 *  Imsi数据
 */

public class CellMeaureAck {
    public final static int CARRIER_RF0 = 1;
    public final static int CARRIER_RF1 = 2;
    public final static int CARRIER_NULL = 3;
    private String imsi;
    // 场强
    private int fieldIntensity;
    // 时间戳
    private long timestamp;
    // 载波信息
    private int carrier;
    // 上行场强
    private int upFieldIntensity;
    // 采集的历史信号强度
    private List<Long> timeStamps = new ArrayList<>();
    private List<Integer> fieldIntensitys = new ArrayList<>();

    public CellMeaureAck() {
    }


    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public int getFieldIntensity() {
        return fieldIntensity;
    }

    public void setFieldIntensity(int fieldIntensity) {
        this.fieldIntensity = fieldIntensity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getCarrier() {
        return carrier;
    }

    public void setCarrier(int carrier) {
        this.carrier = carrier;
    }

    public int getUpFieldIntensity() {
        return upFieldIntensity;
    }

    public void setUpFieldIntensity(int upFieldIntensity) {
        this.upFieldIntensity = upFieldIntensity;
    }

    public List<Long> getTimeStamps() {
        return timeStamps;
    }

    public void addTimeStamps(long timestamp) {
        if (this.timeStamps == null){
            this.timeStamps = new ArrayList();
        }
        timeStamps.add(timestamp);
    }

    public List<Integer> getFieldIntensitys() {
        return fieldIntensitys;
    }

    public void addFieldIntensitys(int fieldIntensity) {
        if (this.fieldIntensitys == null){
            this.fieldIntensitys = new ArrayList<>();
        }
        this.fieldIntensitys.add(fieldIntensity);
    }

    @Override
    public String toString() {
        return "CellMeaureAck{" +
                "imsi='" + imsi + '\'' +
                ", fieldIntensity=" + fieldIntensity +
                ", timestamp=" + timestamp +
                ", carrier=" + carrier +
                ", upFieldIntensity=" + upFieldIntensity +
                '}';
    }
}
