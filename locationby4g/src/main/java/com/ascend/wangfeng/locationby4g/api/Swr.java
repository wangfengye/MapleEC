package com.ascend.wangfeng.locationby4g.api;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 */

public class Swr implements ISwr{
    private Context mContext;
    public Swr(Context context) {
     mContext = context;
    }
    private Context getActivity(){return mContext;}


    @Override
    public void getEquimentState() {
        Toast.makeText(getActivity(), "state", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void restart() {
        Toast.makeText(getActivity(), "restart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void scan() {
        Toast.makeText(getActivity(), "scan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPower(int type) {
        Toast.makeText(getActivity(), "power:" + type, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setMode(int type) {

    }

    @Override
    public void start() {
        Toast.makeText(getActivity(), "start:", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stop() {
        Toast.makeText(getActivity(), "stop:", Toast.LENGTH_SHORT).show();
    }

}
