package com.ascend.wangfeng.latte.delegates;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */

public abstract class LatteDelegate extends BaseDelegate{
    protected CompositeDisposable mDisposable = new CompositeDisposable();
    public <T extends LatteDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }

    /**
     * 添加观察者
     * @param observer
     */
    protected void add(DisposableObserver observer){
        mDisposable.add(observer);
    }

    @Override
    public void onDestroyView() {
        // 解除订阅
        if (mDisposable!=null)mDisposable.clear();
        super.onDestroyView();
    }
}
