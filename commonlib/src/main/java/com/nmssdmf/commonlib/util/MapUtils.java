package com.nmssdmf.commonlib.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class MapUtils {

    private static MapUtils instance;

    private MapUtils() {
        //
    }

    public static MapUtils getInstance() {
        if (instance == null) {
            synchronized (ToastUtil.class) {
                if (instance == null) {
                    instance = new MapUtils();
                }
            }
        }
        return instance;
    }

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;

    /**
     * 获取定位
     * 用完注意
     */
    public void getLocation(final Activity activity, AMapLocationListener listener) {
        if (!PermissionCompat.getInstance().checkLocationPermission(activity)) {
            return;
        }
        if (!CommonUtils.CheckAPSService(activity)) {
            new AlertDialog.Builder(activity).setMessage("请打开GPS或者WIFI开关").setPositiveButton("开启", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//开定系统定位服务设置，需添加 import android.provider.Settings;
                    activity.startActivity(intent);
                }
            }).show();
            return;
        }
        if (mLocationClient == null)
            mLocationClient = new AMapLocationClient(activity.getApplicationContext());//初始化定位
        //设置定位回调监听
        mLocationClient.setLocationListener(listener);
        if (mLocationOption == null)
            mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

}
