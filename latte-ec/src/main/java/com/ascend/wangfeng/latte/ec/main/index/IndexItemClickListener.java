package com.ascend.wangfeng.latte.ec.main.index;

import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.detail.GoodsDetailDelegate;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

/**
 * Created by fengye on 2017/8/28.
 * email 1040441325@qq.com
 */

public class IndexItemClickListener extends SimpleClickListener{
    private final LatteDelegate DELEGATE;

    private IndexItemClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }
    public static IndexItemClickListener create(LatteDelegate delegate){
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity item =(MultipleItemEntity)(baseQuickAdapter.getData().get(position));
        final int id = item.getField(MultipleFields.ID);
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.newInstance(id);
        DELEGATE.start(delegate);
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
