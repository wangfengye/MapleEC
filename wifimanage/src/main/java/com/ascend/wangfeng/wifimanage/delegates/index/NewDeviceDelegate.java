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
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2018/5/8.
 * email 1040441325@qq.com
 * Q;数据由 indexDelgegate 传入,无法主动刷新
 */

public class NewDeviceDelegate extends LatteDelegate {
    public static final String DEVICE_LIST = "devices";
    public static final String TITLE = "title";
    public static final int TITLE_NEW_DEVICE = 0;
    public static final int TITLE_ONLINE_DEVICE = 1;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    private NewDeviceAdapter mAdapter;

    @OnClick(R.id.ic_back)
    void clickIcBack() {
        pop();
    }

    @BindView(R.id.rv_devices)
    RecyclerView mRvDevices;
    ArrayList<Device> mDevices;

    public static NewDeviceDelegate newInstance(Bundle bundle) {
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
        initView();
        initData();
    }

    private void initData() {
        if (getArguments().getInt(TITLE) == TITLE_NEW_DEVICE) {
            Client.getInstance().getUnknownDevices()
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
        } else {
            Client.getInstance().getTagDevices()
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
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initData();
    }

    private void initView() {
        Bundle bundle = getArguments();
        mToolbarTitle.setText(bundle.getInt(TITLE) == TITLE_NEW_DEVICE ? "新设备" : "在线设备");
        mDevices = new ArrayList<>();
        mAdapter = new NewDeviceAdapter(mDevices);
        mAdapter.setListener(device -> {
            Bundle bundle1 = new Bundle();
            switch (getArguments().getInt(TITLE)) {
                case TITLE_NEW_DEVICE:
                    bundle1.putSerializable(DeviceEditDelegate.DEVICE, device);
                    start(DeviceEditDelegate.newInstance(bundle1));
                    break;
                case TITLE_ONLINE_DEVICE:
                    bundle1.putSerializable(DeviceDetailDelegate.DEVICE, device);
                    start(DeviceDetailDelegate.newInstance(bundle1));
                    break;
            }
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRvDevices.setLayoutManager(manager);

        mRvDevices.setAdapter(mAdapter);
        mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.darker_gray,getActivity().getTheme()), 1));
    }

}
