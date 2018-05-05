package com.ascend.wangfeng.wifimanage.delegates.launch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.delegates.MainDelegate;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/5/5.
 * email 1040441325@qq.com
 */

public class LaunchDelegate extends LatteDelegate{
    @BindView(R.id.banner_launch)
    ConvenientBanner mBannerLaunch;
    @OnClick(R.id.btn_demo)
    void clickDemo(){
        pop();
        startWithPop(new MainDelegate());
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_launch;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        ArrayList<Integer> mImages = new ArrayList<>();
        mImages.add(R.drawable.test);
        mImages.add(R.drawable.phone);
        mBannerLaunch.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        },mImages).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
        .startTurning(2*1000);
    }

    @Override
    public void onPause() {
        mBannerLaunch.stopTurning();
        mBannerLaunch.destroyDrawingCache();

        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    class LocalImageHolderView implements Holder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            mImageView = new ImageView(context);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return mImageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            mImageView.setImageResource(data);
        }
    }
}
