package com.ascend.wangfeng.latte.ui.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ascend.wangfeng.latte.ui.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ms.banner.holder.BannerViewHolder;
;


/**
 * @author maple on 2019/4/12 9:57.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class GlideImageLoader implements BannerViewHolder<String> {
    private int rounded = 20;
    private ImageView mImageView;

    public GlideImageLoader() {
    }

    public GlideImageLoader(int rounded) {
        this.rounded = rounded;
    }


    @Override
    public View createView(Context context) {

        mImageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        mImageView.setLayoutParams(params);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return mImageView;
    }

    @Override
    public void onBind(Context context, int position, String data) {
        RequestOptions options = new RequestOptions();
        if (rounded > 0) options.transforms(new CenterCrop(), new RoundedCorners(rounded));
        Glide.with(context).load(data).apply(options).into(mImageView);
    }
}
