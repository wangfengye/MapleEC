package com.ascend.wangfeng.latte.ec.detail;

import android.support.v7.widget.AppCompatImageView;

import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.ascend.wangfeng.latte.ui.recycler.MultipleRecyclerAdapter;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by fengye on 2017/9/7.
 * email 1040441325@qq.com
 */

public class ImageAdapter extends MultipleRecyclerAdapter{

    protected ImageAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.SINGLE_BIG_IMAGE, R.layout.item_image);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        switch (helper.getItemViewType()){
            case ItemType.SINGLE_BIG_IMAGE:
                final String imageUrl = item.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .dontAnimate()
                        .into((AppCompatImageView) helper.getView(R.id.image_rv_item));
                break;
            default:
                break;
        }
    }
}
