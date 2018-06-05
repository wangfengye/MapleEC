package com.ascend.wangfeng.wifimanage.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.index.IndexPersonAdapter;
import com.ascend.wangfeng.wifimanage.delegates.index.IndexSquareAdapter;
import com.ascend.wangfeng.wifimanage.delegates.index.device.DeviceDetailDelegate;
import com.ascend.wangfeng.wifimanage.delegates.index.device.DeviceEditDelegate;
import com.ascend.wangfeng.wifimanage.delegates.index.device.NewDeviceDelegate;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonDetailDelegate;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonListDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class IndexDelegate extends BottomItemDelegate {

    public static final String TAG = IndexDelegate.class.getSimpleName();
    @BindView(R.id.ll_new_device)
    LinearLayout mLlNewDevice;

    @BindView(R.id.ll_people)
    LinearLayout mLlPeople;

    @BindView(R.id.ll_online_device)
    LinearLayout mLlOnlineDevice;
    @BindView(R.id.rv_new_devices)
    RecyclerView mRvNewDevices;
    @BindView(R.id.rv_people)
    RecyclerView mRvPeople;
    @BindView(R.id.rv_online_devices)
    RecyclerView mRvOnlineDevices;

    @OnClick(R.id.ll_new_device)
    void clickLlNewDevice() {
        Bundle bundle = new Bundle();
        bundle.putInt(NewDeviceDelegate.TITLE, NewDeviceDelegate.TITLE_NEW_DEVICE);
        bundle.putSerializable(NewDeviceDelegate.DEVICE_LIST, mNewDevices);
        getParentDelegate().start(NewDeviceDelegate.newInstance(bundle));
    }

    @OnClick(R.id.ll_online_device)
    void clickLlOnlineDevice() {
        Bundle bundle = new Bundle();
        bundle.putInt(NewDeviceDelegate.TITLE, NewDeviceDelegate.TITLE_ONLINE_DEVICE);
        bundle.putSerializable(NewDeviceDelegate.DEVICE_LIST, mOnlineDevices);
        getParentDelegate().start(NewDeviceDelegate.newInstance(bundle));
    }

    @OnClick(R.id.ll_people)
    void clickLlPeople() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("person", mPeople);
        getParentDelegate().start(PersonListDelegate.newInstance(bundle));
    }

    ArrayList<Device> mNewDevices;//新发现设备
    ArrayList<Person> mPeople; // 在线人员
    LinkedHashSet<Long> mPIds; // 在线人员
    ArrayList<Device> mOnlineDevices; // 在线设备
    private IndexSquareAdapter mNewDevicesAdapter;
    private IndexSquareAdapter mOnlineDevicesAdapter;
    private IndexPersonAdapter mPeopleAdapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public Object setLayout() {
        return R.layout.delegate_index1;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initView();
    }

    private void initView() {
        // 新设备
        mNewDevices = new ArrayList<>();
        mNewDevicesAdapter = new IndexSquareAdapter(mNewDevices,
                getResources().getColor(R.color.colorOrange, getActivity().getTheme()));
        mNewDevicesAdapter.setListener(device -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DeviceEditDelegate.DEVICE, device);
            start(DeviceEditDelegate.newInstance(bundle));
        });
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 5);
        mRvNewDevices.setLayoutManager(manager);
        mRvNewDevices.setAdapter(mNewDevicesAdapter);

        // 新人员
        mPIds = new LinkedHashSet<>();
        mPeople = new ArrayList<>();
        mPeopleAdapter = new IndexPersonAdapter(mPeople
                , getResources().getColor(R.color.colorAccent, getActivity().getTheme()));
        mPeopleAdapter.setListener(person -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(PersonDetailDelegate.PERSON, person);
            start(PersonDetailDelegate.newInstance(bundle));
        });
        RecyclerView.LayoutManager managerPeople = new GridLayoutManager(getContext(), 5);
        mRvPeople.setLayoutManager(managerPeople);
        mRvPeople.setAdapter(mPeopleAdapter);

        // 在线人员
        mOnlineDevices = new ArrayList<>();
        mOnlineDevicesAdapter = new IndexSquareAdapter(mOnlineDevices,
                getResources().getColor(R.color.colorAccent, getActivity().getTheme()));
        RecyclerView.LayoutManager managerOnline = new GridLayoutManager(getContext(), 5);
        mOnlineDevicesAdapter.setListener(device -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DeviceDetailDelegate.DEVICE, device);
            start(DeviceDetailDelegate.newInstance(bundle));
        });
        mRvOnlineDevices.setAdapter(mOnlineDevicesAdapter);
        mRvOnlineDevices.setLayoutManager(managerOnline);


    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mCompositeDisposable != null) mCompositeDisposable.clear();
        initData();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        mCompositeDisposable.clear();
    }

    private void initData() {
        // 数据源
        Client.getInstance().getCurrentDevices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Response<List<Device>>>() {
                    @Override
                    public void onSuccess(Response<List<Device>> response) {
                        mOnlineDevices.clear();
                        mNewDevices.clear();
                        mPIds.clear();
                        for (Device d : response.getData()) {
                            if (d.getPid() != null && d.getPid() != 0) {
                                mOnlineDevices.add(d);
                                mPIds.add(d.getPid());
                            } else {
                                mNewDevices.add(d);
                            }
                        }
                        if (mNewDevices.size() == 0) {
                            mLlNewDevice.setVisibility(View.GONE);
                        } else {
                            mLlNewDevice.setVisibility(View.VISIBLE);
                            mNewDevicesAdapter.notifyDataSetChanged();
                        }
                        mOnlineDevicesAdapter.notifyDataSetChanged();
                        getPersons(mPIds);
                    }
                });
    }

    public void getPersons(LinkedHashSet<Long> ids) {
        mPeople.clear();
        Observable.fromIterable(ids)
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        Client.getInstance().getPersonById(aLong)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new MyObserver<Response<Person>>() {
                                    @Override
                                    public void onSuccess(Response<Person> response) {
                                        mPeople.add(response.getData());
                                        mPeopleAdapter.notifyDataSetChanged();
                                    }
                                });
                    }
                });

    }

}
