package com.ascend.wangfeng.latte.ui.banner;


import com.ascend.wangfeng.latte.ui.R;
import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.Transformer;

/**
 * @author maple on 2019/4/12 14:46.
 * @version v1.0
 * @see 1040441325@qq.com
 * 自定义的配套的banner样式配置
 */
public class BannerDecoration {
    /**
     * 指示器 长短相间
     * xml 属性修改
     * app:indicator_width="12dp"
     * app:indicator_height="9dp"
     * app:indicator_padding="0dp"
     * @param banner 要装饰的banner
     */
    public static void decorationLinePoint(Banner banner) {
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setBannerAnimation(Transformer.Default);
        banner.setIndicatorRes(R.drawable.indicator_selected, R.drawable.indicator_unselected);
    }
}
