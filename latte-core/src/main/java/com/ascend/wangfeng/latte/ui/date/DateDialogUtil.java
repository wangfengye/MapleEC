package com.ascend.wangfeng.latte.ui.date;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by fengye on 2017/9/4.
 * email 1040441325@qq.com
 */

public class DateDialogUtil {
    public interface IDateListener{
        void  onDateChange(String date);
    }
    private IDateListener mListener =null;
    public void setDateListener(IDateListener listener){
        this.mListener =listener;
    }
    public void showDialog(Context context){
        final LinearLayout ll = new LinearLayout(context);
        final DatePicker picker =new DatePicker(context);
        final LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        picker.setLayoutParams(lp);
        picker.init(1990,1,1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker picker, int i, int i1, int i2) {
                final Calendar calendar = Calendar.getInstance();
                calendar.set(i,i1,i2);
                SimpleDateFormat format =new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
                final String data = format.format(calendar.getTime());
                if (mListener!=null){
                    mListener.onDateChange(data);
                }
            }
        });
        ll.addView(picker);
        new AlertDialog.Builder(context)
                .setTitle("choose date")
                .setView(ll)
                .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface anInterface, int i) {

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface anInterface, int i) {

                    }
                })
                .show();

    }
}
