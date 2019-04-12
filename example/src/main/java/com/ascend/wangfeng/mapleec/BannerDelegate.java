package com.ascend.wangfeng.mapleec;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.banner.BannerDecoration;
import com.ascend.wangfeng.latte.ui.banner.GlideImageLoader;
import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.Transformer;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;


import java.util.Arrays;

import butterknife.BindView;

/**
 * @author maple on 2019/4/12 10:07.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class BannerDelegate extends LatteDelegate{
    @BindView(R.id.banner)
    Banner mBanner;
    @Override
    public Object setLayout() {
        return R.layout.delegate_banner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        String[] urls = new String[]{"https://alpha.wallhaven.cc/wallpapers/thumb/small/th-758147.jpg",
                "https://alpha.wallhaven.cc/wallpapers/thumb/small/th-755379.jpg",
                "https://alpha.wallhaven.cc/wallpapers/thumb/small/th-757427.jpg"};
        mBanner=rootView.findViewById(R.id.banner);
        BannerDecoration.decorationLinePoint(mBanner);
        mBanner.setPages(Arrays.asList(urls),  GlideImageLoader::new);
        mBanner.start();

    }
}
