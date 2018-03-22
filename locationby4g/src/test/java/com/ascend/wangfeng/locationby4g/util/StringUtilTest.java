package com.ascend.wangfeng.locationby4g.util;

import org.junit.Test;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 */
public class StringUtilTest {
    @Test
    public void test(){
        String data = "0500000090000000A";
        System.out.println( StringUtil.hexString2Bytes(data));
    }
}