package com.ascend.wangfeng.locationby4g.services.rxbus;

import com.ascend.wangfeng.locationby4g.delegates.imsi.CellMeaureAckEntry;

import java.util.ArrayList;

/**
 * Created by fengye on 2018/3/27.
 * email 1040441325@qq.com
 */

public class CellMeaureAckListEvent {
    private ArrayList<CellMeaureAckEntry> mEntries;

    public CellMeaureAckListEvent(ArrayList<CellMeaureAckEntry> entries) {
        mEntries = entries;
    }

    public ArrayList<CellMeaureAckEntry> getEntries() {
        return mEntries;
    }

    public void setEntries(ArrayList<CellMeaureAckEntry> entries) {
        mEntries = entries;
    }
}
