package com.ascend.wangfeng.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.ec.main.personal.address.AddressDelegate;
import com.ascend.wangfeng.latte.ec.main.personal.order.OrderListDelegate;
import com.ascend.wangfeng.latte.ec.main.personal.setttings.SettingsDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class UserDelegate extends BottomItemDelegate {
    public static final String ORDER_TYPE = "orderType";
    public static final String ALL = "all";
    public static final String PAY = "pay";
    public static final String RECEIVE = "receive";
    public static final String EVALUATE = "evaluate";
    public static final String MARKET = "afterMarket";
    private Bundle mArgs = null;
    @BindView(R2.id.img_user_avatar)
    CircleImageView mImgUserAvatar;
    @BindView(R2.id.tv_all_order)
    TextView mTvAllOrder;
    @BindView(R2.id.tv_all_account_arrow)
    IconTextView mTvAllAccountArrow;
    @BindView(R2.id.ll_pay)
    LinearLayout mLlPay;
    @BindView(R2.id.textView)
    TextView mTextView;
    @BindView(R2.id.ll_receive)
    LinearLayout mLlReceive;
    @BindView(R2.id.ll_evaluate)
    LinearLayout mLlEvaluate;
    @BindView(R2.id.ll_after_market)
    LinearLayout mLlAfterMarket;
    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRvPersonalSetting;

    @Override
    public Object setLayout() {
        return R.layout.delegate_user;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mArgs = new Bundle();
        ListBean address = new ListBean.Builder().setItemType(ListBean.TYPE_NORMAL)
                .setId(1)
                .setText("收货地址")
                .setDelegate(new AddressDelegate())
                .build();
        ListBean settings = new ListBean.Builder().setItemType(ListBean.TYPE_NORMAL)
                .setId(1)
                .setText("系统设置")
                .setDelegate(new SettingsDelegate())
                .build();
        ArrayList<ListBean> been = new ArrayList<>();
        been.add(address);
        been.add(settings);
        ListAdapter adapter = new ListAdapter(been);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvPersonalSetting.setLayoutManager(manager);
        mRvPersonalSetting.setAdapter(adapter);
        mRvPersonalSetting.addOnItemTouchListener(new PersonalClickListener(this));
        mRvPersonalSetting.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.gray), 1));
    }

    @OnClick(R2.id.tv_all_order)
    void onClickAllOrder() {
        startOrderListByType(ALL);
    }

    @OnClick(R2.id.ll_pay)
    void onClickPay() {
        startOrderListByType(PAY);
    }

    @OnClick(R2.id.ll_receive)
    void onClickReceive() {
        startOrderListByType(RECEIVE);
    }

    @OnClick(R2.id.ll_evaluate)
    void onClickEvaluate() {
        startOrderListByType(EVALUATE);
    }

    @OnClick(R2.id.ll_after_market)
    void onClickMarket(){
        startOrderListByType(MARKET);
    }
    @OnClick(R2.id.img_user_avatar)
    void onClickUserAvatar(){
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }

    private void startOrderListByType(String orderType) {
        mArgs.putString(ORDER_TYPE, orderType);
        final OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(mArgs);
        getParentDelegate().getSupportDelegate().start(delegate);
    }

}
