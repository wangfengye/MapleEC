package com.ascend.wangfeng.wifimanage;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.ascend.wangfeng.latte.activities.ProxyActivity;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.bean.User;
import com.ascend.wangfeng.wifimanage.delegates.MainDelegate;
import com.ascend.wangfeng.wifimanage.delegates.launch.LaunchDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.net.SchedulerProvider;
import com.ascend.wangfeng.wifimanage.utils.SpKey;

import qiu.niorgai.StatusBarCompat;

public class MainActivity extends ProxyActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        StatusBarCompat.translucentStatusBar(this, true);
        //初始化activity content;
        Latte.getConfigurator().withActivityContext(this);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        User user = (User) LattePreference.getJson(SpKey.USER, User.class);
        if (user != null) {
            Client.getInstance().login(user.getName(),user.getPassword())
                    .compose(SchedulerProvider.applyHttp())
                    .subscribe(new MyObserver<Response<String>>() {
                        @Override
                        public void onNext(Response<String> response) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            MainApp.toast(R.string.login_outtime);
                        }
                    });
            return MainDelegate.newInstance();
        }
        return new LaunchDelegate();

    }
}
