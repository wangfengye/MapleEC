package com.ascend.wangfeng.locationby4g.services.rxbus;


import com.ascend.wangfeng.locationby4g.services.bean.CellMeaureAck;

/**
 * Created by fengye on 2018/3/19.
 * email 1040441325@qq.com
 */

public class CellMeaureAckEvent {
    private CellMeaureAck mAck;

    public CellMeaureAckEvent(CellMeaureAck ack) {
        mAck = ack;
    }

    public CellMeaureAck getAck() {
        return mAck;
    }

    public void setAck(CellMeaureAck ack) {
        mAck = ack;
    }
}
