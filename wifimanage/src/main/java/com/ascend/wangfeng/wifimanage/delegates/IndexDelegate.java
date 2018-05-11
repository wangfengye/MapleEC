package com.ascend.wangfeng.wifimanage.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.api.Api;
import com.ascend.wangfeng.wifimanage.api.Callback;
import com.ascend.wangfeng.wifimanage.api.DemoApi;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.PersonDevicesMap;
import com.ascend.wangfeng.wifimanage.delegates.index.DeviceType;
import com.ascend.wangfeng.wifimanage.delegates.index.NewDeviceDelegate;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonListDelegate;
import com.ascend.wangfeng.wifimanage.greendao.DeviceDao;
import com.ascend.wangfeng.wifimanage.greendao.PersonDao;
import com.ascend.wangfeng.wifimanage.greendao.PersonDevicesMapDao;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class IndexDelegate extends BottomItemDelegate {

    public static final String TAG = IndexDelegate.class.getSimpleName();
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
    ArrayList<Device> mOnlineDevices; // 在线设备

    @Override
    public Object setLayout() {
        return R.layout.delegate_index1;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initData();
        resetView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // 重载数据 ,当点击按钮时
        if (!hidden) {
            initData();
            resetView();
        }
    }

    private void initData() {
        mNewDevices = new ArrayList<>();
        mPeople = new ArrayList<>();
        mOnlineDevices = new ArrayList<>();

        Api api = new DemoApi();
        api.getCurrentDevices(new Callback<List<Device>>() {
            @Override
            public void callback(List<Device> devices) {
                for (Device device : devices) {
                    dispose(device);
                }
                disposePeople();
                resetView();
            }
        });
    }

    private void disposePeople() {
        PersonDao personDao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDao();
        PersonDevicesMapDao mapDao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDevicesMapDao();
        for (Device device : mOnlineDevices) {
            List<PersonDevicesMap> maps = mapDao.queryBuilder().where(PersonDevicesMapDao.Properties.DId.eq(device.getId())).list();
            if (maps.size() > 0) {
                PersonDevicesMap map = maps.get(0);
                Person person = personDao.queryBuilder().where(PersonDao.Properties.Id.eq(map.getPId())).unique();
                if (person!=null&& !contain(person,mPeople)) {
                    mPeople.add(person);
                }
            }
        }
    }

    private boolean contain(Person person, ArrayList<Person> people1) {
        for (Person p : people1) {
            if (p.getId() ==person.getId())return true;
        }
        return false;
    }

    private void dispose(Device device) {
        DeviceDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getDeviceDao();
        PersonDevicesMapDao mapDao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDevicesMapDao();
        List<Device> result = dao.queryBuilder().where(DeviceDao.Properties.Mac.eq(device.getMac())).list();
        if (result.size() > 0) {
            Device rD = result.get(0);
            rD.setLasttime(System.currentTimeMillis());
            dao.update(rD);
            List<PersonDevicesMap> maps = mapDao.queryBuilder().where(PersonDevicesMapDao.Properties.DId.eq(rD.getId())).list();
            if (maps.size() > 0) {
                mOnlineDevices.add(rD);
            } else {
                mNewDevices.add(rD);
            }

        } else {

            device.setFirsttime(System.currentTimeMillis());
            device.setLasttime(System.currentTimeMillis());
            dao.insert(device);
            List<Device> devices = dao.queryBuilder().where(DeviceDao.Properties.Mac.eq(device.getMac())).list();
            mNewDevices.add(devices.get(0));
        }

    }

    private void resetView() {
        // 新设备
        mLlNewDeviceContent.removeAllViews();
        if (mNewDevices == null || mNewDevices.size() == 0) mLlNewDevice.setVisibility(View.GONE);
        for (int i = 0; i < mNewDevices.size(); i++) {
            if (i >= 6) break;
            LayoutInflater.from(getContext()).inflate(R.layout.item_circle_image, mLlNewDeviceContent);
            CircleImageView img = (CircleImageView) mLlNewDeviceContent.getChildAt(i);
            img.setImage(DeviceType.getTypes().get(mNewDevices.get(i).getType()).getImgId());
            img.setBg(getResources().getColor(R.color.colorOrange));
            img.setSrcType(CircleImageView.TYPE_WHITE);
        }
        // 在线人员
        mLlPeopleContent.removeAllViews();
        for (int i = 0; i < mPeople.size(); i++) {
            if (i >= 5) break;
            LayoutInflater.from(getContext()).inflate(R.layout.item_circle_image, mLlPeopleContent);
            CircleImageView img = (CircleImageView) mLlPeopleContent.getChildAt(i);
            img.setImage(getResources().getDrawable(R.drawable.test));
            img.setSrcType(CircleImageView.TYPE_FULL);
        }
        // 在线设备
        mLlOnlineDeviceContent.removeAllViews();
        for (int i = 0; i < mOnlineDevices.size(); i++) {
            if (i >= 5) break;
            LayoutInflater.from(getContext()).inflate(R.layout.item_circle_image, mLlOnlineDeviceContent);
            CircleImageView img = (CircleImageView) mLlOnlineDeviceContent.getChildAt(i);
            img.setImage(DeviceType.getTypes().get(mOnlineDevices.get(i).getType()).getImgId());
            img.setBg(getResources().getColor(R.color.colorAccent));
            img.setSrcType(CircleImageView.TYPE_WHITE);
        }
    }

}
