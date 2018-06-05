package com.ascend.wangfeng.wifimanage.delegates.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Box;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.bean.User;
import com.ascend.wangfeng.wifimanage.delegates.MainDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.net.SchedulerProvider;
import com.ascend.wangfeng.wifimanage.utils.MacUtil;
import com.ascend.wangfeng.wifimanage.utils.SpKey;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;

/**
 * Created by fengye on 2018/5/19.
 * email 1040441325@qq.com
 */

public class RegisterDelegate extends LatteDelegate {
    public static final String TAG = RegisterDelegate.class.getSimpleName();
    @BindView(R.id.map)
    TextureMapView mMap;
    @BindView(R.id.et_no)
    EditText mEtNo;

    @BindView(R.id.et_password)
    EditText mEtPassword;

    BaiduMap mBaiduMap;
    private MyLocationConfiguration.LocationMode mCurrentMode
            = MyLocationConfiguration.LocationMode.FOLLOWING;
    private LocationClient mLocationClient;
    private BitmapDescriptor marker;
    private BDLocation mLocation;

    @OnClick(R.id.btn_login)
    void clickBtnLogin() {
        Long mac = MacUtil.stringToLong(mEtNo.getText().toString().trim());
        String password = mEtPassword.getText().toString().trim();
        Observable.concat(Client.getInstance().createUser(mac, password, mLocation.getLongitude(), mLocation.getLatitude()),
                Client.getInstance().login(mac, password))
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<User>>() {
                    @Override
                    public void onSuccess(Response<User> response) {
                        User user = new User();
                        user.setBmac(mac);
                        user.setUpasswd(password);
                        LattePreference.setJson(SpKey.USER,response.getData());
                        startWithPop(MainDelegate.newInstance());
                    }
                });


    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_register;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        AndPermission.with(getContext())
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_PHONE_STATE, Permission.ACCESS_COARSE_LOCATION)
                .onGranted(data -> {
                    initMap();
                    initLocation();
                })
                .onDenied(data -> Toast.makeText(getActivity(), "无权限", Toast.LENGTH_SHORT).show())
                .start();
        // 获取设备编号
        Client.getLocalApi().getBoxInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Response<Box>>() {
                    @Override
                    public void onSuccess(Response<Box> response) {
                        mEtNo.setText(MacUtil.longToString(response.getData().getBmac()));
                    }
                });
    }

    private void initLocation() {
        mLocationClient = new LocationClient(getActivity());
        BDAbstractLocationListener listener = new BDAbstractLocationListener() {
            @Override
            public void onLocDiagnosticMessage(int i, int i1, String s) {
                super.onLocDiagnosticMessage(i, i1, s);
                Log.i(TAG, "onLocDiagnosticMessage: " + s);
            }

            @Override
            public void onReceiveLocation(BDLocation location) {
                mLocation = location;
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);

            }
        };
        mLocationClient.registerLocationListener(listener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void initMap() {
        mBaiduMap = mMap.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
        marker = BitmapDescriptorFactory.fromResource(R.drawable.map_point);
        int accuracyCircleFillColor = 0xAAFFFF88;
        int accuracyCircleStrokeColor = 0xAA00FF00;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode, true
                , marker, accuracyCircleFillColor, accuracyCircleStrokeColor));
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setCompassEnable(false);
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, marker);
        mBaiduMap.setMyLocationConfiguration(config);
    }

    @Override
    public void onDestroyView() {
        if (mLocationClient != null) mLocationClient.stop();
        super.onDestroyView();

    }
}
