package com.ascend.wangfeng.latte.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.delegates.web.WebDelegate;
import com.ascend.wangfeng.latte.delegates.web.WebDelegateImpl;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;

import butterknife.BindView;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public class DiscoverDelegate extends BottomItemDelegate {
    @BindView(R2.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R2.id.web_discovery_container)
    ContentFrameLayout mWebDiscoveryContainer;

    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initToolbar();
        final WebDelegate delegate = WebDelegateImpl.create("html/index.html");
        delegate.setTopDelegate(this.getParentDelegate());
        getDelegate().loadRootFragment(R.id.web_discovery_container,delegate);
    }

    private void initToolbar() {
        mToolbarTitle.setText("dicover");
    }

}
