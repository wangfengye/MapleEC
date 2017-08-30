package com.ascend.wangfeng.latte.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.ui.recycler.DataConverter;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/8/28.
 * email 1040441325@qq.com
 */

public class VerticalListDataonverter extends DataConverter{
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData())
                .getJSONObject("data").getJSONArray("list");
        for (int i= 0;i<dataArray.size();i++){
          final JSONObject object =dataArray.getJSONObject(i);
            final int id =object.getInteger("id");
            final String name =object.getString("name");
            final MultipleItemEntity entity = new MultipleItemEntity.Builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.TEXT,name)
                    .setField(MultipleFields.TAG,i==0?true:false)
                    .build();
               ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
