package com.ascend.wangfeng.latte.ec.main.personal.setttings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class AboutDelegate extends LatteDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
