package com.ascend.wangfeng.latte.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by fengye on 2017/9/1.
 * email 1040441325@qq.com
 */

public class DataFormat {
    public static String formatMoney(double money){
        NumberFormat format =NumberFormat.getCurrencyInstance(Locale.CHINA);
        String moneyString = format.format(money);
        return moneyString;
    }
}
