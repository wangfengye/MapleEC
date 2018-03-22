package com.ascend.wangfeng.locationby4g;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.ascend.wangfeng.latte.activities.ProxyActivity;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.locationby4g.delegates.MainDelegate;
import com.ascend.wangfeng.locationby4g.services.SocketService;

import qiu.niorgai.StatusBarCompat;

public class MainActivity extends ProxyActivity {
    private SocketService mService = null;
    private boolean mIsBind = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar =getSupportActionBar();
        if (actionBar!=null)actionBar.hide();
        StatusBarCompat.translucentStatusBar(this,true);
        //初始化activity content;
        Latte.getConfigurator().withActivityContext(this);
        initServiceConnection();

    }

    public SocketService getService() {
        return mService;
    }

    private void initServiceConnection() {
         ServiceConnection coon = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mIsBind = true;
                mService = ((SocketService.SocketBinder)binder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIsBind =false;
            }
        };
        bindService(new Intent(MainActivity.this, SocketService.class),coon,BIND_AUTO_CREATE);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new MainDelegate();
    }


}
