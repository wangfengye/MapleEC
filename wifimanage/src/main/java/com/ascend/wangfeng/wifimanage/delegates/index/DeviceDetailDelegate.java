package com.ascend.wangfeng.wifimanage.delegates.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.R;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/5/8.
 * email 1040441325@qq.com
 */

public class DeviceDetailDelegate extends LatteDelegate{
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @OnClick(R.id.ic_back)
    void clickIcBack(){
        pop();
    }
    public static DeviceDetailDelegate newInstance(Bundle bundle){
        DeviceDetailDelegate delegate = new DeviceDetailDelegate();
        return delegate;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_device_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mIcBack.setVisibility(View.VISIBLE);
    }
}
