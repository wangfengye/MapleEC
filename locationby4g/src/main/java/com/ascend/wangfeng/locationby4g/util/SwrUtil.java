package com.ascend.wangfeng.locationby4g.util;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fengye on 2018/3/9.
 * email 1040441325@qq.com
 * 设备数据解析工具
 */

public class SwrUtil {
    public static int hex2Int(List<String> data){
        return hex2Int(data,1);
    }
    /**
     * 从16进制中取前length位转为数值
     *
     * @param data 16进制集合
     * @param length 读取长度
     * @return 数值
     */
    public static synchronized int hex2Int (List<String> data,int length){
        StringBuilder hexStr = new StringBuilder();
        // 小端数据,倒序读取
        for (int i = length - 1; i >= 0; i--) {
            hexStr.append(data.get(i));
        }
        remove(data,length);
        return Integer.valueOf(hexStr.toString(),16);
    }

    /**
     * 从16进制中取前length位转为字符串
     * @param data
     * @param length
     * @return
     */
    public static synchronized String hex2String(List<String> data, int length){
        String result = null;
        byte[] sbyte = new byte[length];
        for (int i = 0; i < length; i++) {
            sbyte[i] = (byte) (0xff & Integer.parseInt(data.get(i), 16));
        }
        remove(data, length);
        try {
            result = new String(sbyte,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            return  result;
        }
    }
    
    public static synchronized String hex2Imsi(List<String> data){

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            builder.append(data.get(i).substring(0,1));
        }
        remove(data,15);
        return builder.toString();
    }

    public static synchronized int hex2Intensity(List<String> data){
        return -140 + hex2Int(data,2);
    }
    private static void remove(List<String> data,int length){
        Iterator<String> iter = data.iterator();
        for (int i = length - 1; i >= 0; i--) {
            if (iter.hasNext()){
                iter.next();
                iter.remove();
            }
        }
    }


}
