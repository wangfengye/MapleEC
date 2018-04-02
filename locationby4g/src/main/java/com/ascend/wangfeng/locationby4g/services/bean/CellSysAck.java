package com.ascend.wangfeng.locationby4g.services.bean;


import com.ascend.wangfeng.locationby4g.rxbus.RxBus;

import java.net.Socket;

/**
 * Created by fengye on 2018/3/19.
 * email 1040441325@qq.com
 */

public class CellSysAck {
    public static final int STATE_UNSYNC_UNACTIVE = 0;//未同步,未激活
    public static final int STATE_SYNC_ACTIVE = 1;//已同步,已激活
    public static final int STATE_SYNC_UNACTIVE = 2;//已同步,未激活
    public static final int STATE_UNSYNC_ACTIVE = 3;//未同步,已激活
    // 同步激活状态
    private int state;
    // rfo发送功率
    private String rf0TransmittedPower;
    // rf1发送功率
    private String rf1TransmittedPower;
    // 同步方式
    private String syncType;

    private String ip;
    private int port;
    private String title;
    private Socket mSocket;

    public Socket getSocket() {
        return mSocket;
    }

    /**
     * 特殊的显示样式
     * @return
     */
    public String[]  getArrayStr(){
        return new String[]{getIpStr(),getTitleStr(),getConnectStr(),getActiveStr(),getSyncStr(),getPowerStr()};
    }
    public String getIpStr() {
        return "IP地址: " + ip;
    }
    public String getTitleStr(){
        return "通信制式: " + title;
    }
    public String getConnectStr(){
        return "是否连接: " + ((mSocket!=null && mSocket.isConnected())?"是":"否");
    }
    public String getActiveStr(){
        if (state == STATE_SYNC_ACTIVE || state == STATE_UNSYNC_ACTIVE){
            return "是否激活: " + "是";
        }else {
            return  "是否激活: " + "否";
        }
    }
    public String getSyncStr(){
        if (state == STATE_SYNC_ACTIVE || state == STATE_SYNC_UNACTIVE){
            return "是否同步: " + "是";
        }else {
            return  "是否同步: " + "否";
        }
    }
    public String getPowerStr(){
        return "设备功率: " + rf0TransmittedPower;
    }
    public void setSocket(Socket socket) {
        mSocket = socket;
    }

    public CellSysAck() {
    }

    public CellSysAck(String ip, int port, String title) {
        this.ip = ip;
        this.port = port;
        this.title = title;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        RxBus.getDefault().post(this);
    }

    public String getRf0TransmittedPower() {
        return rf0TransmittedPower;
    }

    public void setRf0TransmittedPower(String rf0TransmittedPower) {
        this.rf0TransmittedPower = rf0TransmittedPower;
    }

    public String getRf1TransmittedPower() {
        return rf1TransmittedPower;
    }

    public void setRf1TransmittedPower(String rf1TransmittedPower) {
        this.rf1TransmittedPower = rf1TransmittedPower;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    @Override
    public String toString() {
        return "CellSysAck{" +
                "state=" + state +
                ", rf0TransmittedPower='" + rf0TransmittedPower + '\'' +
                ", rf1TransmittedPower='" + rf1TransmittedPower + '\'' +
                ", syncType='" + syncType + '\'' +
                '}';
    }
}
