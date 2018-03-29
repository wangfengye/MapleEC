package com.ascend.wangfeng.locationby4g.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ascend.wangfeng.locationby4g.Config;
import com.ascend.wangfeng.locationby4g.api.ISwr;
import com.ascend.wangfeng.locationby4g.delegates.imsi.CellMeaureAckEntry;
import com.ascend.wangfeng.locationby4g.rxbus.RxBus;
import com.ascend.wangfeng.locationby4g.rxbus.Subscribe;
import com.ascend.wangfeng.locationby4g.services.bean.CellCmd;
import com.ascend.wangfeng.locationby4g.services.bean.CellMeaureAck;
import com.ascend.wangfeng.locationby4g.services.bean.CellSysAck;
import com.ascend.wangfeng.locationby4g.services.rxbus.CellMeaureAckEvent;
import com.ascend.wangfeng.locationby4g.services.rxbus.CellMeaureAckListEvent;
import com.ascend.wangfeng.locationby4g.util.StringUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fengye on 2018/3/27.
 * email 1040441325@qq.com
 */

public class SocketService2 extends Service implements ISwr {
    public static final String TAG = SocketService.class.getSimpleName();
    private ArrayList<CellSysAck> mAcks;
    private SocketService2.SocketBinder mBinder = new SocketService2.SocketBinder();
    private ExecutorService mPool;
    public ArrayList<CellMeaureAckEntry> mEntries;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void getEquimentState() {
        writeData(CellCmd.setCellSynReq());
    }

    @Override
    public void restart() {
        writeData(CellCmd.setCellRstCmd());
    }

    @Override
    public void scan() {
        Toast.makeText(this, "等待实现", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPower(int type) {
        // 需要同时设置RF0和RF1的发射功率,RF0类型为5，RF1类型为19
        writeData(CellCmd.setCellPwrAdjCmd(5, Config.getInstance().getPower()));
        writeData(CellCmd.setCellPwrAdjCmd(19, Config.getInstance().getPower()));
    }

    @Override
    public void setMode(int type) {
        Toast.makeText(this, "等待实现", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void start() {
        writeData(CellCmd.setCellPwrAdjCmd(5, Config.getInstance().getPower()));
        writeData(CellCmd.setCellPwrAdjCmd(19, Config.getInstance().getPower()));
    }

    @Override
    public void stop() {
        writeData(CellCmd.setCellPwrAdjCmd(5, 0));
        writeData(CellCmd.setCellPwrAdjCmd(19, 0));
    }

    @Override
    public void locaion(String imsi) {
        writeData(CellCmd.setCellNumListStopTCmd(imsi, 2));
    }

    @Override
    public void unLocation(String imsi) {
        writeData(CellCmd.setCellNumListStopTCmd(imsi, 1));
    }

    public class SocketBinder extends Binder {
        public SocketService2 getService() {
            return SocketService2.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RxBus.getDefault().register(this);
        init();
    }

    private void init() {
        mEntries = new ArrayList<>();
        mPool = Executors.newCachedThreadPool();
        mAcks = Config.getInstance().getAcks();
        for (CellSysAck ack : mAcks) {
            mPool.execute(new SocketClient(ack));
        }
        initRxbus();
    }
    @Subscribe
    public void receicve(CellMeaureAckEvent event){
        CellMeaureAck ack = event.getAck();
        for (int i = 0; i < mEntries.size(); i++) {
            if (mEntries.get(i).getImsi().equals(ack.getImsi())) {
                if (ack.getTimestamp() <= mEntries.get(i).getTimestamp()) {
                    return;
                }
                mEntries.get(i).copy(ack);
                RxBus.getDefault().post(new CellMeaureAckListEvent(mEntries));
                return;
            }
        }
        mEntries.add(CellMeaureAckEntry.copyStatic(ack));
        Log.i(TAG, "getCellMeasureAck: " + ack);
        RxBus.getDefault().post(new CellMeaureAckListEvent(mEntries));
    }

    private void initRxbus() {
    }

    public void writeData(final String data) {
        mPool.execute(new Runnable() {
            @Override
            public void run() {
                for (CellSysAck sysAck : Config.getInstance().getAcks()) {
                    if (sysAck.getSocket() != null && sysAck.getSocket().isConnected()) {
                        try {
                            DataOutputStream out = new DataOutputStream(sysAck.getSocket().getOutputStream());
                            out.write(StringUtil.hexString2Bytes(data));
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.e(TAG, "writeData: ");
                    }
            }}
        });
    }
    private void showDialog(){

    }
    @Override
    public void onDestroy() {
        RxBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
