package com.ascend.wangfeng.wifimanage.delegates.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by fengye on 2018/5/8.
 * email 1040441325@qq.com
 */

public class NewDeviceDelegate extends LatteDelegate{
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @OnClick(R.id.ic_back)
    void clickIcBack(){
        pop();
    }
    @BindView(R.id.rv_devices)
    RecyclerView mRvDevices;
    ArrayList<Device> mDevices;
    public static NewDeviceDelegate newInstance(Bundle bundle){
        NewDeviceDelegate delegate = new NewDeviceDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_new_device;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {


    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        // 在转场动画后加载数据,避免卡顿
        super.onEnterAnimationEnd(savedInstanceState);
        mIcBack.setVisibility(View.VISIBLE);
        initData();

    }

    private void initData() {
        Bundle bundle = getArguments();
        mToolbarTitle.setText(bundle.getString("title"));
        mDevices= (ArrayList<Device>) bundle.getSerializable("new_device");
        NewDeviceAdapter adapter = new NewDeviceAdapter(mDevices,this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRvDevices.setLayoutManager(manager);

        mRvDevices.setAdapter(adapter);
        mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.darker_gray), 1));
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {

    }
}
