package com.ascend.wangfeng.latte.ec.main.index.search;

import android.support.v7.widget.AppCompatTextView;

import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.ascend.wangfeng.latte.ui.recycler.MultipleRecyclerAdapter;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class SearchAdapter extends MultipleRecyclerAdapter{
    protected SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
      addItemType(ItemType.ITEM_SEARCH, R.layout.item_search);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        switch (helper.getItemViewType()){
            case ItemType.ITEM_SEARCH:
                final AppCompatTextView tvSearchItem = helper.getView(R.id.tv_search_item);
                final String history = item.getField(MultipleFields.TEXT);
                tvSearchItem.setText(history);
                break;
            default:
                break;
        }
    }
}
