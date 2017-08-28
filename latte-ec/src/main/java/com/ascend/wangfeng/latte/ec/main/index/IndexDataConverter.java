package com.ascend.wangfeng.latte.ec.main.index;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.ui.recycler.DataConverter;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class IndexDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSONArray.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String imageUrl = data.getString("imageUrl");
            final String text = data.getString("text");
            final int spanSize = data.getInteger("spanSize");
            final int id = data.getInteger("goodsId");
            final JSONArray banners = data.getJSONArray("banners");
            final ArrayList<String> bannerImages = new ArrayList<>();
            int type = 0;
            if (imageUrl == null && text != null) {
                type = ItemType.TEXT;
            } else if (imageUrl != null && text == null) {
                type = ItemType.IMAGE;
            } else if (imageUrl != null) {
                type = ItemType.IMAGE_TEXT;
            } else if (banners != null) {
                type = ItemType.BANNER;
                for (int j = 0; j < banners.size(); j++) {
                    bannerImages.add(banners.getString(j));
                }
            }
            final MultipleItemEntity entity = new MultipleItemEntity.Builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.SPAN_SIZE, spanSize)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, text)
                    .setField(MultipleFields.IMAGE_URL, imageUrl)
                    .setField(MultipleFields.BANNERS, bannerImages)
                    .build();
            ENTITIES.add(entity);
        }

        return ENTITIES;
    }
}
