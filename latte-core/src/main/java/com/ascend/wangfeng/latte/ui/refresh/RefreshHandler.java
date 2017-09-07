package com.ascend.wangfeng.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;
import com.ascend.wangfeng.latte.ui.recycler.DataConverter;
import com.ascend.wangfeng.latte.ui.recycler.MultipleRecyclerAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final RecyclerView RECYCLERVIEW;
    private final PagingBean BEAN;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;


    private RefreshHandler(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, DataConverter converter, PagingBean bean) {
        this.REFRESH_LAYOUT = refreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, DataConverter c) {
        return new RefreshHandler(refreshLayout, recyclerView, c, new PagingBean());
    }

    public void firstPage(String url) {
        RxRestClient.builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        final JSONObject object = JSON.parseObject(s);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(s));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        REFRESH_LAYOUT.setRefreshing(false);

                    }
                });
    }

    private void pagging(String url) {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();
        if (mAdapter.getData().size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
        } else {
            RxRestClient.builder()
                    .url(url + index)
                    .build()
                    .get()
                    .subscribe(new BaseObserver<String>() {
                        @Override
                        public void onNext(@NonNull String s) {
                            mAdapter.addData(CONVERTER.setJsonData(s).convert());
                            BEAN.setCurrentCount(mAdapter.getData().size());
                            mAdapter.loadMoreComplete();
                            BEAN.addIndex();
                            REFRESH_LAYOUT.setRefreshing(false);
                        }
                    });
        }
    }

    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        firstPage("index");
    }



    @Override
    public void onLoadMoreRequested() {
        //测试模式会加载重复数据
        pagging("index?index=");
    }
}
