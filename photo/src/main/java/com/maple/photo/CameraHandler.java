package com.maple.photo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ascend.wangfeng.latte.util.FileUtil;
import com.blankj.utilcode.util.FileUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.List;

/**
 * @author maple on 2018/11/27 11:36.
 * @version v1.0
 * @see 1040441325@qq.com
 * desc 图片选取器Dialog
 */
public class CameraHandler implements View.OnClickListener {
    private final AlertDialog mDialog;
    private final Activity mContext;
    private Uri mPath;

    public Uri getPath() {
        return mPath;
    }

    public CameraHandler(Activity context) {
        this.mContext = context;
        this.mDialog = new AlertDialog.Builder(context).create();
    }

    /**
     * 弹出选择方式的dialog
     */
    public final void beginCameraDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_camera_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            params.dimAmount = .5f;
            window.setAttributes(params);
            window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
        }
    }

    private String getPhotoName() {
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }

    private void takePhoto() {
        final String currentPhotoName = getPhotoName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR, currentPhotoName);
        //兼容7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getPath());
            final Uri uri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            //将路径转为实际路径
            final File realFile = FileUtils.getFileByPath(FileUtil.getRealFilePath(mContext, uri));
            final Uri realUri = Uri.fromFile(realFile);
            mPath = realUri;
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            final Uri fileUri = Uri.fromFile(tempFile);
            mPath = fileUri;
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }
        mContext.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }

    private void pickPhoto() {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        mContext.startActivityForResult
                (Intent.createChooser(intent, "选择获取图片方式"), RequestCodes.PICK_PHOTO);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.photodialog_btn_cancel) {
            mDialog.cancel();
        } else if (i == R.id.photodialog_btn_take) {
            AndPermission.with(mContext)
                    .runtime()
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(per -> takePhoto())
                    .onDenied(this::showNoPermission)
                    .start();
            mDialog.cancel();
        } else if (i == R.id.photodialog_btn_native) {
            AndPermission.with(mContext)
                    .runtime()
                    .permission(Permission.Group.STORAGE)
                    .onGranted(per -> pickPhoto())
                    .onDenied(this::showNoPermission)
                    .start();
            mDialog.cancel();
        }
    }

    /**
     * 未授权的处理
     *
     * @param pers 权限列表
     */
    private void showNoPermission(List<String> pers) {
        StringBuilder builder = new StringBuilder();
        builder.append("缺少权限:\n");
        for (String s : pers) {
            builder.append(s).append('\n');
        }
        Toast.makeText(mContext, builder.toString(), Toast.LENGTH_SHORT).show();
    }
}