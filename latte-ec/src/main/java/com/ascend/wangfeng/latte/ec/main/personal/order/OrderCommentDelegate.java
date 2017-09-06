package com.ascend.wangfeng.latte.ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.ui.widget.AutoPhotoLayout;
import com.ascend.wangfeng.latte.ui.widget.StatLayout;
import com.ascend.wangfeng.latte.util.callback.CallbackManager;
import com.ascend.wangfeng.latte.util.callback.CallbackType;
import com.ascend.wangfeng.latte.util.callback.IGlobalCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class OrderCommentDelegate extends LatteDelegate {


    @BindView(R2.id.top_tv_comment_commit)
    AppCompatTextView mTopTvCommentCommit;
    @BindView(R2.id.tb_shop_cart)
    Toolbar mTbShopCart;
    @BindView(R2.id.img_order_comment)
    AppCompatImageView mImgOrderComment;
    @BindView(R2.id.tv_comment_title)
    TextView mTvCommentTitle;
    @BindView(R2.id.custom_star_layout)
    StatLayout mCustomStarLayout;
    @BindView(R2.id.et_order_comment)
    AppCompatEditText mEtOrderComment;
    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mCustomAutoPhotoLayout;

    @OnClick(R2.id.top_tv_comment_commit)
    void onClickCommit() {
        Toast.makeText(getContext(), "评分:" + mCustomStarLayout.getStarCount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mCustomAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(Uri args) {
                        mCustomAutoPhotoLayout.onCrop(args);
                    }
                });
    }
}
