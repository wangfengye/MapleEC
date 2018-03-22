package com.ascend.wangfeng.locationby4g.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 */

public class TimeUtil {
    public static String format(long timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        return sdf.format(new Date(timestamp * 1000));
    }
}
