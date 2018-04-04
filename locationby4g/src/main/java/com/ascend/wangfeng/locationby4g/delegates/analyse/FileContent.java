package com.ascend.wangfeng.locationby4g.delegates.analyse;

import com.ascend.wangfeng.locationby4g.delegates.imsi.CellMeaureAckEntry;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by fengye on 2018/4/4.
 * email 1040441325@qq.com
 */

public class FileContent implements MultiItemEntity{
    private String mFileName;
    private List<CellMeaureAckEntry> mEntries;

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    public List<CellMeaureAckEntry> getEntries() {
        return mEntries;
    }

    public void setEntries(List<CellMeaureAckEntry> entries) {
        mEntries = entries;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
