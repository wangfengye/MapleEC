package com.ascend.wangfeng.locationby4g.services.rxbus;

/**
 * Created by fengye on 2018/3/13.
 * email 1040441325@qq.com
 */

public class CMDEvent {
    public static final int SEND_DATA = 0;
    public static final int SHOW_DATA = 1;
    private int cmd;
    private String content;

    public CMDEvent(int cmd) {
        this.cmd = cmd;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
