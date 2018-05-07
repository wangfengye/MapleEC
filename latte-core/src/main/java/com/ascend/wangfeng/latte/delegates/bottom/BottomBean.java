package com.ascend.wangfeng.latte.delegates.bottom;

/**
 * Created by fengye on 2017/8/24.
 * email 1040441325@qq.com
 */

public class BottomBean {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_TOP = 1;
    private final CharSequence ICON;
    private final CharSequence TITLE;
    private  int mType;

    public BottomBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
        mType = TYPE_NORMAL;
    }

    public BottomBean(CharSequence ICON, CharSequence TITLE, int type) {
        this.ICON = ICON;
        this.TITLE = TITLE;
        mType = type;
    }

    public CharSequence getIcon(){
        return ICON;
    }
    public CharSequence getTitle() {
        return TITLE;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}
