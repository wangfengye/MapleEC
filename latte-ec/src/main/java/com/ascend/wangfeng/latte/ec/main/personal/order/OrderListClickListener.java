package com.ascend.wangfeng.latte.ec.main.personal.order;

import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class OrderListClickListener  extends SimpleClickListener{
    private final LatteDelegate mDelegate;

    public OrderListClickListener(LatteDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mDelegate.getSupportDelegate().start(new OrderCommentDelegate());
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
