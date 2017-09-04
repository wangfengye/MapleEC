package com.ascend.wangfeng.latte.ec.main.personal.order;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.ascend.wangfeng.latte.ui.recycler.MultipleRecyclerAdapter;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.latte.util.DataFormat;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by fengye on 2017/9/4.
 * email 1040441325@qq.com
 */

public class OrderListAdapter extends MultipleRecyclerAdapter{
    protected OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.ITEM_ORDER_LIST, R.layout.item_order_list);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()){
            case ItemType.ITEM_ORDER_LIST:
                final AppCompatImageView imageView =helper.getView(R.id.image_order_list);
                final AppCompatTextView title = helper.getView(R.id.tv_order_list_title);
                final AppCompatTextView price = helper.getView(R.id.tv_order_list_price);
                final AppCompatTextView time= helper.getView(R.id.tv_order_list_time);

                final String imageVal = item.getField(MultipleFields.IMAGE_URL);
                final String titleVal = item.getField(MultipleFields.TITLE);
                final double priceVal = item.getField(MultipleFields.PRICE);
                final String timeVal = item.getField(MultipleFields.TIME);
                Glide.with(mContext)
                        .load(imageVal)
                        .into(imageView);
                title.setText(titleVal);
                price.setText(DataFormat.formatMoney(priceVal));
                time.setText(timeVal);
                break;
            default:
                break;
        }
    }
}
