package com.ascend.wangfeng.latte.ui.banner;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ascend.wangfeng.latte.R;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class BannerCreator {
    //bannner rate
    private static final int RATE= 30000;
    public static void setDefault(ConvenientBanner<String> convenientBanner, ArrayList<String> banners
    , final OnItemClickListener listener){
        convenientBanner.setPages(new HolderCreator(),banners)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(listener)
                .startTurning(RATE)
                .setPageTransformer(new DefaultTransformer())
                .setCanLoop(true);


    }
}
