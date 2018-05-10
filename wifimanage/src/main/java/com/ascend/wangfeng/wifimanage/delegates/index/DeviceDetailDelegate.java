package com.ascend.wangfeng.wifimanage.delegates.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.util.TimeUtil;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.PersonDevicesMap;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonListEditDelegate;
import com.ascend.wangfeng.wifimanage.greendao.PersonDao;
import com.ascend.wangfeng.wifimanage.greendao.PersonDevicesMapDao;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/5/8.
 * email 1040441325@qq.com
 */

public class DeviceDetailDelegate extends LatteDelegate {
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cimg_icon)
    CircleImageView mCimgIcon;
    @BindView(R.id.tv_device_name)
    TextView mTvDeviceName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_lasttime)
    TextView mTvLasttime;
    @BindView(R.id.tv_firsttime)
    TextView mTvFirsttime;
    @BindView(R.id.tv_owner)
    TextView mTvOwner;
    @BindView(R.id.tv_ip)
    TextView mTvIp;
    @BindView(R.id.tv_mac)
    TextView mTvMac;
    @BindView(R.id.tv_brand)
    TextView mTvBrand;
    @BindView(R.id.tv_dhcp)
    TextView mTvDhcp;
    @BindView(R.id.tv_netbios)
    TextView mTvNetbios;

    Device mDevice;

    @OnClick(R.id.ic_back)
    void clickIcBack() {
        pop();
    }

    public static DeviceDetailDelegate newInstance(Bundle bundle) {
        DeviceDetailDelegate delegate = new DeviceDetailDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_device_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mIcBack.setVisibility(View.VISIBLE);
        init();
    }

    private void init() {
        Bundle bundle = getArguments();
        mDevice = (Device) bundle.getSerializable("device");
        mTvDeviceName.setText(mDevice.getName());
        mTvLasttime.setText("最近更新时间: " + TimeUtil.format(mDevice.getLasttime()));
        mTvFirsttime.setText("首次出现时间: " + TimeUtil.format(mDevice.getFirsttime()));
        mTvIp.setText(mDevice.getIp());
        mTvMac.setText(mDevice.getMac());
        mTvBrand.setText(mDevice.getBrand());
        mTvDhcp.setText(mDevice.getDhcp());
        mTvNetbios.setText(mDevice.getNetbios());
        mTvOwner.setText(getOwner(mDevice));
        //设置用户
        mTvOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startForResult(PersonListEditDelegate.newInstance(), 1);
            }
        });
    }

    private String getOwner(Device device) {
        PersonDevicesMapDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDevicesMapDao();
        PersonDao personDao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDao();
        List<PersonDevicesMap> maps = dao.queryBuilder().where(PersonDevicesMapDao.Properties.DId.eq(device.getId())).list();
        if (maps.size() > 0) {
            Person person = personDao.queryBuilder().where(PersonDao.Properties.Id.eq(maps.get(0).getPId())).unique();
            if (person != null) {
                return person.getName();
            }
        }
        return "";
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {

        Person person = (Person) data.getSerializable("person");
        if (person != null) {
            mTvOwner.setText(person.getName());
            PersonDevicesMapDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDevicesMapDao();
            PersonDevicesMap map = new PersonDevicesMap();
            map.setDId(mDevice.getId());
            map.setPId(person.getId());
            dao.insert(map);
        }
    }
}
