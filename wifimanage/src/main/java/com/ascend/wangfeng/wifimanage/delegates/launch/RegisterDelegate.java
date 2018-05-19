package com.ascend.wangfeng.wifimanage.delegates.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.R;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

import butterknife.BindView;

/**
 * Created by fengye on 2018/5/19.
 * email 1040441325@qq.com
 */

public class RegisterDelegate extends LatteDelegate {
    public static final String TAG = RegisterDelegate.class.getSimpleName();
    @BindView(R.id.map)
    MapView mMap;

    BaiduMap mBaiduMap;
    private MyLocationConfiguration.LocationMode mCurrentMode
            = MyLocationConfiguration.LocationMode.FOLLOWING;
    private LocationClient mLocationClient;
    private BitmapDescriptor marker;

    @Override
    public Object setLayout() {
        return R.layout.delegate_register;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initMap();
        initLocation();
    }

    private void initLocation() {
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        BDAbstractLocationListener listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
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
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, marker);
        mBaiduMap.setMyLocationConfiguration(config);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // mMap.onDestroy();
        mLocationClient.stop();
    }
}
