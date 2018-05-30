package com.ascend.wangfeng.wifimanage.delegates.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonListEditDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2018/5/11.
 * email 1040441325@qq.com
 */

public class DeviceEditDelegate extends LatteDelegate {
    public static final String DEVICE = "device";
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cimg_icon)
    CircleImageView mCimgIcon;
    @BindView(R.id.et_name)
    TextInputEditText mEtName;
    @BindView(R.id.rv_types)
    RecyclerView mRvTypes;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    @BindView(R.id.tv_owner)
    TextView mTvOwner;
    @BindView(R.id.cimg_owenr)
    CircleImageView mCImgOwener;
    private ArrayList<DeviceType> mTypes;
    private DeviceTypeAdapter mAdapter;
    private Device mDevice;
    private Person mPerson;

    @OnClick(R.id.cimg_owenr)
        // 选择所属人
    void clickCimgOwner() {
        startForResult(PersonListEditDelegate.newInstance(), 1);
    }

    @OnClick(R.id.btn_save)
    void clickBtnSave() {

        if (mDevice.getId() != null && mDevice.getId() != 0) {
            // 更新
            Client.getInstance().updateDevice(mDevice)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MyObserver<Response<Device>>() {
                        @Override
                        public void onNext(Response<Device> response) {
                            MainApp.toast(R.string.update_success);
                            goDeviceDetail();
                        }
                    });
        } else {
            //新增
            Client.getInstance().addDevice(mDevice)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MyObserver<Response<Device>>() {
                        @Override
                        public void onNext(Response<Device> response) {
                            MainApp.toast(R.string.add_success);
                            goDeviceDetail();
                        }
                    });
        }
    }

    private void goDeviceDetail() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DeviceDetailDelegate.DEVICE, mDevice);
        pop();
        start(DeviceDetailDelegate.newInstance(bundle), SINGLETASK);
    }

    public static DeviceEditDelegate newInstance(Bundle args) {
        DeviceEditDelegate fragment = new DeviceEditDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_device_edit;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mToolbarTitle.setText("设备编辑");
        mIcBack.setVisibility(View.VISIBLE);
        mIcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
        initRv();
        initData();

    }

    private void initRv() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 5);
        mTypes = DeviceType.getTypes();
        mAdapter = new DeviceTypeAdapter(mTypes);
        mAdapter.setListener(deviceType-> {
                // 设置设备类型;
                mDevice.setType(deviceType.getId());
        });
        mRvTypes.setAdapter(mAdapter);
        mRvTypes.setLayoutManager(manager);
        mRvTypes.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(R.color.colorBg), 3));
    }

    /**
     * 如果出入devcie,初始化数据
     */
    private void initData() {
        mDevice = (Device) getArguments().getSerializable(DEVICE);
        // 设置设备图标
        mCimgIcon.setImage(mTypes.get(mDevice.getType()).getImgId());
        mEtName.setText(mDevice.getName());
        // 监听名称改变
        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                mDevice.setName(sequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mTypes.get(mDevice.getType()).setChose(true);
        mAdapter.notifyDataSetChanged();
        // 拥有者关系
        reViewOwner(mDevice.getId());
    }

    private void reViewOwner(Long id) {
        Client.getInstance().getPersonById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Response<Person>>() {
                    @Override
                    public void onNext(Response<Person> response) {
                        mCImgOwener.setSrcType(CircleImageView.TYPE_NORMAL);
                        mCImgOwener.setImage(Icon.getImgUrl(response.getData().getImgUrl()));
                        mTvOwner.setText(response.getData().getName());
                    }
                });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        mPerson = (Person) data.getSerializable("person");
        if (mPerson != null) {
            mDevice.setpId(mPerson.getId());
            mCImgOwener.setSrcType(CircleImageView.TYPE_NORMAL);
            mCImgOwener.setImage(Icon.getImgUrl(mPerson.getImgUrl()));
            mTvOwner.setText(mPerson.getName());
        }
    }

}
