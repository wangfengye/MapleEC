package com.ascend.wangfeng.latte.delegates;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */

public abstract class LatteDelegate extends PermissionCheckerDelegate{
    public <T extends LatteDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
