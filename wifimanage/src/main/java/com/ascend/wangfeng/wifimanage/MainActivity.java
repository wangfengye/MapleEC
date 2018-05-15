package com.ascend.wangfeng.wifimanage;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.ascend.wangfeng.latte.activities.ProxyActivity;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.delegates.MainDelegate;

import qiu.niorgai.StatusBarCompat;

public class MainActivity extends ProxyActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        super.onCreate(savedInstanceState);
        final ActionBar actionBar =getSupportActionBar();
        if (actionBar!=null)actionBar.hide();
        StatusBarCompat.translucentStatusBar(this,true);
        //初始化activity content;
        Latte.getConfigurator().withActivityContext(this);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        // return new PlanDetailDelegate();
        return new MainDelegate();
    }
}
