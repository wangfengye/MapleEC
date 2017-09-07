package com.ascend.wangfeng.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.util.DataFormat;

import butterknife.BindView;

/**
 * Created by fengye on 2017/9/7.
 * email 1040441325@qq.com
 */

public class GoodsInfoDelegate extends LatteDelegate {
    public static final String INFO = "goods_info";
    @BindView(R2.id.tv_goods_info_title)
    AppCompatTextView mTvGoodsInfoTitle;
    @BindView(R2.id.tv_goods_info_desc)
    AppCompatTextView mTvGoodsInfoDesc;
    @BindView(R2.id.tv_goods_info_price)
    AppCompatTextView mTvGoodsInfoPrice;
    private JSONObject mInfo;

    public static GoodsInfoDelegate newInstance(String info) {
        final Bundle args = new Bundle();
        GoodsInfoDelegate delegate = new GoodsInfoDelegate();
        args.putString(INFO, info);
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null && args.getString(INFO) != null && !args.getString(INFO).isEmpty()) {
            mInfo = JSON.parseObject(args.getString(INFO));
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_info;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        if (mInfo == null) return;
        final String name = mInfo.getString("name");
        final String desc = mInfo.getString("description");
        final double price = mInfo.getDouble("price");
        mTvGoodsInfoTitle.setText(name);
        mTvGoodsInfoDesc.setText(desc);
        mTvGoodsInfoPrice.setText(DataFormat.formatMoney(price));
    }


}
