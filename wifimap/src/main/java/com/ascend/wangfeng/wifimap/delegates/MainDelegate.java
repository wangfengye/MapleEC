package com.ascend.wangfeng.wifimap.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimap.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/2/7.
 * email 1040441325@qq.com
 */

public class MainDelegate extends LatteDelegate {
    @BindView(R.id.av_running)
    AVLoadingIndicatorView mAvRunning;
    @BindView(R.id.btn_controller)
    Button mBtnController;
    @OnClick(R.id.btn_controller)
    void onViewClicked() {
        mAvRunning.setVisibility(View.GONE);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_main;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
