package com.ascend.wangfeng.latte.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public class SectionDataConverter {
    final List<SectionBean> convert(String json){
        final List<SectionBean> dataList = new ArrayList<>();
        final JSONArray jsonArray = JSON.parseObject(json).getJSONArray("data");
        final int size =jsonArray.size();
        for (int i =0 ;i<size;i++) {
            final JSONObject data = jsonArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String title = data.getString("section");
            //添加title
            final SectionBean sectionTitleBean = new SectionBean(true, title);
            sectionTitleBean.setId(id);
            sectionTitleBean.setMore(true);
            dataList.add(sectionTitleBean);
            final JSONArray goods = data.getJSONArray("goods");
            final int goodsSize = goods.size();
            for (int j = 0; j < goodsSize; j++) {
                final String good = goods.getString(j);
                SectionContentItemEntity entity = JSONObject.parseObject(good, SectionContentItemEntity.class);
                dataList.add(new SectionBean(entity));
            }
        }
        Logger.i("ssss"+dataList.toString());
        return dataList;
    }
}
