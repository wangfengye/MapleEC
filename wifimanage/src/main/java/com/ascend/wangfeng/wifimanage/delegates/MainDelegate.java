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

        items.put(new BottomBean("{fa-calendar}","时间线"),new UserDelegate());
        items.put(new BottomBean("{fa-hourglass}","计划"),new UserDelegate());
        items.put(new BottomBean("{fa-heartbeat}","主页",BottomBean.TYPE_TOP),new IndexDelegate());
        items.put(new BottomBean("{fa-star}","关注"),new UserDelegate());
        items.put(new BottomBean("{fa-user}","我的"),new UserDelegate());
        return  items;
    }

    @Override
    public int setIndexDelegate() {
        return 2;
    }

    @Override
    public int setClickedColor() {
        return getResources().getColor(R.color.colorPrimaryDark);
    }
}
