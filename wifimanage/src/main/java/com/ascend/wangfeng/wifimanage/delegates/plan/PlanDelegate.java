package com.ascend.wangfeng.wifimanage.delegates.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Plan;
import com.ascend.wangfeng.wifimanage.delegates.index.DeviceDetailDelegate;
import com.ascend.wangfeng.wifimanage.delegates.index.NewDeviceAdapter;
import com.ascend.wangfeng.wifimanage.greendao.DeviceDao;
import com.ascend.wangfeng.wifimanage.greendao.PlanDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 * 监管页面
 */

public class PlanDelegate extends BottomItemDelegate{
    @BindView(R.id.rv_devices)
    RecyclerView mRvDevices;
    @Override
    public Object setLayout() {
        return R.layout.delegate_plan;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initRv();
    }

    private void initRv() {
        PlanDao planDao = ((MainApp)getActivity().getApplication()).getDaoSession().getPlanDao();
        List<Plan> plans = planDao.loadAll();
        DeviceDao deviceDao = ((MainApp)getActivity().getApplication()).getDaoSession().getDeviceDao();
        List<Device> devices = new ArrayList<>();
        for (int i = 0; i < plans.size(); i++) {
            Device device = deviceDao.queryBuilder().where(DeviceDao.Properties.Id.eq(plans.get(i).getDid())).unique();
            if(!contain(device,devices)) devices.add(device);
        }
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        NewDeviceAdapter adapter = new NewDeviceAdapter(devices,this);
        adapter.setListener(new NewDeviceAdapter.OnClickListener() {
            @Override
            public void click(Device device) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(DeviceDetailDelegate.DEVICE,device);
                getParentDelegate().start(DeviceDetailDelegate.newInstance(bundle));
            }
        });
        mRvDevices.setLayoutManager(manager);
        mRvDevices.setAdapter(adapter);
        mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(R.color.textThi), 1));
    }

    private boolean contain(Device device, List<Device> devices) {
        for (Device d : devices) {
            if (device.getId() == d.getId())
                return true;
        }
        return false;
    }
}
