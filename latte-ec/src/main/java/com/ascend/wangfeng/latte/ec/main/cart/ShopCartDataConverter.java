package com.ascend.wangfeng.latte.ec.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.ui.recycler.DataConverter;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/8/31.
 * email 1040441325@qq.com
 */

public class ShopCartDataConverter extends DataConverter{
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray jsonArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final  int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject data = jsonArray.getJSONObject(i);
            final String thumb =data.getString("thumb");
            final String desc =data.getString("desc");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final int count = data.getInteger("count");
            final double price = data.getDouble("price");
            final MultipleItemEntity entity = new MultipleItemEntity.Builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.SHOP_CART_ITEM)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(MultipleFields.TITLE, title)
                    .setField(MultipleFields.DESC, desc)
                    .setField(MultipleFields.COUNT,count)
                    .setField(MultipleFields.PRICE,price)
                    .setField(MultipleFields.TAG,false)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
