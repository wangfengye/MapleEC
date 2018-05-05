package com.ascend.wangfeng.wifimanage.delegates;

import com.ascend.wangfeng.latte.delegates.bottom.BaseBottomDelegate;
import com.ascend.wangfeng.latte.delegates.bottom.BottomBean;
import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.delegates.bottom.ItemBuilder;
import com.ascend.wangfeng.wifimanage.*;
import com.ascend.wangfeng.wifimanage.delegates.user.UserDelegate;


import java.util.LinkedHashMap;

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class MainDelegate extends BaseBottomDelegate{

    @Override
    public LinkedHashMap<BottomBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomBean,BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomBean("{fa-home}","主页"),new IndexDelegate());
        items.put(new BottomBean("{fa-list-ul}","历史"),new UserDelegate());
        items.put(new BottomBean("{fa-cogs}","管理"),new UserDelegate());
        items.put(new BottomBean("{fa-user}","我的"),new UserDelegate());
        return  items;
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return getResources().getColor(R.color.colorPrimaryDark);
    }
}
