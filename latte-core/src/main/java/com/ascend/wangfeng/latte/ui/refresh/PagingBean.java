package com.ascend.wangfeng.latte.ui.refresh;

/**
 * Created by fengye on 2017/8/28.
 * email 1040441325@qq.com
 */

public class PagingBean {
    private int mPageIndex =0;
    private int mTotal = 0;
    private int mPageSize =0;
    //showde count
    private int mCurrentCount = 0;
    //load delay
    private int mDelayed =0;

    public int getPageIndex() {
        return mPageIndex;
    }

    public void setPageIndex(int pageIndex) {
        mPageIndex = pageIndex;
    }

    public int getTotal() {
        return mTotal;
    }

    public PagingBean setTotal(int total) {
        mTotal = total;
        return this;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public PagingBean setPageSize(int pageSize) {
        mPageSize = pageSize;
        return this;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public PagingBean setCurrentCount(int currentCount) {
        mCurrentCount = currentCount;
        return this;
    }

    public int getDelayed() {
        return mDelayed;
    }

    public PagingBean setDelayed(int delayed) {
        mDelayed = delayed;
        return this;
    }
    PagingBean addIndex(){
        mPageIndex++;
        return this;
    }
}
