package com.ascend.wangfeng.latte.ui.recycler;


import com.google.auto.value.AutoValue;

/**
 * Created by fengye on 2017/8/28.
 * email 1040441325@qq.com
 */
@AutoValue
public abstract class RgbValue {
    public abstract int red();
    public abstract int green();
    public abstract int blue();
    public static RgbValue create(int red,int green,int blue){
        return new AutoValue_RgbValue(red,green,blue);
    }
}
