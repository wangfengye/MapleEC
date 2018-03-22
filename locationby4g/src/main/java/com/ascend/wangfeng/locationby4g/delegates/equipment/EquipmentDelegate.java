package com.ascend.wangfeng.locationby4g.delegates.equipment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.locationby4g.Config;
import com.ascend.wangfeng.locationby4g.MainActivity;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.api.ISwr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by fengye on 2018/3/20.
 * email 1040441325@qq.com
 */

public class EquipmentDelegate extends BottomItemDelegate {
    @BindView(R.id.rv_setting)
    RecyclerView mRvSetting;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.btn_running)
    Button mBtnRunning;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    ISwr mSwr;
    boolean isRunning;
    private int mTime = 0;
    private TimerTask mTask;
    private Timer mTimer;

    @OnClick(R.id.btn_running)
    void onClickBtnRunnind(){
        isRunning = !isRunning;
        if (isRunning){
            initTimer();
            mBtnRunning.setText(getResources().getText(R.string.stop));
            mBtnRunning.setBackground(getResources().getDrawable(R.drawable.circle_red));
            goContainer(1);
            if (mSwr == null){
                mSwr = ((MainActivity)getProxyActivity()).getService();
            }
            mSwr.start();
        }else {
            stopTimer();
            mBtnRunning.setText(getResources().getText(R.string.start));
            mBtnRunning.setBackground(getResources().getDrawable(R.drawable.circle));
            mSwr.stop();
        }
    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_equipment;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        MainActivity activity = (MainActivity) getProxyActivity();
        mSwr = activity.getService();
        initSetting();
    }

    private void initTimer() {
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mTime++;
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                final String time =secondsFormat( mTime);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvTime.setText(time);
                    }
                });
            }
        };
        mTimer.schedule(mTask,1000,1000);
    }
    private void stopTimer(){
        mTimer.cancel();
        mTime = 0;
        mTvTime.setText(secondsFormat(mTime));
    }
    public String secondsFormat(int second) {
        int h = 0;
        int m = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    m = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            m = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }


        return String.format("%02d",h) + ":" +String.format("%02d",m) + ":" + String.format("%02d",s) + "";
    }

    private void initSetting() {
        ArrayList<EquipmentEntry> entries = new ArrayList<>();
        LinkedHashMap<String, Boolean> map = new LinkedHashMap<>();
        map.put("移动", true);
        map.put("电信", true);
        map.put("联通", false);
        entries.add(EquipmentEntry.createList("侦码状态", map));

        entries.add(EquipmentEntry.createDialog(getString(R.string.restart), getString(R.string.restart_desc),getProxyActivity(), new EquipmentEntry.Callback() {
            @Override
            public void onClickListener(int i) {
                mSwr.restart();
            }
        }));
        entries.add(EquipmentEntry.createDialog(getString(R.string.scan), getString(R.string.scan_desc), getProxyActivity(), new EquipmentEntry.Callback() {
            @Override
            public void onClickListener(int i) {
                mSwr.scan();
            }
        }));
        ArrayList<String> list = new ArrayList<>();
        list.add("高");
        list.add("中");
        list.add("低");
        entries.add(EquipmentEntry.createMultipleChoice("侦码功率", list, new EquipmentEntry.Callback() {
            @Override
            public void onClickListener(int i) {
                switch (i){
                    case R.id.rb_1:
                        Config.getInstance().setPower(Config.POWER_HEIGHT);
                        break;
                    case R.id.rb_2:
                        Config.getInstance().setPower(Config.POWER_MIDDLE);
                        break;
                    case R.id.rb_3:
                        Config.getInstance().setPower(Config.POWER_LOW);
                        break;
                }
                Config.getInstance().setPower(i);
            }
        }));
        ArrayList<String> changes = new ArrayList<>();
        changes.add("自动");
        changes.add("关闭");
        changes.add("开启");
        entries.add(EquipmentEntry.createMultipleChoice("侦码切换", changes, new EquipmentEntry.Callback() {
            @Override
            public void onClickListener(int i) {
                mSwr.setMode(i);
            }
        }));
        EquipmentAdapter adapter = new EquipmentAdapter(entries);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSetting.setLayoutManager(manager);
        mRvSetting.setAdapter(adapter);
        mRvSetting.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(R.color.colorAccent), 1));
    }
}
