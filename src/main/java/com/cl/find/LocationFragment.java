package com.cl.find;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;

import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyInfo;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.baidu.mapapi.radar.RadarUploadInfo;
import com.baidu.mapapi.radar.RadarUploadInfoCallback;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



/**
 * Created by Administrator on 2017/3/12 0012.
 */

public class LocationFragment extends BaseFragment implements RadarUploadInfoCallback {

    private View mLayout;
    private MapView mapView;
    private BaiduMap BDmap;
    private LocationClient locationClient;
    private boolean isFirst=true;
    private SharedPreferences sharedprefrence;
    private double latitude;
    private double longitude;
    private float direction;
    private float speed;
    private float accuracy;
    private int satellitesNum;
    private MyLocationData lastlocation;
    private String phonenumber;
    private RadarSearchManager radarSearchManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedprefrence=getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
          latitude=Double.parseDouble(sharedprefrence.getString("latitude","4.9E-324D"));
         longitude= Double.parseDouble(sharedprefrence.getString("longitude","4.9E-324D"));
        speed = sharedprefrence.getFloat("speed",0f);
        direction = sharedprefrence.getFloat("direction",0f);
        accuracy = sharedprefrence.getFloat("accuracy",0f);
        satellitesNum=sharedprefrence.getInt("satellitesNum",0);
        phonenumber=getPhoneNumber();
        radarSearchManager=RadarSearchManager.getInstance();
        radarSearchManager.startUploadAuto(this, 5000);

        radarSearchManager.addNearbyInfoListener(new RadarSearchListener() {
            @Override
            public void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult, RadarSearchError radarSearchError) {
                if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
                    if (radarNearbyResult.infoList!=null){
                        BitmapDescriptor icon=BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding);
                        for (RadarNearbyInfo info:radarNearbyResult.infoList) {
                            MarkerOptions markerOptions = new MarkerOptions().position(info.pt).icon(icon);
                            BDmap.addOverlay(markerOptions);
                        }
                    }
                    //获取成功，处理数据
                }
            }

            @Override
            public void onGetUploadState(RadarSearchError radarSearchError) {

            }

            @Override
            public void onGetClearInfoState(RadarSearchError radarSearchError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout=inflater.inflate(R.layout.fragment_location,container,false);
         mapView= (MapView) mLayout.findViewById(R.id.fragment_location_mapview);
        BDmap = mapView.getMap();
        lastlocation=new MyLocationData.Builder().latitude(latitude).longitude(longitude).accuracy(accuracy).direction(direction).speed(speed).satellitesNum(satellitesNum).build();
        BDmap.setMyLocationData(lastlocation);
        BitmapDescriptor bitmap=BitmapDescriptorFactory.fromResource(R.mipmap.arrow);
        MyLocationConfiguration myLocationConfiguration=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,bitmap);
        BDmap.setMyLocationConfigeration(myLocationConfiguration);
        locationClient = new LocationClient(getActivity().getApplicationContext());
        LocationClientOption option=new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        locationClient.setLocOption(option);
        locationClient.registerLocationListener(listener);
        return mLayout;
    }


    public static LocationFragment getInstance() {
        return new LocationFragment();
    }
    public void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        radarSearchManager.destroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        BDmap.setMyLocationEnabled(true);
        if (!locationClient.isStarted()){
            locationClient.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        BDmap.setMyLocationEnabled(false);
        if (locationClient.isStarted()){
            locationClient.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationClient.unRegisterLocationListener(listener);


    }


    BDLocationListener listener=new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation arg0) {
            if (arg0==null)return;
            if (arg0.getLatitude()==4.9E-324D||arg0.getLongitude()==4.9E-324D)return;
            MyLocationData locationData=new MyLocationData.Builder().accuracy(arg0.getRadius()).latitude(arg0.getLatitude()).longitude(arg0.getLongitude()).direction(arg0.getDirection()).speed(arg0.getSpeed()).build();
            BDmap.setMyLocationData(locationData);
           sharedprefrence.edit()
                   .putString("latitude",String.valueOf(arg0.getLatitude()))
                   .putString("longitude",String.valueOf(arg0.getLongitude()))
                   .putFloat("speed",arg0.getSpeed())
                   .putFloat("direction",arg0.getDirection())
                   .putFloat("accuracy",arg0.getRadius())
                   .putString("city",arg0.getCity())
                   .putInt("satellitesNum",arg0.getSatelliteNumber())
                   .apply();

            if (isFirst) {
                LatLng latlng=new LatLng(arg0.getLatitude(), arg0.getLongitude());
                MapStatusUpdate msu= MapStatusUpdateFactory.newLatLng(latlng);
                BDmap.animateMapStatus(msu);
                isFirst=false;

                Toast.makeText(getActivity().getApplication(),arg0.getAddrStr(),Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    };

    @Override
    public RadarUploadInfo onUploadInfoCallback() {
        RadarUploadInfo info = new RadarUploadInfo();
        info.comments = phonenumber;
        latitude=Double.parseDouble(sharedprefrence.getString("latitude","4.9E-324D"));
        longitude= Double.parseDouble(sharedprefrence.getString("longitude","4.9E-324D"));
        info.pt = new LatLng(latitude,longitude);
        RadarNearbySearchOption option = new RadarNearbySearchOption().centerPt(info.pt).pageNum(1).radius(2000);
        radarSearchManager.nearbyInfoRequest(option);
        return info;
    }

    private String getPhoneNumber(){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)getActivity().  getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }
}
