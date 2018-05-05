package com.ascend.wangfeng.wifimap.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimap.R;

/**
 * Created by fengye on 2018/4/24.
 * email 1040441325@qq.com
 */

public class CircleImageDelegate extends LatteDelegate{


    @Override
    public Object setLayout() {
        return R.layout.delegate_circle_image;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }
}
