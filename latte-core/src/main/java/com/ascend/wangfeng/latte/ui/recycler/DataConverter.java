package com.ascend.wangfeng.latte.ui.recycler;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 * 数据转换约束
 */

public abstract class DataConverter {
    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJson = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJson = json;
        return this;
    }

    protected String getJsonData() {
        if (mJson == null || mJson.isEmpty()) {
            throw new NullPointerException("DATA IS NULL");
        }
        return mJson;
    }
}
