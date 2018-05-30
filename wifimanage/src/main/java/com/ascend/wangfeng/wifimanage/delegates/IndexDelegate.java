package com.ascend.wangfeng.wifimanage.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.delegates.index.DeviceType;
import com.ascend.wangfeng.wifimanage.delegates.index.NewDeviceDelegate;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonListDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;

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
    LinkedHashSet<Long> mPIds; // 在线人员
    ArrayList<Device> mOnlineDevices; // 在线设备
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public Object setLayout() {
        return R.layout.delegate_index1;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initData();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        mCompositeDisposable.clear();
    }

    private void initData() {
        mNewDevices = new ArrayList<>();
        mPeople = new ArrayList<>();
        mOnlineDevices = new ArrayList<>();
        mPIds = new LinkedHashSet<>();
        // 数据源
        Client.getInstance().getCurrentDevices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Response<List<Device>>>() {
            @Override
            public void onNext(Response<List<Device>> response) {
                for (Device d : response.getData()) {
                    if (d.getpId() != null && d.getpId() != 0) {
                        mOnlineDevices.add(d);
                        mPIds.add(d.getpId());
                    } else {
                        mNewDevices.add(d);
                    }
                }
                getPersons(mPIds);
            }
        });


    }

    public void getPersons(LinkedHashSet<Long> ids) {
        Observable.fromIterable(ids)
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        Client.getInstance().getPersonById(aLong)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new MyObserver<Response<Person>>() {
                                    @Override
                                    public void onNext(Response<Person> response) {
                                        mPeople.add(response.getData());
                                        resetView();
                                    }
                                });
                    }
                });

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
            img.setImage(Icon.getImgUrl(mPeople.get(i).getImgUrl()));
            img.setBg(getResources().getColor(R.color.colorAccent));
            img.setSrcType(CircleImageView.TYPE_NORMAL);
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

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }
}
