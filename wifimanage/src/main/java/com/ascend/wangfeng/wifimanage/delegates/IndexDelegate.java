package com.ascend.wangfeng.wifimanage.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.wifimanage.R;

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class IndexDelegate extends BottomItemDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
