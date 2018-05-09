package com.ascend.wangfeng.latte.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 * 时间格式类
 */

public class TimeUtil {
    public static final String STAND_FIR = "yyyy-MM-dd HH:mm:ss";
    public static String format(long timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }
    public static String format(long timestamp,String stand){
        SimpleDateFormat sdf = new SimpleDateFormat(stand);
        return sdf.format(new Date(timestamp));
    }
}
