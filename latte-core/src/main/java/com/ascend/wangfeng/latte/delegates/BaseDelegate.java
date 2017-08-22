package com.ascend.wangfeng.latte.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascend.wangfeng.latte.activities.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */

public abstract class BaseDelegate extends SwipeBackFragment{
    public abstract Object setLayout();
    private Unbinder mUnbinder =null;
    public abstract void onBindView(@Nullable Bundle saveInstanceState,View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView ;
        if (setLayout() instanceof Integer){
            rootView =inflater.inflate((Integer) setLayout(),container,false);
        }else if (setLayout() instanceof View){
            rootView = (View) setLayout();
        }else {
            throw new ClassCastException("setLayout() type must be int or View");
        }

            mUnbinder= ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);
        return rootView;
    }

    public final ProxyActivity getProxyActivity(){
        return (ProxyActivity) _mActivity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder!=null)mUnbinder.unbind();
    }
}
