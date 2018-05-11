package com.ascend.wangfeng.wifimanage.delegates.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/5/8.
 * email 1040441325@qq.com
 */

public class NewDeviceDelegate extends LatteDelegate{
    public static final String DEVICE_LIST="devices";
    public static final String TITLE="title";
    public static final int TITLE_NEW_DEVICE=0;
    public static final int TITLE_ONLINE_DEVICE=1;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @OnClick(R.id.ic_back)
    void clickIcBack(){
        pop();
    }
    @BindView(R.id.rv_devices)
    RecyclerView mRvDevices;
    ArrayList<Device> mDevices;
    public static NewDeviceDelegate newInstance(Bundle bundle){
        NewDeviceDelegate delegate = new NewDeviceDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_new_device;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mIcBack.setVisibility(View.VISIBLE);
        initData();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        mToolbarTitle.setText(bundle.getInt(TITLE)==TITLE_NEW_DEVICE?"新设备":"在线设备");
        mDevices= (ArrayList<Device>) bundle.getSerializable(DEVICE_LIST);
        NewDeviceAdapter adapter = new NewDeviceAdapter(mDevices,this);
        adapter.setListener(new NewDeviceAdapter.OnClickListener() {
            @Override
            public void click(Device device) {
                Bundle bundle = new Bundle();
                switch (getArguments().getInt(TITLE)){
                    case TITLE_NEW_DEVICE:
                        bundle.putSerializable(DeviceEditDelegate.DEVICE,device);
                        start(DeviceEditDelegate.newInstance(bundle));
                        break;
                    case TITLE_ONLINE_DEVICE:
                        bundle.putSerializable(DeviceDetailDelegate.DEVICE,device);
                        start(DeviceDetailDelegate.newInstance(bundle));
                        break;
                }

            }
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRvDevices.setLayoutManager(manager);

        mRvDevices.setAdapter(adapter);
        mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.darker_gray), 1));
    }

}