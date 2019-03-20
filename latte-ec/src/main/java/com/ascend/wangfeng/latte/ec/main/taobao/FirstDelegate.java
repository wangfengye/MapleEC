package com.ascend.wangfeng.latte.ec.main.taobao;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.util.DimenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maple on 2019/2/20 16:37.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class FirstDelegate extends LatteDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_first;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        TabLayout tab = rootView.findViewById(R.id.tab);
        CustomNestedScrollView  sv = rootView.findViewById(R.id.sv);
        sv.setMyScrollHeight(DimenUtil.dp2px(240));
        FullHeightViewpager viewPager = rootView.findViewById(R.id.vp);
        viewPager.setAdapter(new PageAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(viewPager);
    }

    class  PageAdapter extends FragmentPagerAdapter {
        List<Fragment> mList;
        List<String> mTitles;
        public PageAdapter(FragmentManager fm) {
            super(fm);
            mList = new ArrayList<>();
            mList.add(new ChildDelegate());
            mList.add(new ChildDelegate());
            mList.add(new ChildDelegate());
            mTitles=new ArrayList<>();
            mTitles.add("a");
            mTitles.add("b");
            mTitles.add("c");
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
