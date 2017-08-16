package com.ascend.wangfeng.mapleec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.net.RestClient;
import com.ascend.wangfeng.latte.net.callback.ISuccess;

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
        testRestClient();
    }
    private void  testRestClient(){
        RestClient.builder()
                .url("api/data/%E7%A6%8F%E5%88%A9/2/1")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(_mActivity, response, Toast.LENGTH_SHORT).show();
                    }
                })
                .loader(getContext())
                .build().get();


    }
}
