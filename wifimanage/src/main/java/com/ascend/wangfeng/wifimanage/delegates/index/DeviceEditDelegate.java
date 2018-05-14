package com.ascend.wangfeng.wifimanage.delegates.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.ascend.wangfeng.wifimanage.bean.PersonDevicesMap;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonListEditDelegate;
import com.ascend.wangfeng.wifimanage.greendao.DeviceDao;
import com.ascend.wangfeng.wifimanage.greendao.PersonDao;
import com.ascend.wangfeng.wifimanage.greendao.PersonDevicesMapDao;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
        // 更新device
        DeviceDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getDeviceDao();
        dao.update(mDevice);
        // 更新从属关系
        if (mPerson != null) {
            PersonDevicesMapDao mapDao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDevicesMapDao();
            PersonDevicesMap map = mapDao.queryBuilder().where(PersonDevicesMapDao.Properties.DId.eq(mDevice.getId())).unique();
            if (map != null) {
                map.setPId(mPerson.getId());
                mapDao.update(map);
            } else {
                map = new PersonDevicesMap();
                map.setPId(mPerson.getId());
                map.setDId(mDevice.getId());
                mapDao.insert(map);
            }
        }
        Snackbar.make(mToolbar, "保存成功", Snackbar.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DeviceEditDelegate.DEVICE, mDevice);
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
        mAdapter.setListener(new DeviceTypeAdapter.OnClickListener() {
            @Override
            public void click(DeviceType deviceType) {
                // 设置设备类型;
                mDevice.setType(deviceType.getId());
            }
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
        Person person = getOwner(mDevice);
        if (person != null) {
            mCImgOwener.setSrcType(CircleImageView.TYPE_NORMAL);
            mCImgOwener.setImage(person.getImgUrl());
            mTvOwner.setText(person.getName());
        }
    }

    private Person getOwner(Device device) {
        PersonDevicesMapDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDevicesMapDao();
        PersonDao personDao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDao();
        List<PersonDevicesMap> maps = dao.queryBuilder().where(PersonDevicesMapDao.Properties.DId.eq(device.getId())).list();
        if (maps.size() > 0) {
            Person person = personDao.queryBuilder().where(PersonDao.Properties.Id.eq(maps.get(0).getPId())).unique();
            if (person != null) {
                return person;
            }
        }
        return null;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        mPerson = (Person) data.getSerializable("person");
        if (mPerson != null) {
            mCImgOwener.setSrcType(CircleImageView.TYPE_NORMAL);
            mCImgOwener.setImage(mPerson.getImgUrl());
            mTvOwner.setText(mPerson.getName());
            PersonDevicesMapDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDevicesMapDao();
            PersonDevicesMap map = new PersonDevicesMap();
            map.setDId(mDevice.getId());
            map.setPId(mPerson.getId());
            dao.insert(map);
        }
    }

}
