package com.ascend.wangfeng.latte.ui.camera;

import android.net.Uri;

/**
 * Created by fengye on 2017/9/4.
 * email 1040441325@qq.com
 */

public class CameraImageBean {
    private Uri mPath =null;
    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri path) {
        mPath = path;
    }
}
