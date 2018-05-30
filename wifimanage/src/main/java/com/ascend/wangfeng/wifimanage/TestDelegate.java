package com.ascend.wangfeng.wifimanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.ascend.wangfeng.wifimanage.bean.User;
import com.ascend.wangfeng.wifimanage.utils.SpKey;
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
        User user = new User();
        user.setName("maple");
        user.setPassword("1234");
        LattePreference.setJson(SpKey.USER,user);
        User user1= (User) LattePreference.getJson(SpKey.USER,User.class);
        user1.toString();
        Log.i(TAG, "onBindView: "+ user1.toString());
    }
}
