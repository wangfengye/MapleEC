package com.ascend.wangfeng.latte.util.timer;

import java.util.TimerTask;

/**
 * Created by fengye on 2017/8/21.
 * email 1040441325@qq.com
 */

public class BaseTimerTask extends TimerTask{
    private ITimerListener mListener = null;

    public BaseTimerTask(ITimerListener listener) {
        mListener = listener;
    }

    @Override
    public void run() {
        if (mListener!=null){
            mListener.onTimer();
        }
    }
}
