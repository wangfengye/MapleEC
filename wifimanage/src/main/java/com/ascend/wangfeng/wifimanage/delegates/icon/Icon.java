package com.ascend.wangfeng.wifimanage.delegates.icon;

import android.support.annotation.DrawableRes;

import com.ascend.wangfeng.wifimanage.R;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

/**
 * Created by fengye on 2018/5/14.
 * email 1040441325@qq.com
 */

public class Icon implements MultiItemEntity{

    private int mIcon;
    private boolean mChose;


    public static ArrayList<Icon> getList(){

        return Build.sIcons;
    }

    public Icon(@DrawableRes int icon) {
        this.mIcon = icon;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public boolean isChose() {
        return mChose;
    }

    public void setChose(boolean chose) {
        mChose = chose;
    }

    @Override
    public int getItemType() {
        return 0;
    }
    private static class Build{
        public static ArrayList<Icon> sIcons = new ArrayList<>();
        static {
            sIcons.add(new Icon(R.mipmap.p_2));
            sIcons.add(new Icon(R.mipmap.p_3));
            sIcons.add(new Icon(R.mipmap.p_2));
            sIcons.add(new Icon(R.mipmap.p_2));
            sIcons.add(new Icon(R.mipmap.p_2));
            sIcons.add(new Icon(R.mipmap.p_2));
        }
    }
}
