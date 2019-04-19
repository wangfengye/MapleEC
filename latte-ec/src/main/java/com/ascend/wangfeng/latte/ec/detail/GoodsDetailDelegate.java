package com.ascend.wangfeng.latte.ec.detail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;

import com.ascend.wangfeng.latte.ui.widget.CircleTextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.joanzapata.iconify.widget.IconTextView;
import com.ms.banner.holder.HolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2017/8/28.
 * email 1040441325@qq.com
 */

public class GoodsDetailDelegate extends LatteDelegate implements OnOffsetChangedListener{
    public static final String GOOD_ID = "GOODS_ID";
    @BindView(R2.id.detail_banner)
    ConvenientBanner mDetailBanner;
    @BindView(R2.id.frame_goods_info)
    ContentFrameLayout mFrameGoodsInfo;
    @BindView(R2.id.icon_goods_back)
    IconTextView mIconGoodsBack;
    @BindView(R2.id.tv_detail_title_text)
    AppCompatTextView mTvDetailTitleText;
    @BindView(R2.id.goods_detail_toolbar)
    Toolbar mGoodsDetailToolbar;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarDetail;
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppBarDetail;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager;
    @BindView(R2.id.icon_favor)
    IconTextView mIconFavor;
    @BindView(R2.id.rl_favor)
    RelativeLayout mRlFavor;
    @BindView(R2.id.icon_shop_cart)
    IconTextView mIconShopCart;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mTvShoppingCartAmount;
    @BindView(R2.id.rl_shop_cart)
    RelativeLayout mRlShopCart;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRlAddShopCart;
    @BindView(R2.id.ll_bottom)
    LinearLayoutCompat mLlBottom;
    private int mGoodsId;
    private boolean isFavor;

    private int mGoodsNum;
    @OnClick(R2.id.rl_favor)
    void  onClickFacour(){
        isFavor=!isFavor;
        if (isFavor){
        mIconFavor.setText("{fa-heart}");
        mIconFavor.setTextColor(getResources().getColor(R.color.app_main));}
        else {
            mIconFavor.setText("{fa-heart-o}");
            mIconFavor.setTextColor(getResources().getColor(R.color.gray));
        }
    }
    @OnClick(R2.id.rl_add_shop_cart)
    void onClickAddGoods(){
        mGoodsNum++;
        resetCircleText();
    }
    public static GoodsDetailDelegate newInstance(@NonNull int goodsId){
        final Bundle args = new Bundle();
        args.putInt(GOOD_ID,goodsId);
        GoodsDetailDelegate delegate = new GoodsDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args!=null){
            mGoodsId = args.getInt(GOOD_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mCollapsingToolbarDetail.setContentScrimColor(Color.WHITE);
        mAppBarDetail.addOnOffsetChangedListener(this);
        initData();
        initTabLayout();

        mTvShoppingCartAmount.setCircleBackground(Color.RED);
        resetCircleText();

    }

    private void resetCircleText() {
        if (mGoodsNum>0){
        mTvShoppingCartAmount.setText(String.valueOf(mGoodsNum));
        mTvShoppingCartAmount.setVisibility(View.VISIBLE);}
        else {
            mTvShoppingCartAmount.setVisibility(View.GONE);
        }
    }

    private void initTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(
                ContextCompat.getColor(getContext(),R.color.app_main));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void initData(){
        RxRestClient.builder()
                .url("gooddetail")
              //  .params("goods_id",mGoodsId)
                .build()
                .get()
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String s) {
                        final JSONObject data = JSON.parseObject(s).getJSONObject("data");
                        initBanner(data);
                        initGoodsInfo(data);
                        initPager(data);
                    }
                });
    }

    private void initPager(JSONObject data) {
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(),data);
        mViewPager.setAdapter(adapter);
    }

    private void initGoodsInfo(JSONObject data) {
        String info =data.toJSONString();
        getSupportDelegate().loadRootFragment(R.id.frame_goods_info,
                GoodsInfoDelegate.newInstance(info));
    }


    private void initBanner(JSONObject data) {
        final JSONArray array = data.getJSONArray("banners");
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            images.add(array.getString(i));
        }
      /*  mDetailBanner.setPages(new HolderCreator(),images)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);*/
    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }
}
