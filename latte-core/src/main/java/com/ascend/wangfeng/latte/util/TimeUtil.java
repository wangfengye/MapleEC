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
    public static final String STAND_DAY = "yyyy-MM-dd ";
    public static String format(long timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }
    public static String format(long timestamp,String stand){
        SimpleDateFormat sdf = new SimpleDateFormat(stand);
        return sdf.format(new Date(timestamp));
    }

    /**
     * 格式化为 yyyy-MM 第几周
     * @param timestamp
     * @return
     */
    public static String formatWeek(long timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        String result = format(timestamp,"yyyy-MM ");
        result+= String.format("第%d周",week);
        return result;
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
