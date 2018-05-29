package com.ascend.wangfeng.wifimanage.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 * 从早上 00:00 开始计时的时间戳转换
 */

public class TimeUtil {
    public static Long getTime(int hour, int minute) {

        return Long.valueOf(hour * 60 * 60 + minute * 60);
    }

    public static String time2Str(Long timeLong) {
        int time = (int) (timeLong / 60);
        int min = time % 60;
        int hour = time / 60;
        return String.format("%02d :%02d", hour, min);
    }

    public static int getHour(Long time) {
        return (int) (time / 60 / 60);
    }

    public static int getMinute(Long time) {
        int t = (int) (time / 60);
        return t % 60;
    }
    public static long getTime(int year,int month,int day){
        String s = String.format("%04d-%02d-%02d",year,month,day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(s);
            return date.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
