package com.ascend.wangfeng.latte.ui.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.util.callback.CallbackManager;
import com.ascend.wangfeng.latte.util.callback.CallbackType;
import com.ascend.wangfeng.latte.util.callback.IGlobalCallback;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class ScannerDelegate extends LatteDelegate implements ZBarScannerView.ResultHandler{
    public static final String SCAN_RESULT = "SCAN_RESULT";
    private ScanView mScanView =null;
    @Override
    public Object setLayout() {
        return mScanView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScanView==null){
            mScanView=new ScanView(getContext());
        }
        mScanView.setAutoFocus(true);
        mScanView.setResultHandler(this);
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScanView!=null){
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScanView!=null){
            mScanView.stopCameraPreview();
            mScanView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {

        final String qrCode = result.getContents();
        final IGlobalCallback<String> callback = CallbackManager.getInstance()
                .getCallback(CallbackType.ON_SCAN);
        if (callback!=null){
            callback.executeCallback(qrCode);
        getSupportDelegate().pop();
    }}
}
