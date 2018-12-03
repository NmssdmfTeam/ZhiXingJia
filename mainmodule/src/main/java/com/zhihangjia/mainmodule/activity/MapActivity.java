package com.zhihangjia.mainmodule.activity;

import android.content.Intent;
import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;


public class MapActivity extends BaseTitleActivity {
    private final String TAG = MapActivity.class.getSimpleName();
    MapView mMapView = null;
    AMap aMap = null;

    @Override
    public String setTitle() {
        return "当前位置";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {

                double lat = bundle.getDouble(IntentConfig.LAT);
                double lng = bundle.getDouble(IntentConfig.LONG);
                String company_name = bundle.getString(IntentConfig.COMPANY_NAME);
                String location = bundle.getString(IntentConfig.LOCATION);
                LatLng latLng = new LatLng(lat,lng);

                //地图放大
                CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,0,0,0));
                aMap.moveCamera(mCameraUpdate);
                mCameraUpdate = CameraUpdateFactory.zoomTo(17);
                aMap.moveCamera(mCameraUpdate);

                MarkerOptions markerOption = new MarkerOptions().title(location).snippet(company_name);
                markerOption.position(latLng);
                // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                final Marker marker = aMap.addMarker(markerOption);
                marker.showInfoWindow();
            }
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_map;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
