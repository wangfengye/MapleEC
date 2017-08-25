package com.ascend.wangfeng.latte.ec.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ec.R;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class CompassDelegate extends BottomItemDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_compass;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
