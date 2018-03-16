package com.ascend.wangfeng.wifimap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.ascend.wangfeng.latte.activities.ProxyActivity;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimap.delegates.tables.LineDelegate;

import qiu.niorgai.StatusBarCompat;

public class MainActivity extends ProxyActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar =getSupportActionBar();
        if (actionBar!=null)actionBar.hide();
        StatusBarCompat.translucentStatusBar(this,true);
        //初始化activity content;
        Latte.getConfigurator().withActivityContext(this);
    }
    @Override
    public LatteDelegate setRootDelegate() {
        // return new MainDelegate();
        return  new LineDelegate();
    }
}
