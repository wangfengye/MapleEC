package com.ascend.wangfeng.wifimanage.delegates.index.device;

import com.ascend.wangfeng.wifimanage.R;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

/**
 * Created by fengye on 2018/5/11.
 * email 1040441325@qq.com
 * 设备类型, 用于用户设置设备类型
 */

public class DeviceType implements MultiItemEntity {
    private int mId;
    private String mName;
    private int imgId;
    private boolean chose;

    public static ArrayList<DeviceType> getTypes() {
        for (DeviceType t : Build.sTypes) {
            t.setChose(false);
        }
        return Build.sTypes;
    }

    public boolean isChose() {
        return chose;
    }

    public void setChose(boolean chose) {
        this.chose = chose;
    }

    public DeviceType(int id, String name, int imgId) {
        mId = id;
        mName = name;
        this.imgId = imgId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    private static class Build {
        private static ArrayList<DeviceType> sTypes = new ArrayList<>();

        static {
            sTypes.add(new DeviceType(0, "Android", R.mipmap.ic_android));
            sTypes.add(new DeviceType(1, "iPhone", R.mipmap.ic_iphone));
            sTypes.add(new DeviceType(2, "平板", R.mipmap.ic_pad));
            sTypes.add(new DeviceType(3, "电脑", R.mipmap.ic_pc));
            sTypes.add(new DeviceType(4, "笔记本", R.mipmap.ic_pcbook));
            sTypes.add(new DeviceType(5, "电视", R.mipmap.ic_vedio));
        }
    }
}
