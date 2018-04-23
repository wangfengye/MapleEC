package com.ascend.wangfeng.locationby4g.services;

import android.util.Log;

import com.ascend.wangfeng.locationby4g.MainApp;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.rxbus.RxBus;
import com.ascend.wangfeng.locationby4g.services.bean.CellMeaureAck;
import com.ascend.wangfeng.locationby4g.services.bean.CellSysAck;
import com.ascend.wangfeng.locationby4g.services.bean.Metrocell;
import com.ascend.wangfeng.locationby4g.services.rxbus.CellMeaureAckEvent;
import com.ascend.wangfeng.locationby4g.services.rxbus.MetrocellEvent;
import com.ascend.wangfeng.locationby4g.util.ArrayUtil;
import com.ascend.wangfeng.locationby4g.util.SwrUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by fengye on 2018/3/27.
 * email 1040441325@qq.com
 * socket 长连接(包含数据解析)
 */

public class SocketClient implements Runnable {
    public static final String TAG = SocketClient.class.getSimpleName();
    public static final int CONNECT_PERIOD = 1000;
    public static final int HEART_PERIOD = 3000;
    private static final int CONNECT_TIME_OUT = 10 * 1000;
    private static final int CONNECT_TRY_TIMES = 3;// 重连次数
    private CellSysAck mCellSysAck;
    private int mConnectTimes = 0;
    private Socket mSocket;
    private InetSocketAddress mAddress;
    private HeartBeatTask mTask;
    private ScheduledExecutorService mPool;


    public SocketClient(CellSysAck cellSysAck) {
        mCellSysAck = cellSysAck;
    }

    @Override
    public void run() {
        mAddress = new InetSocketAddress(mCellSysAck.getIp(), mCellSysAck.getPort());
        while (mConnectTimes < CONNECT_TRY_TIMES) {
            connect();
            if (!mSocket.isConnected()){
                mConnectTimes = 0;
                break;
            }else {
                mConnectTimes ++;
                sleep(CONNECT_PERIOD);
            }
        }
        if (mSocket != null && mSocket.isConnected()) {
            keepHeartBeat();
            reciceData();
        }else {
            MainApp.toast(R.string.unconnected);
        }
    }

    /**
     * 心跳保活
     */
    private void keepHeartBeat() {
        if (mTask == null) {
            mTask = new HeartBeatTask();
        }
        if (mPool != null) {
            mPool.shutdown();
            mPool = null;
        }
        mPool = Executors.newScheduledThreadPool(1);
        mPool.scheduleAtFixedRate(mTask, 1000, HEART_PERIOD, TimeUnit.MILLISECONDS);
    }

    /**
     * 数据解析
     */
    private void reciceData() {
        while (mSocket != null && mSocket.isConnected()) {
            try {
                DataInputStream in = new DataInputStream(mSocket.getInputStream());
                ArrayList<String> response = new ArrayList<>();
                byte[] buffer = new byte[in.available()];
                if (in.read(buffer) > 0) {
                    for (int i = 0; i < buffer.length; i++) {
                        response.add(Integer.toHexString(buffer[i] & 0xFF));
                    }
                }
                analyseData(response, mCellSysAck);
            } catch (IOException e) {

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }

    }

    /**
     * 校验并解析数据
     *
     * @param data 源数据
     * @throws Exception 数据完整性校验异常
     */
    public void analyseData(List<String> data, CellSysAck ack) throws Exception {
        while (data.size() > 0) {
            // Log.i(TAG, "analyseData: "+data.toString());
            // 读取数据时会将已读取的删除,因此需要一开始就获取长度
            int lengthActual = data.size();
            if (lengthActual < 8) {
                throw new Exception("接收数据长度异常");
            }
            // 数据类型
            int id = SwrUtil.hex2Int(data, 4);
            int length = SwrUtil.hex2Int(data, 4);
            // 长度验证
            List<String> data1 = null;
            if (lengthActual < length) {
                throw new Exception("接收数据长度异常" + lengthActual + "---" + length);
            } else if (lengthActual == length) {
                data1 = ArrayUtil.copy(data);
                data.clear();
            } else {
                data1 = ArrayUtil.copy(data, 0, length - 8);
                data = ArrayUtil.copy(data, length - 8, data.size());
            }
            // 解析数据
            switch (id) {
                case 8:
                    getCellMeasureAck(data1);
                    break;
                case 13:
                    getCellSynAck(data1, ack);
                    break;
                case 43:
                    getCellScanAck(data1);
                    break;
                default:

                    break;

            }
        }
    }

    /**
     * 解析上报数据
     *
     * @param data
     */
    private static void getCellScanAck(List<String> data) {
        int length = SwrUtil.hex2Int(data);
        ArrayList<Metrocell> metrocells = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Metrocell metrocell = new Metrocell();
            int frequencyPoint = SwrUtil.hex2Int(data, 4);
            metrocell.setFrequencyPoint(frequencyPoint);
            int pci = SwrUtil.hex2Int(data, 2);
            metrocell.setPci(pci);
            int cid = SwrUtil.hex2Int(data, 4);
            metrocell.setCid(cid);
            int intensity = SwrUtil.hex2Int(data, 2);
            metrocell.setIntensity(intensity);
            int tac = SwrUtil.hex2Int(data, 2);
            metrocell.setTac(tac);
            int plmnid = SwrUtil.hex2Int(data, 2);
            metrocell.setPlmnid(plmnid);
            int sa = SwrUtil.hex2Int(data);
            metrocell.setSa(sa);
            int sp = SwrUtil.hex2Int(data);
            metrocell.setSp(sp);
            int priority = SwrUtil.hex2Int(data);
            metrocell.setPriority(priority);
            int hydt = SwrUtil.hex2Int(data);
            metrocell.setHydt(hydt);
            int pilotFrequencyThreshold = SwrUtil.hex2Int(data);
            metrocell.setPilotFrequencyThreshold(pilotFrequencyThreshold);
            int sameFrequencyThreshold = SwrUtil.hex2Int(data);
            metrocell.setSameFrequencyThreshold(sameFrequencyThreshold);
            int min = SwrUtil.hex2Int(data);
            metrocell.setMinElectricalLevel(min);
            int reChooseTimer = SwrUtil.hex2Int(data);
            metrocell.setReChooseTimer(reChooseTimer);
            int adjacentRegionCount = SwrUtil.hex2Int(data);
            metrocell.setAdjacentRegionCount(adjacentRegionCount);
            metrocells.add(metrocell);
        }
        RxBus.getDefault().post(new MetrocellEvent(metrocells));
        return;
    }

    /**
     * 查询设备状态返回数据
     *
     * @param data
     */
    private static void getCellSynAck(List<String> data, CellSysAck ack) {
        int state = SwrUtil.hex2Int(data);
        ack.setState(state);
        String rf0 = SwrUtil.hex2String(data, 10);
        ack.setRf0TransmittedPower(rf0);
        String rf1 = SwrUtil.hex2String(data, 10);
        ack.setRf1TransmittedPower(rf1);
        String syncType = SwrUtil.hex2String(data, 25);
        ack.setSyncType(syncType);
        Log.i(TAG, "getCellSynAck: " + ack.toString());
    }

    /**
     * 采集到的imsi数据
     *
     * @param data
     */
    private void getCellMeasureAck(List<String> data) {

        CellMeaureAck ack = new CellMeaureAck();
        String imsi = SwrUtil.hex2Imsi(data);
        ack.setImsi(imsi);
        int intensity = SwrUtil.hex2Intensity(data);
        ack.setFieldIntensity(intensity);
        long timestamp = SwrUtil.hex2Int(data, 4);
        ack.setTimestamp(timestamp);
        int carrier = SwrUtil.hex2Int(data);
        ack.setCarrier(carrier);
        if (data.size() >= 2) {
            int upIntensity = SwrUtil.hex2Intensity(data);
            ack.setUpFieldIntensity(upIntensity);
        }
        Log.i(TAG, "getCellMeasureAck: " + ack);
        RxBus.getDefault().post(new CellMeaureAckEvent(ack));
    }

    /**
     * 查询设备运行状态
     */
    private void connect() {
        if (mSocket == null) {
            try {
                mSocket = new Socket();
                mCellSysAck.setSocket(mSocket);
                mSocket.connect(mAddress, CONNECT_TIME_OUT);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "connect: mCellSysAck.getIp() ", e);
            }
        }
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 心跳检测
    class HeartBeatTask implements Runnable {

        @Override
        public void run() {
            try {
                mSocket.sendUrgentData(65);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    mSocket.close();
                    mSocket = null;
                    connect();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
