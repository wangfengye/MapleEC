package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.PersonDevicesMap;
import com.ascend.wangfeng.wifimanage.greendao.DeviceDao;
import com.ascend.wangfeng.wifimanage.greendao.PersonDevicesMapDao;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 */

public class PersonDetailDelegate extends LatteDelegate {

    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cimg_icon)
    CircleImageView mCimgIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.rv_devices)
    RecyclerView mRvDevices;
    @BindView(R.id.bar_history)
    BarChart mBarChart;
    private Person mPerson;


    public static PersonDetailDelegate newInstance(Bundle args) {
        PersonDetailDelegate fragment = new PersonDetailDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_person_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mIcBack.setVisibility(View.VISIBLE);
        mIcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
        mToolbarTitle.setText("成员详情");
        mPerson = (Person) getArguments().getSerializable("person");
        initPerson();
        initDevices();
        initHistory();
    }

    private void initPerson() {
        mTvName.setText(mPerson.getName());
        //mTvDesc.setText();
    }

    private void initHistory() {
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            xValues.add(i+1 + "");
        }
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            float value = (float) (Math.random() * 7) + 1;
            yValues.add(new BarEntry(i,value));
        }
        BarDataSet barDataSet = new BarDataSet(yValues,"q");
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(.5f);
        mBarChart.setData(barData);
    }

    private void initDevices() {
        if (mPerson != null) {
            // 通过人员id 获取关联设备
            PersonDevicesMapDao mapDao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDevicesMapDao();
            List<PersonDevicesMap> maps = mapDao.queryBuilder().where(PersonDevicesMapDao.Properties.PId.eq(mPerson.getId())).list();
            DeviceDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getDeviceDao();
            ArrayList<Device> devices = new ArrayList<>();
            for (PersonDevicesMap map : maps) {
                Device devcice = dao.queryBuilder().where(DeviceDao.Properties.Id.eq(map.getDId())).unique();
                devices.add(devcice);
            }

            DeviceSquareAdapter adapter = new DeviceSquareAdapter(devices);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
            mRvDevices.setLayoutManager(manager);
            mRvDevices.setAdapter(adapter);
            mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                    .getColor(android.R.color.white), 3));
        }
    }

}
