package com.ascend.wangfeng.latte.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.ascend.wangfeng.latte.R;
import com.ascend.wangfeng.latte.ui.banner.BannerCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity,MultipleViewHolder>
implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {
    //防止重复加载
    private boolean mIsInitBanner =false;

    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }
    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data){
        return new MultipleRecyclerAdapter(data);
    }
    public static MultipleRecyclerAdapter create(DataConverter converter){
        return new MultipleRecyclerAdapter(converter.convert());
    }
    private void init(){
        //设置不同 item布局
        addItemType(ItemType.TEXT,R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE,R.layout.item_multiple_image);
        addItemType(ItemType.IMAGE_TEXT,R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER,R.layout.item_multiple_banner);
        //设置宽度监听
        setSpanSizeLookup(this);
        //多次动画
        openLoadAnimation();
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        final String text;
        final String imgUrl;
        final ArrayList<String> bannerImages;
        switch (helper.getItemViewType()){
            case ItemType.TEXT:
                text= item.getField(MultipleFields.TEXT);
                helper.setText(R.id.text_single,text);
                break;
            case ItemType.IMAGE:
                imgUrl =item.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imgUrl)
                        .into((ImageView) helper.getView(R.id.img_single));
                break;
            case ItemType.IMAGE_TEXT:
                text= item.getField(MultipleFields.TEXT);
                helper.setText(R.id.text_single,text);
                imgUrl =item.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imgUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .into((ImageView) helper.getView(R.id.img_single));
                break;
            case ItemType.BANNER:
                if(!mIsInitBanner){
                    bannerImages = (ArrayList<String>) item.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner =helper.getView(R.id.banner_recycler_banner);
                    BannerCreator.setDefault(convenientBanner,bannerImages,this);
                    mIsInitBanner =true;
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        Logger.i("getspansize lg:"+getData().get(position).getField(MultipleFields.SPAN_SIZE));
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
