package com.ascend.wangfeng.locationby4g.delegates.equipment;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.ascend.wangfeng.locationby4g.R;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by fengye on 2018/3/20.
 * email 1040441325@qq.com
 */

public class  EquipmentEntry implements MultiItemEntity{
    public static final int DIALOG = 0;
    public static final int MULTIPLE_CHOICE = 1;
    public static final int LIST =2;
    private int mType;
    private String mTitle;
    private List<String> mChoices;
    private LinkedHashMap<String,Boolean> mListMap;
    private android.support.v7.app.AlertDialog mDialog;
    private Callback mCallback;

    /**
     * 创建有dialog的 设置项
     * @param title 标题
     * @param content 弹框内容
     * @param context 上下文
     * @param callback 弹框执行方法
     * @return
     */
    public static EquipmentEntry createDialog(String title, String content, Context context, final Callback callback){
        EquipmentEntry entry = new EquipmentEntry();
        entry.mType = DIALOG;
        entry.mDialog = build(title,content,callback,context);
        entry.mTitle = title;
        return entry;
    }
    private static AlertDialog build(String title,String content, final Callback callback, Context context){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(context.getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 侦码重启
                        callback.onClickListener(0);
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        return dialog;
    }

    public static EquipmentEntry createMultipleChoice(String title,List<String> choices,Callback callback){
        EquipmentEntry entry = new EquipmentEntry();
        entry.mType = MULTIPLE_CHOICE;
        entry.mTitle = title;
        entry.mCallback = callback;
        entry.mChoices = choices;
        return entry;
    }
    public static EquipmentEntry createList(String title,LinkedHashMap<String,Boolean> map,Callback callback){
        EquipmentEntry entry = new EquipmentEntry();
        entry.mType = LIST;
        entry.mCallback = callback;
        entry.mTitle = title;
        entry.mListMap = map;
        return entry;
    }

    @Override
    public int getItemType() {
        return mType;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<String> getChoices() {
        return mChoices;
    }

    public void setChoices(List<String> choices) {
        mChoices = choices;
    }

    public LinkedHashMap<String, Boolean> getListMap() {
        return mListMap;
    }

    public void setListMap(LinkedHashMap<String, Boolean> listMap) {
        mListMap = listMap;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public void setDialog(AlertDialog dialog) {
        mDialog = dialog;
    }

    public Callback getCallback() {
        return mCallback;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback{
        void onClickListener(int i);
    }
}
