package com.ascend.wangfeng.wifimanage.delegates.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.bean.User;
import com.ascend.wangfeng.wifimanage.delegates.MainDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.net.SchedulerProvider;
import com.ascend.wangfeng.wifimanage.utils.SpKey;

/**
 * Created by fengye on 2018/6/6.
 * email 1040441325@qq.com
 */

public class Launch1Delegate extends LatteDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_lanunch_1;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        User user = LattePreference.getJson(SpKey.USER, User.class);
        Client.getInstance().login(user.getBmac(),user.getUpasswd())
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<User>>() {
                    @Override
                    public void onSuccess(Response<User> response) {//重写onNext后,该方法无效
                    }

                    @Override
                    public void onNext(Response<User> response) {
                        super.onNext(response);
                        if (response.getStatusCode() == 200){
                            startWithPop(MainDelegate.newInstance());
                        }else {
                            startWithPop(new LoginDelegate());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        startWithPop(new LoginDelegate());
                    }
                });
    }
}
