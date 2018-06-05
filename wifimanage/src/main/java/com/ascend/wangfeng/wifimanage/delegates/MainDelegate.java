package com.ascend.wangfeng.wifimanage.delegates;

import com.ascend.wangfeng.latte.delegates.bottom.BaseBottomDelegate;
import com.ascend.wangfeng.latte.delegates.bottom.BottomBean;
import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.delegates.bottom.ItemBuilder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.delegates.history.HistoryDelegate;
import com.ascend.wangfeng.wifimanage.delegates.me.SetDelegate;
import com.ascend.wangfeng.wifimanage.delegates.plan.PlanDelegate;
import com.ascend.wangfeng.wifimanage.delegates.user.UserDelegate;

import java.util.LinkedHashMap;

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class MainDelegate extends BaseBottomDelegate{
    public static MainDelegate newInstance(){
        return new MainDelegate();
    }
    @Override
    public LinkedHashMap<BottomBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomBean,BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomBean("{fa-calendar}","动态"),new HistoryDelegate());
        items.put(new BottomBean("{fa-hourglass}","监管"),new PlanDelegate());
        items.put(new BottomBean("{fa-heartbeat}","主页",BottomBean.TYPE_TOP),new IndexDelegate());
        items.put(new BottomBean("{fa-star}","关注"), UserDelegate.newInstance(null));
        items.put(new BottomBean("{fa-user}","我的"),new SetDelegate());
        return  builder.addItem(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 2;
    }

    @Override
    public int setClickedColor() {
        return getResources().getColor(R.color.colorPrimaryDark,getActivity().getTheme());
    }
}
