package com.ascend.wangfeng.wifimanage.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class IndexDelegate extends BottomItemDelegate {

    @BindView(R.id.ll_new_device_content)
    LinearLayout mLlNewDeviceContent;
    @BindView(R.id.ll_new_device)
    LinearLayout mLlNewDevice;
    @BindView(R.id.ll_people_content)
    LinearLayout mLlPeopleContent;
    @BindView(R.id.ll_people)
    LinearLayout mLlPeople;
    @BindView(R.id.ll_online_device_content)
    LinearLayout mLlOnlineDeviceContent;
    @BindView(R.id.ll_online_device)
    LinearLayout mLlOnlineDevice;


    List<Device> mNewDevices;//新发现设备
    List<Person> mPeople; // 在线人员
    List<Device> mOnlineDevices; // 在线设备

    @Override
    public Object setLayout() {
        return R.layout.delegate_index1;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        // 重载数据
        initData();
        resetView();
    }

    private void initData() {
        mNewDevices = new ArrayList<>();
        mNewDevices.add(new Device());


        mPeople = new ArrayList<>();
        mPeople.add(new Person()); mPeople.add(new Person()); mPeople.add(new Person()); mPeople.add(new Person());

        mOnlineDevices = new ArrayList<>();
        mOnlineDevices.add(new Device());
        mOnlineDevices.add(new Device());
        mOnlineDevices.add(new Device());
        mOnlineDevices.add(new Device());
    }

    private void resetView() {
        // 新设备
        mLlNewDeviceContent.removeAllViews();
        if(mNewDevices ==null||mNewDevices.size() ==0 )mLlNewDevice.setVisibility(View.GONE);
        for (int i = 0; i < mNewDevices.size(); i++) {
            if (i >= 6) break;
            LayoutInflater.from(getContext()).inflate(R.layout.item_circle_image, mLlNewDeviceContent);
            CircleImageView img = (CircleImageView) mLlNewDeviceContent.getChildAt(i);
            img.setImage(getResources().getDrawable(R.drawable.phone));
            img.setNeedBg(getResources().getColor(R.color.colorOrange));
        }
        // 在线人员
        mLlPeopleContent.removeAllViews();
        for (int i = 0; i < mPeople.size(); i++) {
            if (i >= 5) break;
            LayoutInflater.from(getContext()).inflate(R.layout.item_circle_image, mLlPeopleContent);
            CircleImageView img = (CircleImageView) mLlPeopleContent.getChildAt(i);
            img.setImage(getResources().getDrawable(R.drawable.test));
        }
        // 在线设备
        mLlOnlineDeviceContent.removeAllViews();
        for (int i = 0; i < mOnlineDevices.size(); i++) {
            if (i >= 5) break;
            LayoutInflater.from(getContext()).inflate(R.layout.item_circle_image, mLlOnlineDeviceContent);
            CircleImageView img = (CircleImageView) mLlOnlineDeviceContent.getChildAt(i);
            img.setImage(getResources().getDrawable(R.drawable.phone));
            img.setNeedBg(getResources().getColor(R.color.colorAccent));
        }
    }

}
