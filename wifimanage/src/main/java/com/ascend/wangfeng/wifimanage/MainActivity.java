package com.ascend.wangfeng.wifimanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.ascend.wangfeng.latte.R;
import com.ascend.wangfeng.latte.activities.ProxyActivity;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.bean.User;
import com.ascend.wangfeng.wifimanage.delegates.MainDelegate;
import com.ascend.wangfeng.wifimanage.delegates.launch.LaunchDelegate;
import com.ascend.wangfeng.wifimanage.delegates.launch.LoginDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.net.SchedulerProvider;
import com.ascend.wangfeng.wifimanage.utils.SpKey;

public class MainActivity extends ProxyActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        // 透明状态栏
        // StatusBarCompat.translucentStatusBar(this, true);
        //初始化activity content;
        Latte.getConfigurator().withActivityContext(this);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        User user = LattePreference.getJson(SpKey.USER, User.class);
        if (user != null) {
            Client.getInstance().login(user.getBmac(),user.getUpasswd())
                    .compose(SchedulerProvider.applyHttp())
                    .subscribe(new MyObserver<Response<User>>() {
                        @Override
                        public void onSuccess(Response<User> response) {
                            //登录成功
                        }
                    });
            return MainDelegate.newInstance();
        }
        return new LaunchDelegate();
    }

    public void showLoginOut(){
        View view1 = findViewById(R.id.cl_root);
        Snackbar.make(view1,"登录过期",Snackbar.LENGTH_SHORT)
                .setAction("重新登录",view -> start(new LoginDelegate()))
                .show();
    }
}
