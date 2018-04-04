package com.ascend.wangfeng.locationby4g.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 * 时间戳为秒级的
 */

public class TimeUtil {
    public static final String STAND_FIR = "MM-dd HH:mm:ss";
    public static String format(long timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp * 1000));
    }
    public static String format(long timestamp,String stand){
        SimpleDateFormat sdf = new SimpleDateFormat(stand);
        return sdf.format(new Date(timestamp * 1000));
    }
    public static float getTodayBegin(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis() / 1000;
    }

    public static long getTimeStamp(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timestamp = 0;
        Date date;
        try {
            date = sdf.parse(dateStr);
            timestamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp / 1000;
    }
}
