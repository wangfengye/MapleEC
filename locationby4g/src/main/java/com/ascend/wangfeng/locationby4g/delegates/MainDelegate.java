package com.ascend.wangfeng.locationby4g.delegates;

import com.ascend.wangfeng.latte.delegates.bottom.BaseBottomDelegate;
import com.ascend.wangfeng.latte.delegates.bottom.BottomBean;
import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.delegates.bottom.ItemBuilder;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.delegates.equipment.EquipmentDelegate;
import com.ascend.wangfeng.locationby4g.delegates.imsi.ImsiDelegate;

import java.util.LinkedHashMap;

/**
 * Created by fengye on 2018/3/20.
 * email 1040441325@qq.com
 */

public class MainDelegate extends BaseBottomDelegate{

    @Override
    public LinkedHashMap<BottomBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomBean,BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomBean("{fa-th-large}",getString(R.string.equiment_manage)),new EquipmentDelegate());
        items.put(new BottomBean("{fa-stack-overflow}",getString(R.string.scan_info)),new ImsiDelegate());
       // items.put(new BottomBean("{fa-wifi}",getString(R.string.wifi_info)),new EquipmentDelegate());

        return builder.addItem(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return getResources().getColor(R.color.colorAccent);
    }
}
