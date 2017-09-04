package com.ascend.wangfeng.latte.delegates.bottom;

import android.view.View;
import android.widget.Toast;

import com.ascend.wangfeng.latte.R;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;

/**
 * Created by fengye on 2017/8/24.
 * email 1040441325@qq.com
 */

public abstract class BottomItemDelegate extends LatteDelegate{
    private long mTouchTime = 0;
    private static final long WAIT_TIME= 2000L;

    @Override
    public void onResume() {
        super.onResume();
        View rootView =getView();
        if (rootView!=null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis()-mTouchTime<WAIT_TIME){
            _mActivity.finish();
        }else {
            mTouchTime=System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出 "+ R.string.app_name, Toast.LENGTH_SHORT).show();
        }
        return true;

    }

    public void goContainer(int i){
        BaseBottomDelegate parent = (BaseBottomDelegate) getParentDelegate();
        if (parent instanceof BaseBottomDelegate){
            parent.goContainer(i);
        }
    }
}
