package com.ascend.wangfeng.latte.ec.main.index.search;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.ascend.wangfeng.latte.ui.recycler.MultipleRecyclerAdapter;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.latte.util.storage.LattePreference;

import java.util.List;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class SearchAdapter extends MultipleRecyclerAdapter {
    protected SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.ITEM_SEARCH, R.layout.item_search);
        addItemType(ItemType.ITEM_DELETE, R.layout.item_delete);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        switch (helper.getItemViewType()) {
            case ItemType.ITEM_SEARCH:
                final AppCompatTextView tvSearchItem = helper.getView(R.id.tv_search_item);
                final String history = item.getField(MultipleFields.TEXT);
                tvSearchItem.setText(history);
                break;
            case ItemType.ITEM_DELETE:
                final LinearLayoutCompat layout= helper.getView(R.id.delete);
                final String content = item.getField(MultipleFields.TEXT);
                helper.setText(R.id.delete_text,content);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LattePreference.removeCustomAppProfile(SearchDataConverter.TAG_SEACH_HISTORY);
                        getData().clear();
                        notifyDataSetChanged();
                    }
                });
                break;
            default:
                break;
        }
    }

}
