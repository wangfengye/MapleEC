package com.ascend.wangfeng.locationby4g.services.rxbus;

/**
 * Created by fengye on 2018/3/28.
 * email 1040441325@qq.com
 */

public class ErrorEvent {
    private String message;

    public ErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
