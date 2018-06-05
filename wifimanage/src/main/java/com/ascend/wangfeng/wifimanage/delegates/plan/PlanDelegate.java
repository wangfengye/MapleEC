package com.ascend.wangfeng.wifimanage.delegates.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.index.device.DeviceDetailDelegate;
import com.ascend.wangfeng.wifimanage.delegates.index.device.NewDeviceAdapter;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 * 监管页面
 */

public class PlanDelegate extends BottomItemDelegate {
    @BindView(R.id.rv_devices)
    RecyclerView mRvDevices;
    private ArrayList<Device> mDevices;
    private NewDeviceAdapter mAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_plan;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initRv();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initData();
    }

    private void initData() {
        Client.getInstance().getDeviecseWithPlan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Response<List<Device>>>() {
                    @Override
                    public void onSuccess(Response<List<Device>> response) {
                        mDevices.clear();
                        mDevices.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initRv() {

        mDevices = new ArrayList<>();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter = new NewDeviceAdapter(mDevices);
        mAdapter.setListener(device -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DeviceDetailDelegate.DEVICE, device);
            getParentDelegate().start(DeviceDetailDelegate.newInstance(bundle));
        });
        mRvDevices.setLayoutManager(manager);
        mRvDevices.setAdapter(mAdapter);
        mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(R.color.textThi,getActivity().getTheme()), 1));
    }
}
