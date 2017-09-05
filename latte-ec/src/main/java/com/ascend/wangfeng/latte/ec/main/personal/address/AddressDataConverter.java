package com.ascend.wangfeng.latte.ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.ui.recycler.DataConverter;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/9/5.
 * email 1040441325@qq.com
 */

public class AddressDataConverter extends DataConverter{
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        int size  =array.size();
        for (int i = 0; i < size; i++) {
            JSONObject data = array.getJSONObject(i);
            final int id =data.getInteger("id");
            final boolean isDefault = data.getBoolean("default");
            final String name =data.getString("name");
            final String phone =data.getString("phone");
            final String address =data.getString("address");
            MultipleItemEntity entity =new MultipleItemEntity.Builder()
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.TAG,isDefault)
                    .setField(MultipleFields.NAME,name)
                    .setField(MultipleFields.PHONE,phone)
                    .setField(MultipleFields.ADDRESS,address)
                    .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_ADDRESS)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
