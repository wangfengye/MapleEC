package com.ascend.wangfeng.latte.ec.main.personal;

import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

/**
 * Created by fengye on 2017/9/5.
 * email 1040441325@qq.com
 */

public class PersonalClickListener extends SimpleClickListener{
    private final LatteDelegate DELEGATE;

    public PersonalClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        switch (bean.getId()){
            case 1:
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                break;
            default:
                break;
        }
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
