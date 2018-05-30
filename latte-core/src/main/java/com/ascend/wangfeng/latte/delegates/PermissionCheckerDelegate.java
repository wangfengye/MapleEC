package com.ascend.wangfeng.latte.delegates;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.ascend.wangfeng.latte.R;
import com.ascend.wangfeng.latte.ui.camera.CameraImageBean;
import com.ascend.wangfeng.latte.ui.camera.LatteCamera;
import com.ascend.wangfeng.latte.ui.camera.RequestCodes;
import com.ascend.wangfeng.latte.util.callback.CallbackManager;
import com.ascend.wangfeng.latte.util.callback.CallbackType;
import com.ascend.wangfeng.latte.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;



/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */

public abstract class PermissionCheckerDelegate extends BaseDelegate {


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .start(getContext(),this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data!=null){
                        final Uri pickPath =data.getData();
                        //存放剪裁后图片
                        final String pickUri = LatteCamera.createCropFile().getPath();
                        UCrop.of(pickPath, Uri.parse(pickUri))
                                .withMaxResultSize(400, 400)
                                .start(getContext(),this);
                    }

                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri =UCrop.getOutput(data);
                    final IGlobalCallback<Uri> callback = CallbackManager.getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback!=null){
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), R.string.crop_error, Toast.LENGTH_SHORT).show();
                    break;
                case RequestCodes.SCAN:
                    break;
                default:
                    break;
            }
        }
    }


}
