package com.zhihangjia.mainmodule.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.CommonUtils;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.PermissionCompat;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.AdvertisingRotationViewPagerAdapter;
import com.zhihangjia.mainmodule.adapter.MaterialsCategoryAdapter;
import com.zhihangjia.mainmodule.adapter.MaterialsMarketAdapter;
import com.zhihangjia.mainmodule.bean.House;
import com.zhihangjia.mainmodule.callback.MarketFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMarketBinding;
import com.zhihangjia.mainmodule.databinding.ItemHotBrandBinding;
import com.zhihangjia.mainmodule.databinding.ItemMaterialsCrvheadBinding;
import com.zhihangjia.mainmodule.viewmodel.MarketFragmentVM;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.ArrayList;
import java.util.List;

import static android.databinding.DataBindingUtil.inflate;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 知行家首页-- 建材家居fragment
 * @date 2018/11/13 15:53
 */
public class MaterialsMarketFragment extends BaseFragment implements MarketFragmentCB {
    private final String TAG = MaterialsMarketFragment.class.getSimpleName();
    private FragmentMarketBinding binding;
    private MarketFragmentVM vm;
    private MaterialsMarketAdapter adapter;
    private AdvertisingRotationViewPagerAdapter viewPagerAdapter;
    private MaterialsCategoryAdapter materialsCategoryAdapter;
    private ItemMaterialsCrvheadBinding itemMaterialsCrvheadBinding;
    private int[] materialsIconRes = new int[]{R.drawable.air_conditioner, R.drawable.air_conditioner,
            R.drawable.air_conditioner, R.drawable.air_conditioner, R.drawable.air_conditioner,
            R.drawable.air_conditioner, R.drawable.air_conditioner, R.drawable.air_conditioner,
            R.drawable.air_conditioner, R.drawable.air_conditioner};
    private String[] materialsName = new String[]{"空调", "冰箱", "凳子", "组合柜", "空调", "沙发", "椅子", "衣柜", "餐桌", "全部"};
    private double latitude;
    private double longitude;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;

    @Override
    public BaseVM initViewModel() {
        vm = new MarketFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_market;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMarketBinding) baseBinding;
        adapter = new MaterialsMarketAdapter(new ArrayList());
        binding.crv.setAdapter(adapter);
        binding.crv.setLoadMoreEnable(false);
        //初始化首页头部
        itemMaterialsCrvheadBinding = inflate(getLayoutInflater(), R.layout.item_materials_crvhead, null, false);
        adapter.setHeaderView(itemMaterialsCrvheadBinding.getRoot());
        //初始化首页轮播图
        viewPagerAdapter = new AdvertisingRotationViewPagerAdapter(new ArrayList<Banner.CommomBanner>(), itemMaterialsCrvheadBinding.rpv);
        itemMaterialsCrvheadBinding.rpv.setAdapter(viewPagerAdapter);

        //建材家居分类
        itemMaterialsCrvheadBinding.crv.setLayoutManager(new GridLayoutManager(getContext(), 5));
        materialsCategoryAdapter = new MaterialsCategoryAdapter(new ArrayList<HouseBean.CateBean>());
        itemMaterialsCrvheadBinding.crv.setAdapter(materialsCategoryAdapter);

        //有品位
        for (int i = 0; i < 4; i++) {
            GlideImageView imageView = new GlideImageView(getContext());
            imageView.setRoundRadius(DensityUtil.dpToPx(getContext(), 2));
            imageView.setRoundtype(GlideImageView.RoundAll);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dpToPx(getContext(), 80), DensityUtil.dpToPx(getContext(), 80));
            layoutParams.leftMargin = DensityUtil.dpToPx(getContext(), 4);
            layoutParams.rightMargin = DensityUtil.dpToPx(getContext(), 4);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(R.drawable.pic_kitchen);
            itemMaterialsCrvheadBinding.llHome.addView(imageView);
        }
        vm.getHouseIndex(true);
        vm.getHouseBanner(true);
        setListener();
        getLocation();
    }

    private void setListener() {
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getHouseBanner(false);
                vm.getHouseIndex(false);
                getLocation();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    /**
     * 获取定位
     */
    private void getLocation() {
        if (!PermissionCompat.getInstance().checkLocationPermission(getActivity())) {
            return;
        }
        if (!CommonUtils.CheckAPSService(getActivity())) {
            new AlertDialog.Builder(getActivity()).setMessage("请打开GPS或者WIFI开关").setPositiveButton("开启", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//开定系统定位服务设置，需添加 import android.provider.Settings;
                    startActivity(intent);
                }
            }).show();
            return;
        }
        if (mLocationClient == null)
            mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());//初始化定位
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        if (mLocationOption == null)
            mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            adapter.setLocation(aMapLocation.getLongitude(),aMapLocation.getLatitude());
        }
    };

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void endFresh() {
        binding.crv.setRefreshing(false);
    }

    @Override
    public void setCatData(List<HouseBean.CateBean> cateBeans) {
        //设置分类
        materialsCategoryAdapter.setNewData(cateBeans);
    }

    @Override
    public void setMiddleBanner(Banner.CommomBanner middleBanner) {
        itemMaterialsCrvheadBinding.ivMiddleBanner.setVisibility(middleBanner != null ? View.VISIBLE : View.GONE);
        if (middleBanner != null) {
            GlideUtil.load(itemMaterialsCrvheadBinding.ivMiddleBanner, middleBanner.getImg_url());
        }
    }

    @Override
    public void setRollPagerBanner(List<Banner.CommomBanner> topBanner) {
        viewPagerAdapter.getAdvertisingRotations().clear();
        viewPagerAdapter.getAdvertisingRotations().addAll(topBanner);
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setBrandData(List<HouseBean.BrandsBean> brandData) {
        itemMaterialsCrvheadBinding.llBrand.removeAllViews();
        for (HouseBean.BrandsBean brandsBean : brandData) {
            ItemHotBrandBinding itemHotBrandBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_hot_brand, null, false);
            itemHotBrandBinding.setData(brandsBean);
            itemMaterialsCrvheadBinding.llBrand.addView(itemHotBrandBinding.getRoot());
        }
    }

    @Override
    public void setListData(List<House> houses) {
        adapter.notifyDataChangedAfterLoadMore(true, houses);
    }
}
