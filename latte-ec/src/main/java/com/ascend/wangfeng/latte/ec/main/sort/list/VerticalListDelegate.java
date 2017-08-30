package com.ascend.wangfeng.latte.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.ec.main.sort.SortDelegate;
import com.ascend.wangfeng.latte.ec.main.sort.SortRecyclerAdapter;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2017/8/28.
 * email 1040441325@qq.com
 */

public class VerticalListDelegate extends LatteDelegate {
    @BindView(R2.id.rv_vertiacl_menu_list)
    RecyclerView mRecyclerView;
    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }
    private void initRecyclerView(){
        final LinearLayoutManager manager =new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(null);
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
            initRecyclerView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RxRestClient.builder()
                .url("sortlist")
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        final ArrayList<MultipleItemEntity> data = new VerticalListDataonverter().setJsonData(s).convert();
                        final SortDelegate delegate =getParentDelegate();
                        final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data,delegate);
                        mRecyclerView.setAdapter(adapter);
                    }
                });
    }
}
