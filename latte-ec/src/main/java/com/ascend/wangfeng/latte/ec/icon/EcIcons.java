package com.ascend.wangfeng.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */

public enum EcIcons implements Icon{
    icon_test('\ue6b4');
    private char character;
    EcIcons(char character){
        this.character= character;
    }
    @Override
    public String key() {
        return name().replace('_','-');
    }

    @Override
    public char character() {
        return character;
    }
}
