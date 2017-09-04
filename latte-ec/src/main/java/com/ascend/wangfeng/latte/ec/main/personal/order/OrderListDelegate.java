package com.ascend.wangfeng.latte.ec.main.personal.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.ec.main.personal.UserDelegate;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

/**
 * Created by fengye on 2017/9/4.
 * email 1040441325@qq.com
 */

public class OrderListDelegate extends LatteDelegate {
    private String mType = null;
    @BindView(R2.id.tb_shop_cart)
    Toolbar mTbShopCart;
    @BindView(R2.id.tb_title)
    AppCompatTextView mTbTitle;
    @BindView(R2.id.rv_order_list)
    RecyclerView mRvOrderList;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args =getArguments();
        mType =args.getString(UserDelegate.ORDER_TYPE);
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        setToolbar();
        RxRestClient.builder()
                .url("orderlist")
                .params("type",mType)
                .build()
                .get()
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public Context getShowContext() {
                        return getContext();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        final LinearLayoutManager manager =new LinearLayoutManager(getContext());
                        mRvOrderList.setLayoutManager(manager);
                        final List<MultipleItemEntity> data =new OrderDataConverter().setJsonData(s).convert();
                        OrderListAdapter adapter = new OrderListAdapter(data);
                        mRvOrderList.setAdapter(adapter);
                    }
                });
    }

    private void setToolbar() {
        switch (mType){
            case UserDelegate.ALL:
                mTbTitle.setText(R.string.order_all);
                break;
        case UserDelegate.PAY:
                mTbTitle.setText(R.string.order_before_pay);
                break;
        case UserDelegate.RECEIVE:
                mTbTitle.setText(R.string.order_before_receive);
                break;
        case UserDelegate.EVALUATE:
                mTbTitle.setText(R.string.order_before_evaluate);
                break;
        case UserDelegate.MARKET:
                mTbTitle.setText(R.string.order_after_market);
                break;
        }
    }
}
