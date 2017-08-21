package com.ascend.wangfeng.mapleec;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.net.RestClient;
import com.ascend.wangfeng.latte.net.callback.ISuccess;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */

public class ExampleDelegate extends LatteDelegate{
    @Override
    public Object setLayout() {
        return com.ascend.wangfeng.mapleec.R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        testRx() ;
    }
    private void  testRestClient(){
        RestClient.builder()
                .url("index")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(_mActivity, response, Toast.LENGTH_SHORT).show();
                    }
                })
                .loader(getContext())
                .build().get();


    }
    //TODO:
    void  testRx(){
        RxRestClient.builder()
                .url("s")
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public Context getContext() {
                        return ExampleDelegate.this.getContext();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Toast.makeText(_mActivity, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
