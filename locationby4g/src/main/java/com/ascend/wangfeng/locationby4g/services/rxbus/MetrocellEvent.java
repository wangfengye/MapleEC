package com.ascend.wangfeng.locationby4g.services.rxbus;


import com.ascend.wangfeng.locationby4g.services.bean.Metrocell;

import java.util.List;

/**
 * Created by fengye on 2018/3/13.
 * email 1040441325@qq.com
 */

public class MetrocellEvent {
    private List<Metrocell> mMetrocells;

    public MetrocellEvent(List<Metrocell> metrocells) {
        mMetrocells = metrocells;
    }

    public List<Metrocell> getMetrocells() {
        return mMetrocells;
    }

    public void setMetrocells(List<Metrocell> metrocells) {
        mMetrocells = metrocells;
    }
}
