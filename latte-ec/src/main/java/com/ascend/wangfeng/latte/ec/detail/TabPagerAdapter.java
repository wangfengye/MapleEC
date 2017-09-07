package com.ascend.wangfeng.latte.ec.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/9/7.
 * email 1040441325@qq.com
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<String> mTitles;
    private final ArrayList<ArrayList<String>> mPictures;

    public TabPagerAdapter(FragmentManager fm, JSONObject data) {
        super(fm);
        mTitles = new ArrayList<>();
        mPictures = new ArrayList<>();
        final JSONArray tabs = data.getJSONArray("tabs");
        final int size = tabs.size();
        for (int i = 0; i < size; i++) {
            final JSONObject tab = tabs.getJSONObject(i);
            mTitles.add(tab.getString("name"));
            final JSONArray pictures = tab.getJSONArray("pictures");
            final ArrayList<String> picturesList = new ArrayList<>();
            for (int j = 0; j < pictures.size(); j++) {
                picturesList.add(pictures.getString(j));
            }
            mPictures.add(picturesList);
        }
    }

    @Override
    public Fragment getItem(int position) {
            return ImageDelegate.newInstance(mPictures.get(position));

    }

    @Override
    public int getCount() {
        return mTitles.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

}
