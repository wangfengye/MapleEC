package com.ascend.wangfeng.wifimanage.delegates.icon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.R;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;

/**
 * Created by fengye on 2018/5/14.
 * email 1040441325@qq.com
 * 头像选择界面
 */

public class IconChooseDelegate extends LatteDelegate {
    public static final int DELEGAE_CODE = 1;
    public static final String ICON = "icon";
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.ic_edit)
    IconTextView mIcEdit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_icons)
    RecyclerView mRvIcons;

    public static IconChooseDelegate newInstance(){
        return new IconChooseDelegate();
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_icon_choose;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mIcBack.setVisibility(View.VISIBLE);
        mToolbarTitle.setText("头像选择");
        mIcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
        initRv();
    }

    private void initRv() {
        GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        IconAdapter adapter = new IconAdapter(Icon.getList());
        adapter.setListener(new IconAdapter.OnClicekListener() {
            @Override
            public void click(Icon icon) {
                // 设置返回数据
                Bundle bundle = new Bundle();
                bundle.putInt(ICON,icon.getIcon());
                setFragmentResult(DELEGAE_CODE, bundle);
            }
        });
        mRvIcons.setLayoutManager(manager);
        mRvIcons.setAdapter(adapter);
        mRvIcons.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(R.color.colorBg), 3));

    }

    @Override
    public boolean onBackPressedSupport() {

        pop();
        return true;
    }
}
