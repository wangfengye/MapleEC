package com.ascend.wangfeng.latte.ec.main.index.search;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ascend.wangfeng.latte.ui.recycler.DataConverter;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.ascend.wangfeng.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class SearchDataConverter extends DataConverter{
    public static final String TAG_SEACH_HISTORY ="search_history";
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final String json = LattePreference.getCustomAppProfile(TAG_SEACH_HISTORY);
        if (json!=null&& !json.isEmpty()){
            final JSONArray array = JSON.parseArray(json);
            int size =array.size();
            for (int i = 0; i < size; i++) {
                final String historyItemText = array.getString(i);
                final MultipleItemEntity entity =new MultipleItemEntity.Builder()
                        .setItemType(ItemType.ITEM_SEARCH)
                        .setField(MultipleFields.TEXT, historyItemText)
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
