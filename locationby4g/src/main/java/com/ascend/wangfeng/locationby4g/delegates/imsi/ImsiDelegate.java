package com.ascend.wangfeng.locationby4g.delegates.imsi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.rxbus.RxBus;
import com.ascend.wangfeng.locationby4g.rxbus.Subscribe;
import com.ascend.wangfeng.locationby4g.services.rxbus.CellMeaureAckListEvent;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 */

public class ImsiDelegate extends BottomItemDelegate {
    public static final String TAG = ImsiDelegate.class.getSimpleName();
    @BindView(R.id.rv_imsi)
    RecyclerView mRvImsi;
    private ArrayList<CellMeaureAckEntry> mData = new ArrayList<>();
    private ImsiAdapter mAdapter;


    @Override
    public Object setLayout() {
        return R.layout.delegate_imsi;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initRv();
        RxBus.getDefault().register(this);
    }
    @Subscribe
    public void receive(CellMeaureAckListEvent event){
        mData.clear();
        mData.addAll(event.getEntries());
        Log.i(TAG, "onNext: " + mData.toString());
        updateView();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    private void initRv() {
        mData = new ArrayList<>();
        mAdapter = new ImsiAdapter(mData, this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvImsi.setLayoutManager(manager);
        mRvImsi.setAdapter(mAdapter);
        mRvImsi.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.darker_gray), 1));
    }

    private void updateView() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroy() {
        RxBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
