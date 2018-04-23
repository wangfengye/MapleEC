package com.ascend.wangfeng.latte.delegates.web.event;

import com.orhanobut.logger.Logger;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public class UndefindEvent extends Event{
    @Override
    public String execute(String params) {
        Logger.e("UndefindEvent: 未定义此事件---"+params);
        return null;
    }
}
