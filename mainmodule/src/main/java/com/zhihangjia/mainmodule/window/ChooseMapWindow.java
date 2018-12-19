package com.zhihangjia.mainmodule.window;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.amap.api.maps2d.model.LatLng;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemMapButtonBinding;
import com.zhihangjia.mainmodule.databinding.WindowChooseMapBinding;
import com.zhihangjia.mainmodule.viewmodel.ChooseMapWindowVM;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 选择地图
 * @date 2018/12/19 16:06
 */
public class ChooseMapWindow extends PopupWindow {
    private ChooseMapWindowVM vm;
    private WindowChooseMapBinding binding;
    private Context context;
    private MapInfo mapInfo;

    public MapInfo getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }

    /**
     * @param context
     */
    public ChooseMapWindow(final Context context) {
        this.context = context;
        Map<String, String> maps = new HashMap<>();
        maps.put("百度地图", "com.baidu.BaiduMap");
        maps.put("高德地图", "com.autonavi.minimap");
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_choose_map, null, false);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(MATCH_PARENT);
        setContentView(binding.getRoot());
        for (String key : maps.keySet()) {
            ItemMapButtonBinding itemMapButtonBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_map_button, null, false);
            itemMapButtonBinding.btnMapName.setText(key);
            itemMapButtonBinding.getRoot().setOnClickListener(v -> toMapApp(itemMapButtonBinding.btnMapName.getText().toString()));
            binding.llMapApps.addView(itemMapButtonBinding.getRoot());
        }
        if (isHaveTencentMap()) {
            ItemMapButtonBinding itemMapButtonBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_map_button, null, false);
            itemMapButtonBinding.btnMapName.setText("腾讯地图");
            binding.llMapApps.addView(itemMapButtonBinding.getRoot());
            itemMapButtonBinding.getRoot().setOnClickListener(v -> toMapApp(itemMapButtonBinding.btnMapName.getText().toString()));
        }
        binding.btnCancel.setOnClickListener(v -> dismiss());
        setOnDismissListener(() -> WindowUtil.setBackgroundAlpha((Activity) context, 1f));
    }

    private void toMapApp(String mapName) {
        if (mapInfo == null) {
            return;
        }
        if ("百度地图".equals(mapName)) {
            toBaiduMap(context, mapInfo);
        } else if ("高德地图".equals(mapName)) {
            toGaoDeMap(context, mapInfo);
        } else if ("腾讯地图".equals(mapName)) {
            toTencentMap(context, mapInfo);
        }
    }

    /***
     * 是否安装腾讯地图
     * @return
     */
    public static boolean isHaveTencentMap() {
        try {
            if (!new File("/data/data/" + "com.tencent.map").exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        WindowUtil.setBackgroundAlpha((Activity) parent.getContext(), 0.5f);
    }

    public static void toBaiduMap(Context context, MapInfo mapInfo) {
        if (isAvilible(context, "com.baidu.BaiduMap")) {//传入指定应用包名

            try {
                LatLng endPoint = GCJ2BD(new LatLng(Double.valueOf(mapInfo.getLatitude()), Double.valueOf(mapInfo.getLongitude())));//坐标转换
                Intent intent = Intent.parseUri("intent://map/direction?" +
                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                        "destination=latlng:" + endPoint.latitude + "," + endPoint.longitude + "|name:" + mapInfo.getName() +        //终点
                        "&mode=driving&" +          //导航路线方式
                        "region=浙江" +           //
                        "&src=com.zhihangjia.project#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end", 0);
                context.startActivity(intent); //启动调用
            } catch (URISyntaxException e) {
                JLog.e("intent", e.getMessage());
            }
        } else {//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
        }
    }

    public static void toGaoDeMap(Context context, MapInfo mapInfo) {
        if (isAvilible(context, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.parseUri("androidamap://navi?sourceApplication=com.zhihangjia.project&poiname="
                        + mapInfo.getName() + "&lat=" + mapInfo.getLatitude() + "&lon=" + mapInfo.getLongitude() + "&dev=0", 0);
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
        }
    }

    public static void toTencentMap(Context context, MapInfo mapInfo) {
        if (isHaveTencentMap()) {
            try {
                LatLng endPoint = new LatLng(Double.valueOf(mapInfo.getLatitude()), Double.valueOf(mapInfo.getLongitude()));//坐标转换
                StringBuffer stringBuffer = new StringBuffer("qqmap://map/routeplan?type=drive")
                        .append("&tocoord=").append(endPoint.latitude).append(",").append(endPoint.longitude).append("&to=" + mapInfo.getName());
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * BD-09 坐标转换成 GCJ-02 坐标
     */
    public static LatLng BD2GCJ(LatLng bd) {
        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double lng = z * Math.cos(theta);//lng
        double lat = z * Math.sin(theta);//lat
        return new LatLng(lat, lng);
    }

    /**
     * GCJ-02 坐标转换成 BD-09 坐标
     */
    public static LatLng GCJ2BD(LatLng bd) {
        double x = bd.longitude, y = bd.latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new LatLng(tempLat, tempLon);
    }

    public static class MapInfo {
        private String name;        //目的地名称
        private String region;      //省份
        private String longitude;   //地址经度
        private String latitude;    //地址纬度

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }
}
