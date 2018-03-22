package com.ascend.wangfeng.locationby4g.services.rxbus;


import com.ascend.wangfeng.locationby4g.services.bean.CellSysAck;

/**
 * Created by fengye on 2018/3/19.
 * email 1040441325@qq.com
 */

public class CellSysAckEvent {
    private CellSysAck mAck;

    public CellSysAckEvent(CellSysAck ack) {
        mAck = ack;
    }
}
