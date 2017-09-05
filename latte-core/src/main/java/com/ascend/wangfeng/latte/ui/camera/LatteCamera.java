package com.ascend.wangfeng.latte.ui.camera;

import android.net.Uri;

import com.ascend.wangfeng.latte.delegates.PermissionCheckerDelegate;
import com.ascend.wangfeng.latte.util.FileUtil;

/**
 * Created by fengye on 2017/9/4.
 * email 1040441325@qq.com
 */

public class LatteCamera {
    public static Uri createCropFile(){
        return Uri.parse(FileUtil.createFile("Crop_image",
                FileUtil.getFileNameByTime("IMG","jpg")).getPath());
    }
    public static void start(PermissionCheckerDelegate delegate){
        new CameraHandler(delegate).beginCameraDialog();

    }
}
