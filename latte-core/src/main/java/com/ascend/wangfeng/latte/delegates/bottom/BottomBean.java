package com.ascend.wangfeng.latte.delegates.bottom;

/**
 * Created by fengye on 2017/8/24.
 * email 1040441325@qq.com
 */

public class BottomBean
{
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }
    public CharSequence getIcon(){
        return ICON;
    }
    public CharSequence getTitle() {
        return TITLE;
    }
}
