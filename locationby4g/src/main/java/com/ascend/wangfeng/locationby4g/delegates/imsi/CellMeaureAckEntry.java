package com.ascend.wangfeng.locationby4g.delegates.imsi;

import com.ascend.wangfeng.locationby4g.services.bean.CellMeaureAck;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 */

public class CellMeaureAckEntry extends CellMeaureAck implements MultiItemEntity{
    @Override
    public int getItemType() {
        return 0;
    }

    public static CellMeaureAckEntry copyStatic(CellMeaureAck ack){
        CellMeaureAckEntry entry = new CellMeaureAckEntry();
        entry.setImsi(ack.getImsi());
        entry.setCarrier(ack.getCarrier());
        entry.setFieldIntensity(ack.getFieldIntensity());
        entry.setTimestamp(ack.getTimestamp());
        entry.setUpFieldIntensity(ack.getUpFieldIntensity());
        return entry;
    }
    public CellMeaureAckEntry copy(CellMeaureAck ack){
        this.addFieldIntensitys(this.getFieldIntensity());
        this.addTimeStamps(this.getTimestamp());
        this.setCarrier(ack.getCarrier());
        this.setFieldIntensity(ack.getFieldIntensity());
        this.setTimestamp(ack.getTimestamp());
        this.setUpFieldIntensity(ack.getUpFieldIntensity());
        return this;
    }
}
