package com.ascend.wangfeng.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.ec.main.EcBottomDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.latte.ui.recycler.MultipleRecyclerAdapter;
import com.ascend.wangfeng.latte.ui.refresh.RefreshHandler;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class IndexDelegate extends BottomItemDelegate {
    @BindView(R2.id.rv_index)
    RecyclerView mRvIndex;
    @BindView(R2.id.srl)
    SwipeRefreshLayout mSrl;
    @BindView(R2.id.ic_scan)
    IconTextView mIcScan;
    @BindView(R2.id.ic_message)
    IconTextView mIcMessage;
    @BindView(R2.id.tb)
    Toolbar mTb;
    private RefreshHandler mRefreshHandler;
    private MultipleRecyclerAdapter mAdapter;

    private void initRefreshLayout(){
        mSrl.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //动画设置 (大小变化),start高度,终止高度
        mSrl.setProgressViewOffset(true,120,300);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("index");
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        mRvIndex.setLayoutManager(manager);
        mRvIndex.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.app_background),4));
        final EcBottomDelegate ecBottomDelegate =getParentDelegate();
        mRvIndex.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mRefreshHandler= RefreshHandler.create(mSrl,mRvIndex, new IndexDataConverter());
    }

}
