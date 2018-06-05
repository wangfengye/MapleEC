package com.ascend.wangfeng.wifimanage.delegates.icon;

import android.support.annotation.DrawableRes;

import com.ascend.wangfeng.wifimanage.R;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

/**
 * Created by fengye on 2018/5/14.
 * email 1040441325@qq.com
 */

public class Icon implements MultiItemEntity {
    private int mIcon;
    private int mIconUrl;
    private boolean mChose;


    public static ArrayList<Icon> getList() {
        return Build.sIcons;
    }

    public static void addIcon(@DrawableRes int res) {
        Build.sIcons.add(new Icon(Build.sIcons.size(), res));
    }

    public static int getImgUrl(int i) {
        return Build.sIcons.get(i).mIconUrl;
    }

    public Icon(int icon, @DrawableRes int iconUrl) {
        this.mIcon = icon;
        this.mIconUrl = iconUrl;
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

    public int getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(int iconUrl) {
        mIconUrl = iconUrl;
    }

    public void setChose(boolean chose) {
        mChose = chose;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    private static class Build {
        public static ArrayList<Icon> sIcons = new ArrayList<>();
        static {
            Icon.addIcon(R.mipmap.p_boy_01);
            Icon.addIcon(R.mipmap.p_boy_02);
            Icon.addIcon(R.mipmap.p_girl);
            Icon.addIcon(R.mipmap.p_dad_1);
            Icon.addIcon(R.mipmap.p_dad_02);
            Icon.addIcon(R.mipmap.p_dad_03);
            Icon.addIcon(R.mipmap.p_mom_01);
            Icon.addIcon(R.mipmap.p_mom_02);
            Icon.addIcon(R.mipmap.p_mom_03);
            Icon.addIcon(R.mipmap.p_mom_04);
            Icon.addIcon(R.mipmap.p_mom_05);
            Icon.addIcon(R.mipmap.p_grandpa_01);
            Icon.addIcon(R.mipmap.p_grandpa_02);
            Icon.addIcon(R.mipmap.p_grandpa_03);
            Icon.addIcon(R.mipmap.p_grandpa_04);
            Icon.addIcon(R.mipmap.p_grandmom_01);
            Icon.addIcon(R.mipmap.p_grandmom_02);
            Icon.addIcon(R.mipmap.p_grandmom_03);
        }
    }
}
