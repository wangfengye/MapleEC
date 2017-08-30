package com.ascend.wangfeng.latte.ec.main.sort.content;

import android.widget.ImageView;

import com.ascend.wangfeng.latte.ec.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean,BaseViewHolder>{
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }


    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.header,item.header);
        helper.setVisible(R.id.more,item.isMore());
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        //item.t back  SectionBean type
        final String thumb = item.t.getGoodsThumb();
        final String name= item.t.getGoodsName();
        final int goodsId = item.t.getGoodsId();
        final  SectionContentItemEntity entity =item.t;
        helper.setText(R.id.tv,name);
        Glide.with(mContext)
                .load(thumb)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .into((ImageView) helper.getView(R.id.iv));
    }
}
