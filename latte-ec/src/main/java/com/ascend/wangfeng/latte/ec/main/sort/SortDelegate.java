package com.ascend.wangfeng.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.main.sort.content.ContentDelegate;
import com.ascend.wangfeng.latte.ec.main.sort.list.VerticalListDelegate;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class SortDelegate extends BottomItemDelegate {
    private final static int DEFAULT_ID =1;
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate listDelegate =new VerticalListDelegate();
        loadRootFragment(R.id.verticl_list_container,listDelegate);
        replaceLoadRootFragment(R.id.verticl_content_container, ContentDelegate.newInstance(DEFAULT_ID),false);
    }
}
