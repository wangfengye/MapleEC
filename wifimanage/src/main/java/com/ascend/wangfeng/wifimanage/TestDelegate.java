package com.ascend.wangfeng.wifimanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.views.GithubActivityView;

import butterknife.BindView;

/**
 * Created by fengye on 2018/5/20.
 * email 1040441325@qq.com
 */

public class TestDelegate extends LatteDelegate{
    private static final String TAG = "sss";
    @BindView(R.id.github)
    GithubActivityView mGithub;
    @Override
    public Object setLayout() {
        return R.layout.delegate_test;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        Integer[][] data = new Integer[7][];
        //构造假数据
        for(int i = 0;i <7; i++){
            Integer[] column = new Integer[24];
            for(int j= 0;j <24; j++){
                column[j] = (int)(Math.random()*4);
            }
            data[i] = column;
        }
        mGithub.setData(data);
    }
}
