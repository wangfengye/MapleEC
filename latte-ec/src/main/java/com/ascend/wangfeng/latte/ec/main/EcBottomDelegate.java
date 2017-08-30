package com.ascend.wangfeng.latte.ec.main;

import android.graphics.Color;

import com.ascend.wangfeng.latte.delegates.bottom.BaseBottomDelegate;
import com.ascend.wangfeng.latte.delegates.bottom.BottomBean;
import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.delegates.bottom.ItemBuilder;
import com.ascend.wangfeng.latte.ec.main.index.IndexDelegate;
import com.ascend.wangfeng.latte.ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class EcBottomDelegate extends BaseBottomDelegate{
    @Override
    public LinkedHashMap<BottomBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomBean,BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomBean("{fa-home}","主页"),new IndexDelegate());
        items.put(new BottomBean("{fa-sort}","分类"),new SortDelegate());
        items.put(new BottomBean("{fa-compass}","发现"),new CompassDelegate());
        items.put(new BottomBean("{fa-shopping-cart}","购物车"),new ShoppingCartDelegate());
        items.put(new BottomBean("{fa-user}","我的"),new UserDelegate());
        return builder.addItem(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
