package com.maple.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.ascend.wangfeng.latte.util.FileUtil;
import com.yalantis.ucrop.UCrop;


public class BaseActivity extends AppCompatActivity {
    private CameraHandler mHandler;
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
         mHandler = new CameraHandler(BaseActivity.this);
        findViewById(R.id.imageView).setOnClickListener(v-> mHandler.beginCameraDialog());
    }

    /**
     * 图片处理
     * @param cropUri
     */
    private void deal(Uri cropUri) {
        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setImageURI(cropUri);
    }

    /**
     * 图片获取
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = mHandler.getPath();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .start(this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        if (pickPath == null) {
                            Log.e(TAG, "onActivityResult-PICK_PHOTO: picPath is null");
                            return;
                        }
                        //存放剪裁后图片
                        final String pickUri =  Uri.parse(FileUtil
                                .createFile("Crop_image", FileUtil.getFileNameByTime("IMG","jpg"))
                                .getPath()).getPath();
                        UCrop.of(pickPath, Uri.parse(pickUri))
                                .withMaxResultSize(400, 400)
                                .start(this);
                    }

                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    deal(cropUri);
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(this, R.string.crop_error, Toast.LENGTH_SHORT).show();
                    break;
                case RequestCodes.SCAN:
                    break;
                default:
                    break;
            }
        }
    }

}
