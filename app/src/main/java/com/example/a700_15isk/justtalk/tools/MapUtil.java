package com.example.a700_15isk.justtalk.tools;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.a700_15isk.justtalk.MyApp;

/**
 * Created by 700-15isk on 2017/8/25.
 */

public class MapUtil {
    public static AMapLocationClientOption mLocationOption = null;
    public static AMapLocationClient mLocationClient = null;

//初始化AMapLocationClientOption对象
   public static void getLocation(){
       mLocationOption = new AMapLocationClientOption();
       mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
       mLocationOption.setOnceLocation(true);
       mLocationOption.setOnceLocationLatest(true);
       mLocationOption.setNeedAddress(true);
       mLocationOption.setHttpTimeOut(20000);
       mLocationOption.setLocationCacheEnable(false);
       mLocationClient.setLocationOption(mLocationOption);
       mLocationClient = new AMapLocationClient(MyApp.getMyAppContext());
       mLocationClient.setLocationOption(mLocationOption);
       mLocationClient.startLocation();
       mLocationClient.setLocationListener(new AMapLocationListener() {
           @Override
           public void onLocationChanged(AMapLocation aMapLocation) {
               if (aMapLocation != null) {
                   if (aMapLocation.getErrorCode() == 0) {
                   }else {
                       Log.e("AmapError","location Error, ErrCode:"
                               + aMapLocation.getErrorCode() + ", errInfo:"
                               + aMapLocation.getErrorInfo());
                   }
               }
           }
       });
   }
}
