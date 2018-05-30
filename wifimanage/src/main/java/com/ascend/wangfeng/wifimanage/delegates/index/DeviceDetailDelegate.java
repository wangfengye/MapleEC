package com.ascend.wangfeng.wifimanage.delegates.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.util.TimeUtil;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Plan;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonDetailDelegate;
import com.ascend.wangfeng.wifimanage.delegates.plan.PlanAdapter;
import com.ascend.wangfeng.wifimanage.delegates.plan.PlanDetailDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.ascend.wangfeng.wifimanage.utils.TimeUtil.getTime;

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
    @BindView(R.id.cimg_owenr)
    CircleImageView mCimgOwner;
    @BindView(R.id.rv_plans)
    RecyclerView mRvPlans;
    @BindView(R.id.ll_add)
    LinearLayout mLlAdd;
    Device mDevice;
    private PlanAdapter mPlanAdapter;
    private ArrayList<Plan> mPlans;
    private Person mPerson;

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
        initView();

    }

    private void initView() {
        mPlans = new ArrayList<>();
        mPlanAdapter = new PlanAdapter(mPlans);
        mPlanAdapter.setListener(new PlanAdapter.OnClickListener() {
            @Override
            public void click(Plan plan) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(PlanDetailDelegate.PLAN, plan);
                start(PlanDetailDelegate.newInstance(bundle));
            }
        });
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvPlans.setAdapter(mPlanAdapter);
        mRvPlans.setLayoutManager(manager);

        mLlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Plan plan = new Plan();
                plan.setdId(mDevice.getId());
                plan.setType(0);
                plan.setStarttime(getTime(9, 0));
                plan.setEndtime(getTime(18, 0));
                bundle.putSerializable(PlanDetailDelegate.PLAN, plan);
                start(PlanDetailDelegate.newInstance(bundle));
            }
        });
        mCimgOwner.setOnClickListener(view -> {
            if (mPerson != null) {
                Bundle args = new Bundle();
                args.putSerializable(PersonDetailDelegate.PERSON, mPerson);
                start(PersonDetailDelegate.newInstance(args), SINGLETASK);
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        mDevice = (Device) bundle.getSerializable(DEVICE);
        mCimgIcon.setImage(DeviceType.getTypes().get(mDevice.getType()).getImgId());
        mTvDeviceName.setText(mDevice.getName());
        mTvLasttime.setText("最近更新时间: " + TimeUtil.format(mDevice.getLasttime()));
        mTvFirsttime.setText("首次出现时间: " + TimeUtil.format(mDevice.getFirsttime()));
        mTvIp.setText(mDevice.getIp());
        mTvMac.setText(mDevice.getMac());
        mTvBrand.setText(mDevice.getBrand());
        mTvDhcp.setText(mDevice.getDhcp());
        mTvNetbios.setText(mDevice.getNetbios());
        reViewOwner(mDevice.getId());
        Client.getInstance().getPlansByDId(mDevice.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Response<List<Plan>>>() {
                    @Override
                    public void onNext(Response<List<Plan>> response) {
                        mPlans.clear();
                        mPlans.addAll(response.getData());
                        mPlanAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void reViewOwner(Long id) {
        Client.getInstance().getPersonById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Response<Person>>() {
                    @Override
                    public void onNext(Response<Person> response) {
                        mPerson = response.getData();
                        mTvOwner.setText(response.getData().getName());
                        mCimgOwner.setImage(Icon.getImgUrl(response.getData().getImgUrl()));
                    }
                });

    }


}
