package com.ascend.wangfeng.wifimanage.utils;

/**
 * Created by fengye on 2018/6/5.
 * email 1040441325@qq.com
 */

public abstract class MacUtil {
    public static final String MAC_SUFFIX = "-";

    public static String formartOui(String mac) {
        if (mac != null && mac != "") {
            return null;
        }
        mac = mac.trim();
        try {
            if (mac.contains("-")) {
                return mac.replace("-", "-").substring(0, 8).toUpperCase();
            }

            if (mac.length() <= 6) {
                return mac;
            }

            StringBuffer rtn = new StringBuffer();
            rtn.append(mac.substring(0, 2)).append("-");
            rtn.append(mac.substring(2, 4)).append("-");
            rtn.append(mac.substring(4, 6));
            return rtn.toString().toUpperCase();
        } catch (Exception ex) {
            return mac;
        }
    }

    public static String longToString(Long sl) {

        if (sl == null) {
            return "-";
        }
        try {
            String rtn = Long.toHexString(sl);
            if (rtn != null && rtn.length() != 12) {
                rtn = padStart(rtn, 12, '0');
            }
            StringBuffer buffer = new StringBuffer();
            buffer.append(rtn.substring(0, 2)).append("-");
            buffer.append(rtn.substring(2, 4)).append("-");
            buffer.append(rtn.substring(4, 6)).append("-");
            buffer.append(rtn.substring(6, 8)).append("-");
            buffer.append(rtn.substring(8, 10)).append("-");
            buffer.append(rtn.substring(10, 12));
            return buffer.toString();
        } catch (Exception ex) {
            return "-";
        }
    }

    public static Long stringToLong(String strMac) {
        if (strMac == null || strMac.trim().isEmpty()) {
            return null;
        }

        if (strMac.contains(MAC_SUFFIX)) {
            strMac = strMac.replaceAll(MAC_SUFFIX, "").trim(); // dash
        }
        if (strMac.contains(":")) {
            strMac = strMac.replaceAll(":", "").trim(); // colon
        }

        try {
            return Long.valueOf(strMac, 16);
        } catch (Exception ex) {
            return -1L;
        }
    }
    public static String padStart(String string, int minLength, char padChar) {

        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = string.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        sb.append(string);
        return sb.toString();
    }
}