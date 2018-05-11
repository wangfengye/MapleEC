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
    public static final String DEVICE = "device";
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ic_edit)
    IconTextView mIcEdit;
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

    @OnClick(R.id.ic_edit)
    void clickIcEdit() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DeviceEditDelegate.DEVICE, mDevice);
        start(DeviceEditDelegate.newInstance(bundle));
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
        mIcEdit.setVisibility(View.VISIBLE);
        init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (true)init();
    }

    private void init() {
        Bundle bundle = getArguments();
        mDevice = (Device) bundle.getSerializable("device");
        mCimgIcon.setImage(DeviceType.getTypes().get(mDevice.getType()).getImgId());
        mTvDeviceName.setText(mDevice.getName());
        mTvLasttime.setText("最近更新时间: " + TimeUtil.format(mDevice.getLasttime()));
        mTvFirsttime.setText("首次出现时间: " + TimeUtil.format(mDevice.getFirsttime()));
        mTvIp.setText(mDevice.getIp());
        mTvMac.setText(mDevice.getMac());
        mTvBrand.setText(mDevice.getBrand());
        mTvDhcp.setText(mDevice.getDhcp());
        mTvNetbios.setText(mDevice.getNetbios());
        mTvOwner.setText(getOwner(mDevice));

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


}
