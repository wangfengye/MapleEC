package com.ascend.wangfeng.latte.ec.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public class SectionBean extends SectionEntity<SectionContentItemEntity>{
    private boolean mIsMore =false;
    private int mId = -1;
    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionBean(SectionContentItemEntity entity) {
        super(entity);
    }

    public boolean isMore() {
        return mIsMore;
    }

    public void setMore(boolean more) {
        mIsMore = more;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
