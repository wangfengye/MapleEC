package com.ascend.wangfeng.latte.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.ascend.wangfeng.latte.app.Latte;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fengye on 2017/8/17.
 * email 1040441325@qq.com
 */

public class FileUtil {
    //格式化模板
    private static final String TIME_FORMAT = "_yyyyMMdd_HHmmss";
    private static final String SDCARD_DIR =
            Environment.getExternalStorageDirectory().getPath();
    //默认本地上传图片目录
    public static final String UPLOAD_PHOTO_DIR =
            Environment.getExternalStorageDirectory().getPath() + "/a_upload_photos/";
    //网页缓存
    public static final String WEB_CACHE_DIR =
            Environment.getExternalStorageDirectory().getPath() + "/app_web_cache/";
    //系统相机目录
    public static final String CAMERA_PHOTO_DIR =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/Camera/";

    /**
     * @param timeFormatHeader 前缀
     * @return 添加时间
     */
    private static String getTimeFormatName(String timeFormatHeader) {
        final Date date = new Date(System.currentTimeMillis());
        final SimpleDateFormat format = new SimpleDateFormat("'" + timeFormatHeader + "'" + TIME_FORMAT,
                Locale.getDefault());
        return format.format(date);
    }

    /**
     * @param timeFormatHeader 前缀
     * @param extension        后缀
     * @return 添加时间的, 文件名
     */
    public static String getFileNameByTime(String timeFormatHeader, String extension) {
        return getTimeFormatName(timeFormatHeader) + "." + extension;
    }

    /**
     * @param sdcardDirName 文件夹名
     * @return 文件夹
     */
    private static File createDir(String sdcardDirName) {
        final String dir = SDCARD_DIR + "/" + sdcardDirName + "/";
        final File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    /**
     * @param sdcardDirName 路径
     * @param fileName      文件名
     * @return 创建的文件
     */
    public static File createFile(String sdcardDirName, String fileName) {
        return new File(createDir(sdcardDirName), fileName);
    }

    /**
     * @param sdcardDirName    路径
     * @param timeFormatHeader 文件前缀
     * @param extension        后缀
     * @return 创建文件
     */
    public static File createFileByTime(String sdcardDirName, String timeFormatHeader, String extension) {
        final String fileName = getFileNameByTime(timeFormatHeader, extension);
        return createFile(sdcardDirName, fileName);
    }

    /**
     * @param filePath 文件路径
     * @return 文件类型
     */
    public static String getMimeType(String filePath) {
        final String extension = getExtension(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    /**
     * @param path 文件路径
     * @return 文件后缀名
     */
    private static String getExtension(String path) {
        String suffix = "";
        final File file = new File(path);
        final String name = file.getName();
        final int idx = name.lastIndexOf('.');
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    /**
     * @param bitmap   图片
     * @param dir      路径
     * @param compress 压缩比 100为无损,值越小压缩率越高
     * @return
     */
    public static File saveBitmap(Bitmap bitmap, String dir, int compress) {
        final String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        File fileName = createFileByTime(dir, "DOWN_LOAD", "jpg");
        try {
            fos = new FileOutputStream(fileName);
            bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, compress, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.flush();
                if (fos != null) fos.close();
                if (bos != null) bos.flush();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        refreshDCIM();
        return fileName;
    }

    /**
     * 通知系统刷新系统相册，使照片展现出来
     */
    private static void refreshDCIM() {
        if (Build.VERSION.SDK_INT >= 19) {
            //兼容android4.4版本，只扫描存放照片的目录
            MediaScannerConnection.scanFile(Latte.getApplicationContext(),
                    new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()}
                    , null, null);
        } else {
            //扫描整个sd卡更新系统图库
            Latte.getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    /**
     * @param is
     * @param dir
     * @param name
     * @return 写入的文件
     */
    public static File writeToDisk(InputStream is, String dir, String name) {
        final File file = createFile(dir, name);
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(fos);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte data[] = new byte[1024 * 4];
            int count;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
            }
            bos.flush();
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) try {
                bos.close();
                if (fos != null) fos.close();
                if (bis != null) bis.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return file;
    }

    public static File writeToDisk(InputStream is, String dir, String prefix, String extension) {
        String name = getFileNameByTime(prefix, extension);
        return writeToDisk(is, dir, name);
    }

    /**
     * @param id raw的id
     * @return 返回 raw 的字符串
     */
    public static String getRawFile(int id) {
        final InputStream is = Latte.getApplicationContext().getResources().openRawResource(id);
        return readFrom(is);
    }

    public static String getAssetsFile(String name) {
        InputStream is = null;
        try {
            is = Latte.getApplicationContext().getAssets().open(name);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (is == null) return null;
        return readFrom(is);
    }

    private static String readFrom(InputStream is) {
        final InputStreamReader isr = new InputStreamReader(is);
        final BufferedReader br = new BufferedReader(isr);
        final StringBuilder sb = new StringBuilder();
        String str;
        try {
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return sb.toString();
    }

    public static void setIconFont(String path, TextView textView) {
        final Typeface typeface = Typeface.createFromAsset(Latte.getApplicationContext().getAssets(), path);
        textView.setTypeface(typeface);
    }

    public static String getRealFilePath(Context context, Uri uri) {
        if (uri == null) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            final Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}