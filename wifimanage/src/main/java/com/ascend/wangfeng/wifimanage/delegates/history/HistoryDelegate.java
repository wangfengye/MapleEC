package com.ascend.wangfeng.wifimanage.delegates.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.wifimanage.R;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 * 历史记录(动态)
 */

public class HistoryDelegate extends BottomItemDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_history;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
