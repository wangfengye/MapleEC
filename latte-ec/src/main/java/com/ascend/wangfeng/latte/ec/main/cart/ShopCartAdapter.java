package com.ascend.wangfeng.latte.ec.main.cart;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.ascend.wangfeng.latte.ui.recycler.MultipleRecyclerAdapter;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.latte.util.DataFormat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengye on 2017/8/31.
 * email 1040441325@qq.com
 */

public class ShopCartAdapter extends MultipleRecyclerAdapter {
    private double mTotalPrice;
    private boolean mIsSelectedAll = false;
    private ICartItemListener mListener;

    protected ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
        //初始化总价
        initSum();
        if (mListener != null) {
            mListener.onSum(mTotalPrice);
        }
    }
    protected ShopCartAdapter(List<MultipleItemEntity> data,ICartItemListener listener) {
        super(data);
        this.mListener =listener;
        addItemType(ItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
        //初始化总价
        initSum();
        if (mListener != null) {
            mListener.onSum(mTotalPrice);
        }

    }

    private void initSum() {
        List<MultipleItemEntity> data = getData();
        mTotalPrice = 0;
        for (MultipleItemEntity entity : data) {
            final double price = entity.getField(MultipleFields.PRICE);
            final int count = entity.getField(MultipleFields.COUNT);
            final double total = price * count;
            mTotalPrice += total;
        }
    }

    public void setCartItemListener(ICartItemListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case ItemType.SHOP_CART_ITEM:
                item.setField(MultipleFields.TAG, mIsSelectedAll);
                final String thumb = item.getField(MultipleFields.IMAGE_URL);
                final String desc = item.getField(MultipleFields.DESC);
                final String title = item.getField(MultipleFields.TITLE);
                final int id = item.getField(MultipleFields.ID);
                final int count = item.getField(MultipleFields.COUNT);
                final double price = item.getField(MultipleFields.PRICE);
                final boolean selected = item.getField(MultipleFields.TAG);

                final AppCompatImageView imgThumb = helper.getView(R.id.image_item_shop_cart);
                final AppCompatTextView titleView = helper.getView(R.id.title_item_shop_cart);
                final AppCompatTextView descView = helper.getView(R.id.desc_item_shop_cart);
                final AppCompatTextView priceView = helper.getView(R.id.price_item_shop_cart);
                final IconTextView icMinus = helper.getView(R.id.icon_item_minus);
                final IconTextView icPlus = helper.getView(R.id.icon_item_plus);
                final AppCompatTextView countView = helper.getView(R.id.count_item_shop_cart);
                final IconTextView icSelected = helper.getView(R.id.icon_item_shop_cart);
                Glide.with(mContext)
                        .load(thumb)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .dontAnimate()
                        .into(imgThumb);
                titleView.setText(title);
                descView.setText(desc);
                priceView.setText(DataFormat.formatMoney(price));
                countView.setText(String.valueOf(count));
                setSelectedColor(icSelected, selected);
                //左侧勾选事件
                icSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final boolean cuurentSelected = item.getField(MultipleFields.TAG);
                        item.setField(MultipleFields.TAG, !cuurentSelected);
                        setSelectedColor(icSelected, !cuurentSelected);
                    }
                });
                icMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //这里只在客户端增减,退出时需提交数据变更
                        final int count = item.getField(MultipleFields.COUNT);
                        if (count > 1) {
                            item.setField(MultipleFields.COUNT, count - 1);
                            countView.setText(String.valueOf(count - 1));
                            mTotalPrice = mTotalPrice - price;
                            if (mListener != null) {
                                mListener.onItemClick(price * (count - 1));
                                mListener.onSum(mTotalPrice);
                            }
                        } else {
                            Toast.makeText(mContext, "size has least", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                icPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int count = item.getField(MultipleFields.COUNT);
                        item.setField(MultipleFields.COUNT, count + 1);
                        countView.setText(String.valueOf(count + 1));
                        mTotalPrice = mTotalPrice + price;
                        if (mListener != null) {
                            mListener.onItemClick(price * (count + 1));
                            mListener.onSum(mTotalPrice);
                        }

                    }
                });
                break;
            default:
                break;

        }
    }

    private void setSelectedColor(IconTextView icon, boolean selected) {
        if (selected) {
            icon.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
        } else {
            icon.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
        }
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        mIsSelectedAll = isSelectedAll;
        notifyItemRangeChanged(0, getItemCount());
    }

    public void clear() {
        getData().clear();
        notifyDataSetChanged();
        mTotalPrice = 0;
        if (mListener != null) {
            mListener.onSum(mTotalPrice);
        }
    }

    public void remove() {
        final List<MultipleItemEntity> data = getData();
        List<MultipleItemEntity> deleteData = new ArrayList<>();
        for (int i = data.size() - 1; i >= 0; i--) {
            if (data.get(i).getField(MultipleFields.TAG)) {
                remove(i);
                notifyItemRangeChanged(i, getItemCount());
            }
        }
        initSum();
        if (mListener != null) {
            mListener.onSum(mTotalPrice);
        }
    }
}
