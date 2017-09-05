package com.ascend.wangfeng.latte.delegates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.ascend.wangfeng.latte.R;
import com.ascend.wangfeng.latte.ui.camera.CameraImageBean;
import com.ascend.wangfeng.latte.ui.camera.LatteCamera;
import com.ascend.wangfeng.latte.ui.camera.RequestCodes;
import com.ascend.wangfeng.latte.util.callback.CallbackManager;
import com.ascend.wangfeng.latte.util.callback.CallbackType;
import com.ascend.wangfeng.latte.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */
@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {
    //不是直接调用方法
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        LatteCamera.start(this);
    }

    public void startCameraWithCheck() {
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithCheck(this);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showDialog(request);
    }

    void showDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限管理")
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher
                .onRequestPermissionsResult(this, requestCode, grantResults);
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
