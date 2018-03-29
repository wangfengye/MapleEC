package com.ascend.wangfeng.locationby4g.util;

/**
 * Created by fengye on 2018/3/29.
 * email 1040441325@qq.com
 * Imsi信息分析
 */

public class ImsiUtil {
    /**
     *
     * @param imsi
     * @return 运营商名称
     */
    public static String getImsiOperator(String imsi) {
        String mnc = imsi.substring(3, 5);
        String operator = null;
        if (equals(mnc, new String[]{"00", "02", "04", "07"})) {
            operator = "中国移动";
        } else if (equals(mnc, new String[]{"01", "06", "09"})) {
            operator = "中国联通";
        } else if (equals(mnc, new String[]{"03", "05","11"})) {
            operator = "中国电信";
        } else if (equals(mnc, new String[]{"20"})) {
            operator = "中国铁通";
        } else {
            operator = "未知";
        }
        return operator;
    }
    private static boolean equals(String target, String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            if (target.equals(strings[i])) return true;
        }
        return false;
    }
}
