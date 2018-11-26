package com.nmssdmf.commonlib.util;

import android.content.Context;
import android.location.LocationManager;

import java.util.List;

/**
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class CommonUtils {

    /**
     * 判断list里都有不为空的值
     *
     * @param args
     * @param index 事先已知数组里有几个数值
     * @return
     */
    public static boolean isFull(List<String> args, int index) {
        for (int i = 0; i < index; i++) {
            if (isEmpty(args.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串是否为null
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
//        JLog.i(TAG, "isEmpty obj:" + obj);
        String str = obj + "";
        if (str.equals("") || str.equals("null")) {
            return true;
        }
        return false;
    }

    /*校验定位服务是否开启*/
    public static boolean CheckAPSService(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }
}
