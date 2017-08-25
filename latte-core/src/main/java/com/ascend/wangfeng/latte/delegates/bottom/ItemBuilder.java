package com.ascend.wangfeng.latte.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * Created by fengye on 2017/8/24.
 * email 1040441325@qq.com
 */

public final class ItemBuilder {
    private final LinkedHashMap<BottomBean,BottomItemDelegate> ITEMS =new LinkedHashMap<>();
    static ItemBuilder builder(){
        return new ItemBuilder();
    }
    public final ItemBuilder addItem(BottomBean bean,BottomItemDelegate delegate){
        ITEMS.put(bean,delegate);
        return this;
    }
    public final ItemBuilder addItem(LinkedHashMap<BottomBean,BottomItemDelegate> items){
        ITEMS.putAll(items);
        return this;
    }
    public final LinkedHashMap<BottomBean,BottomItemDelegate> build(){
        return ITEMS;
    }
}
