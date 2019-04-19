package com.ascend.wangfeng.mapleec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.ascend.wangfeng.latte.ui.banner.BannerDecoration;
import com.ascend.wangfeng.latte.ui.banner.GlideImageLoader;

import com.ms.banner.Banner;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;

import java.util.Arrays;

import qiu.niorgai.StatusBarCompat;

/**
 * @author maple on 2019/4/12 10:07.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class BannerActivity extends AppCompatActivity {
    public static final String TITLE = "轮播图(com.ms.banner)";
    Banner mBanner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delegate_banner);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        StatusBarCompat.translucentStatusBar(this, true);
        String[] urls = new String[]{"https://alpha.wallhaven.cc/wallpapers/thumb/small/th-758147.jpg",
                "https://alpha.wallhaven.cc/wallpapers/thumb/small/th-755379.jpg",
                "https://alpha.wallhaven.cc/wallpapers/thumb/small/th-757427.jpg"};
        mBanner = findViewById(R.id.banner);
        BannerDecoration.decorationLinePoint(mBanner);
        mBanner.setPages(Arrays.asList(urls), GlideImageLoader::new);
        mBanner.start();
    }


}
