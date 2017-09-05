package com.ascend.wangfeng.latte.ec.main.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.ascend.wangfeng.latte.util.DataFormat;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class ShoppingCartDelegate extends BottomItemDelegate implements ICartItemListener{
    @BindView(R2.id.toolbar_clear)
    TextView mToolbarClear;
    @BindView(R2.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R2.id.toolbar_remove)
    TextView mToolbarRemove;
    @BindView(R2.id.shop_cart)
    RecyclerView mShopCart;
    @BindView(R2.id.icon_cart_select_all)
    IconTextView mIconCartSelectAll;
    @BindView(R2.id.sum)
    AppCompatTextView mSum;
    @BindView(R2.id.cart_account)
    AppCompatTextView mCartAccount;
    @BindView(R2.id.stub_empty)
    ViewStubCompat mStubEmpty;
    private ShopCartAdapter mAdapter;
    private boolean isShowEmpty=false;

    /**
     * 全选
     */
    @OnClick(R2.id.icon_cart_select_all)
    void onClick() {
        final int tag = (int) mIconCartSelectAll.getTag();
        if (tag == 0) {
            mIconCartSelectAll.setTextColor(
                    ContextCompat.getColor(getContext(), R.color.app_main));
            mAdapter.setIsSelectedAll(true);
            mIconCartSelectAll.setTag(1);
        } else {
            mIconCartSelectAll.setTextColor(
                    ContextCompat.getColor(getContext(), R.color.gray));
            mAdapter.setIsSelectedAll(false);
            mIconCartSelectAll.setTag(0);


        }
    }


    @OnClick(R2.id.toolbar_remove)
    void onRemove() {
        mAdapter.remove();
        checkItemCount();
    }
    /*
     * 清空
     */
    @OnClick(R2.id.toolbar_clear)
    void onClear() {
        mAdapter.clear();
        checkItemCount();
    }

    /**
     * 创建订单
     */
    @OnClick(R2.id.cart_account)
    void onPay(){
        createOrder();
    }

    private void createOrder() {
        final String orderUrl="order";
        final WeakHashMap<String,Object> params =new WeakHashMap<>();
        params.put("userid","");
        params.put("amount",0.01);
        params.put("comment","test");
        params.put("type",1);
        params.put("ordertype",0);
        params.put("isanomymous",true);
        params.put("followeduser",0);
        RxRestClient.builder()
                .url(orderUrl)
                .params(params)
                .build()
                .put()
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {

                    }
                });
    }

    private void checkItemCount(){
        final int count =mAdapter.getItemCount();
        if (count ==0){
            if (isShowEmpty)return;
            final View stubView= mStubEmpty.inflate();
            final AppCompatTextView emptyView = (AppCompatTextView) stubView.findViewById(R.id.empty_hint);
            emptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goContainer(0);
                }
            });
            isShowEmpty =true;
            mShopCart.setVisibility(View.GONE);
        }else {
            mStubEmpty.setVisibility(View.GONE);
            mShopCart.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_shopping_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mIconCartSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RxRestClient.builder()
                .url("shopcart")
                .build()
                .get()
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        ArrayList<MultipleItemEntity> data = new ShopCartDataConverter().setJsonData(s).convert();
                        mAdapter = new ShopCartAdapter(data,ShoppingCartDelegate.this);
                        mShopCart.setLayoutManager(new LinearLayoutManager(getContext()));
                        mShopCart.setAdapter(mAdapter);
                        mShopCart.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                        checkItemCount();
                    }
                });
    }



    @Override
    public void onItemClick(double itemToatalPrice) {

    }

    @Override
    public void onSum(double sum) {
        mSum.setText(DataFormat.formatMoney(sum));
    }
}
