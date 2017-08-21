package com.ascend.wangfeng.latte.net.rx;


import android.content.Context;
import android.widget.Toast;

import com.ascend.wangfeng.latte.ui.LatteLoader;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by fengye on 2017/8/21.
 * email 1040441325@qq.com
 */

public abstract class BaseObserver<T> implements Observer<T> {
    public abstract Context getContext();
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        LatteLoader.showLoading(getContext());
    }

    @Override
    public void onError(@NonNull Throwable e) {
        LatteLoader.stopLoading();
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete() {
        LatteLoader.stopLoading();
    }

}