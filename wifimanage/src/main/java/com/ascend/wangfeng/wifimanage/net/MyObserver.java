package com.ascend.wangfeng.wifimanage.net;

import com.ascend.wangfeng.latte.app.ConfigType;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.ui.loader.LatteLoader;
import com.ascend.wangfeng.wifimanage.MainActivity;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Response;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by fengye on 2018/5/28.
 * email 1040441325@qq.com
 */

public abstract class MyObserver<T extends Response> implements Observer<T> {

    public static final String TAG = MyObserver.class.getSimpleName();

    @Override
    public void onSubscribe(Disposable d) {
        if (showLoading())LatteLoader.showLoading(Latte.getConfiguration(ConfigType.ACTIVITY_CONTEXT));
        // d.dispose(); //解除订阅;
    }

    @Override
    public void onError(Throwable e) {
        if (showLoading()) LatteLoader.stopLoading();
        if (MainApp.mDemo) {
            MainApp.toast(R.string.demo_hint);
        } else {

            MainApp.toast(R.string.error);
        }

    }

    @Override
    public void onComplete() {
        if (showLoading()) LatteLoader.stopLoading();
    }

    @Override
    public void onNext(T t) {
        if (showLoading()) LatteLoader.stopLoading();
        switch (t.getStatusCode()) {
            case 200:
                onSuccess(t);
                break;
            case 400:// 请求异常
                MainApp.toast(t.getMessage());
                break;
            case 401://登录信息过期
                MainActivity activity = Latte.getConfiguration(ConfigType.ACTIVITY_CONTEXT);
                activity.showLoginOut();
                break;
        }
    }

    public abstract void onSuccess(T t);
    public boolean showLoading(){
        return false;
    }
}
