package com.ascend.wangfeng.latte.ec.main.personal.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.ui.recycler.DataConverter;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/9/4.
 * email 1040441325@qq.com
 */

public class OrderDataConverter extends DataConverter{
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            JSONObject bean = array.getJSONObject(i);
            final String thumb =bean.getString("thumb");
            final String title =bean.getString("title");
            final int id = bean.getInteger("id");
            final double price = bean.getDouble("price");
            final String time = bean.getString("time");
            MultipleItemEntity entity =new MultipleItemEntity.Builder()
                    .setItemType(ItemType.ITEM_ORDER_LIST)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.IMAGE_URL,thumb)
                    .setField(MultipleFields.TITLE,title)
                    .setField(MultipleFields.PRICE,price)
                    .setField(MultipleFields.TIME,time)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
