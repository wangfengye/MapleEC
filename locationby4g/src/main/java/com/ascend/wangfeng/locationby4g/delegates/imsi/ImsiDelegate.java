package com.ascend.wangfeng.locationby4g.delegates.imsi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxBus;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.services.bean.CellMeaureAck;
import com.ascend.wangfeng.locationby4g.services.rxbus.CellMeaureAckEvent;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 */

public class ImsiDelegate extends BottomItemDelegate{
    @BindView(R.id.rv_imsi)
    RecyclerView mRvImsi;
    private ArrayList<CellMeaureAckEntry> mData;
    private ImsiAdapter mAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_imsi;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initRv();
        RxBus.getDefault().toObservable(CellMeaureAckEvent.class)
                .subscribe(new BaseObserver<CellMeaureAckEvent>() {
                    @Override
                    public void onNext(CellMeaureAckEvent ack) {
                        CellMeaureAck data = ack.getAck();
                        for (int i = 0; i < mData.size(); i++) {
                            if (mData.get(i).getImsi().equals(data.getImsi())){
                                mData.get(i).copy(data);
                                updateView();
                                return;
                            }
                        }
                        mData.add(CellMeaureAckEntry.copyStatic(data));
                        updateView();
                    }
                });
    }

    private void initRv() {
        mData = new ArrayList<>();
        mAdapter = new ImsiAdapter(mData);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvImsi.setLayoutManager(manager);
        mRvImsi.setAdapter(mAdapter);
        mRvImsi.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.darker_gray), 1));
    }
    private void updateView(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
