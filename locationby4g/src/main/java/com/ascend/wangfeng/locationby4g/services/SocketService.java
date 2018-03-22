package com.ascend.wangfeng.locationby4g.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ascend.wangfeng.latte.net.rx.RxBus;
import com.ascend.wangfeng.locationby4g.Config;
import com.ascend.wangfeng.locationby4g.api.ISwr;
import com.ascend.wangfeng.locationby4g.services.bean.CellCmd;
import com.ascend.wangfeng.locationby4g.services.bean.CellMeaureAck;
import com.ascend.wangfeng.locationby4g.services.bean.CellSysAck;
import com.ascend.wangfeng.locationby4g.services.bean.Metrocell;
import com.ascend.wangfeng.locationby4g.services.rxbus.BaseObserver;
import com.ascend.wangfeng.locationby4g.services.rxbus.CMDEvent;
import com.ascend.wangfeng.locationby4g.services.rxbus.CellMeaureAckEvent;
import com.ascend.wangfeng.locationby4g.services.rxbus.MetrocellEvent;
import com.ascend.wangfeng.locationby4g.util.ArrayUtil;
import com.ascend.wangfeng.locationby4g.util.StringUtil;
import com.ascend.wangfeng.locationby4g.util.SwrUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by fengye on 2018/3/8.
 * email 1040441325@qq.com
 */

/**
 * .::::.
 * .::::::::.
 * :::::::::::  FUCK YOU
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 */
public class SocketService extends Service implements ISwr{

    public static final String TAG = SocketService.class.getSimpleName();
    private ExecutorService mThreadPool;
    private ArrayList<CellSysAck> mAcks;
    private SocketBinder mBinder = new SocketBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void getEquimentState() {
        setCellRstCmd();
    }

    @Override
    public void restart() {

    }

    @Override
    public void scan() {

    }

    @Override
    public void setPower(int type) {

    }

    @Override
    public void setMode(int type) {

    }

    @Override
    public void start() {
        // 需要同时设置RF0和RF1的发射功率,RF0类型为5，RF1类型为19
        setCellPwrAdjCmd(5,Config.getInstance().getPower());
        setCellPwrAdjCmd(19,Config.getInstance().getPower());
        Toast.makeText(this, "start-ser", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stop() {
        setCellPwrAdjCmd(5,0);
        setCellPwrAdjCmd(19,0);
    }

    public class SocketBinder extends Binder{
        public SocketService getService(){
            return SocketService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(SocketService.this, "service_start", Toast.LENGTH_LONG).show();
        mThreadPool = Executors.newCachedThreadPool();
        mAcks = Config.getInstance().getAcks();
        for (CellSysAck ack : mAcks) {
            initSocket(ack);
        }
        initRxBus();
    }

    private void initRxBus() {
        RxBus.getDefault().toObservable(CMDEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CMDEvent>() {
                    @Override
                    public void onNext(CMDEvent event) {
                        switch (event.getCmd()) {
                            case CMDEvent.SEND_DATA:
                                break;
                            case CMDEvent.SHOW_DATA:
                                Toast.makeText(SocketService.this, event.getContent(), Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
    }

    private void initSocket(final CellSysAck cellSysAck) {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(cellSysAck.getIp(), cellSysAck.getPort());
                    cellSysAck.setSocket(socket);
                    while (true) {
                        Thread.sleep(100);
                        ArrayList<String> data = readData(cellSysAck);
                        if (data != null && data.size() > 0) {
                            analyseData(data, cellSysAck);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void writeBytes(final byte[] s) {

        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for (CellSysAck ack : mAcks) {
                        DataOutputStream out = new DataOutputStream(ack.getSocket().getOutputStream());
                        out.write(s);
                        out.flush();

                    }
                } catch (IOException e) {
                    Log.e(TAG, "writeBytes: ", e);
                    e.printStackTrace();
                }
            }
        });

    }

    private void writeData(String data) {

        writeBytes(StringUtil.hexString2Bytes(data));
    }

    private ArrayList<String> readData(CellSysAck ack) throws IOException {
        DataInputStream in = new DataInputStream(ack.getSocket().getInputStream());
        ArrayList<String> response = new ArrayList<>();
        try {
            byte[] buffer = new byte[in.available()];
            if (in.read(buffer) > 0) {
                for (int i = 0; i < buffer.length; i++) {
                    response.add(Integer.toHexString(buffer[i] & 0xFF));
                }
            }
            return response;
        } catch (IOException e) {
            Log.e(TAG, "readData: ", e);
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 校验并解析数据
     *
     * @param data 源数据
     * @throws Exception 数据完整性校验异常
     */
    public static void analyseData(List<String> data, CellSysAck ack) throws Exception {
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
    private static void getCellMeasureAck(List<String> data) {
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
        RxBus.getDefault().post(new CellMeaureAckEvent(ack));
        Log.i(TAG, "getCellMeasureAck: " + ack);
    }

    /**
     * 查询设备运行状态
     */
    private void setCellSynReq() {
        writeData(CellCmd.build(12).toString());
    }

    /**
     * 开启侦测 命令
     *
     * @param type     rfo:5,rf1:19
     * @param idensity 功率
     */
    private void setCellPwrAdjCmd(int type, int idensity) {
        writeData(CellCmd.build(type).addBodyInt(idensity).toString());
    }

    /**
     * 复位
     */
    private void setCellRstCmd() {
        writeData(CellCmd.build(6).toString());
    }

    /**
     * 定位目标
     *
     * @param imsi
     * @param type
     */
    private void setCellNumListStopTCmd(String imsi, int type) {
        CellCmd.build(47).addBodyInt(1);
    }


}
