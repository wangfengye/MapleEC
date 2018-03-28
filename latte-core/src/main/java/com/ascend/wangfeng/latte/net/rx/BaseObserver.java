package com.ascend.wangfeng.latte.net.rx;


import android.content.Context;
import android.widget.Toast;

import com.ascend.wangfeng.latte.app.ConfigType;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.ui.loader.LatteLoader;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by fengye on 2017/8/21.
 * email 1040441325@qq.com
 */

public abstract class BaseObserver<T> implements Observer<T> {
    public Context getShowContext(){
        return Latte.getConfiguration(ConfigType.ACTIVITY_CONTEXT);
    }
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        // 开启动画
      /*  if (getShowContext()!=null){
        LatteLoader.showLoading(getShowContext());}*/
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (getShowContext()!=null){
        LatteLoader.stopLoading();
        Toast.makeText(getShowContext(), e.getMessage(), Toast.LENGTH_SHORT).show();}
    }

    @Override
    public void onComplete() {
        LatteLoader.stopLoading();
    }

}