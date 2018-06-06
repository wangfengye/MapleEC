package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Event;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.history.TimeLineAdapter;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.delegates.index.device.DeviceDetailDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.net.SchedulerProvider;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 */

public class PersonDetailDelegate extends LatteDelegate {

    public static final String PERSON = "person";
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.ic_edit)
    IconTextView mIcEdit;
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
    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    private Person mPerson;
    private DeviceSquareAdapter mDeviceAdapter;
    private ArrayList<Device> mDevices;
    private ArrayList<Event> mEvents;
    private TimeLineAdapter mTimeLineAdapter;


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
        mIcBack.setOnClickListener(view -> pop());
        mIcEdit.setVisibility(View.VISIBLE);
        mIcEdit.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(PersonEditDelegate.PERSON, mPerson);
            start(PersonEditDelegate.newInstance(bundle));
        });
        mToolbarTitle.setText("成员详情");
        mPerson = (Person) getArguments().getSerializable(PERSON);
        mDevices = new ArrayList<>();
        mDeviceAdapter = new DeviceSquareAdapter(mDevices);
        mDeviceAdapter.setListener(device -> {
            Bundle args = new Bundle();
            args.putSerializable(DeviceDetailDelegate.DEVICE, device);
            start(DeviceDetailDelegate.newInstance(args), SINGLETASK);
        });
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRvDevices.setLayoutManager(manager);
        mRvDevices.setAdapter(mDeviceAdapter);
        mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.white, getActivity().getTheme()), 3));

        // 时间轴
        mEvents = new ArrayList<>();
        mTimeLineAdapter = new TimeLineAdapter(mEvents);
        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(getContext());
        mRvHistory.setAdapter(mTimeLineAdapter);
        mRvHistory.setLayoutManager(manager1);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initPerson();
        initDevices();
    }

    private void initPerson() {
        mTvName.setText(mPerson.getPname());
        mCimgIcon.setImage(Icon.getImgUrl(mPerson.getPimage()));
        //mTvDesc.setText();
    }

    private void initHistory() {
        Client.getInstance().getOnlineByDmac(mDevices.get(0).getDmac(),0,10)
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<List<Event>>>() {
                    @Override
                    public void onSuccess(Response<List<Event>> response) {
                        mEvents.clear();
                        mEvents.addAll(response.getData());
                        mTimeLineAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initDevices() {
        Client.getInstance().getDevicesByPid(mPerson.getPid())
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<List<Device>>>() {
                    @Override
                    public void onSuccess(Response<List<Device>> response) {
                        mDevices.clear();
                        mDevices.addAll(response.getData());
                        mDeviceAdapter.notifyDataSetChanged();
                        initHistory();
                    }
                });

    }
}
