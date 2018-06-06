package com.ascend.wangfeng.latte.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 * 时间格式类
 */

public class TimeUtil {
    public static final int FIRST_DAY =Calendar.MONDAY;//设置一周的第一天是周几
    public static final String STAND_FIR = "yyyy-MM-dd HH:mm:ss";
    public static String format(long timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }
    public static String format(long timestamp,String stand){
        SimpleDateFormat sdf = new SimpleDateFormat(stand);
        return sdf.format(new Date(timestamp));
    }

    public static Long getFirstTimeOfWeek(long timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.DAY_OF_WEEK,FIRST_DAY);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }
}
