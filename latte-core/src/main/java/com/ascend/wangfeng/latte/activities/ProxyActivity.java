package com.ascend.wangfeng.latte.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ascend.wangfeng.latte.R;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */

public abstract class ProxyActivity extends SupportActivity {
    public abstract LatteDelegate setRootDelegate();

    /**
     *
     * @param savedInstanceState bug原因:重写的是该方法的重载方法
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(Bundle savedInstanceState){
        setContentView(R.layout.activity_main);

        if (findFragment(LatteDelegate.class) == null&&savedInstanceState==null){
            loadRootFragment(R.id.fl_container,setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
