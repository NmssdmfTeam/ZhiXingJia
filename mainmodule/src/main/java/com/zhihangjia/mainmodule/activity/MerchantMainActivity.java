package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;

import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.CommonUtils;
import com.nmssdmf.commonlib.util.MapUtils;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.MerchantMainCB;
import com.zhihangjia.mainmodule.databinding.ActivityMerchantMainBinding;
import com.zhihangjia.mainmodule.databinding.IncludeMerchantMainHeaderBinding;
import com.zhihangjia.mainmodule.fragment.MerchantAllFragment;
import com.zhihangjia.mainmodule.fragment.MerchantEvaluateFragment;
import com.zhihangjia.mainmodule.fragment.MerchantMainFragment;
import com.zhihangjia.mainmodule.viewmodel.MerchantMainVM;
import com.zhihangjia.mainmodule.window.ChooseMapWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺详情
 */
public class MerchantMainActivity extends BaseActivity implements MerchantMainCB {
    private final String TAG = MerchantMainActivity.class.getSimpleName();

    private ActivityMerchantMainBinding binding;

    private MerchantMainFragment mainFragment;
    private MerchantAllFragment allFragment;
    private MerchantEvaluateFragment evaluateFragment;
    private List<Fragment> list = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private MerchantMainVM vm;
    private ChooseMapWindow chooseMapWindow;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_merchant_main;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MerchantMainVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityMerchantMainBinding) baseBinding;
        chooseMapWindow = new ChooseMapWindow(this);
        vm.initData();
        initTabLayout();

        initView();
        vm.getShopInfo();
    }


    private void initView() {
        IncludeMerchantMainHeaderBinding headerBinding = binding.iHeader;
        headerBinding.ivBack.setOnClickListener(v -> onBackPressed());

        headerBinding.ivCall.setOnClickListener(v -> {

        });

        headerBinding.ivAdress.setOnClickListener(v -> {

        });
    }

    private void initTabLayout() {
        mainFragment = new MerchantMainFragment();
        allFragment = new MerchantAllFragment();
        evaluateFragment = new MerchantEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.ID, vm.member_id);
        allFragment.setArguments(bundle);
        evaluateFragment.setArguments(bundle);
        list.add(mainFragment);
        list.add(allFragment);
        list.add(evaluateFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, list);

        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);

        binding.tl.getTabAt(0).setText("店铺首页");
        binding.tl.getTabAt(1).setText("全部商品");
        binding.tl.getTabAt(2).setText("口碑评价");

        binding.vp.setCurrentItem(vm.getType());
    }

    @Override
    public void setHeadData() {
        binding.iHeader.setData(vm.shopInfo.get().getMember());
        binding.iHeader.setVm(vm);
        binding.iHeader.acrbStore.setRating(Float.valueOf(vm.shopInfo.get().getMember().getScore()));
        //获取距离
        MapUtils.getInstance().getLocation(this, locationListener);
        //给店铺首页设置数据
        if (mainFragment != null && mainFragment.isAdded()) {
            mainFragment.setData(vm.shopInfo.get().getBanners(), vm.shopInfo.get().getCommodity_info());
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.BANNERS, (Serializable) vm.shopInfo.get().getBanners());
        bundle.putSerializable(IntentConfig.COMMODITY_INFO, (Serializable) vm.shopInfo.get().getCommodity_info());
        mainFragment.setArguments(bundle);
        if (evaluateFragment != null && evaluateFragment.isAdded()) {
            evaluateFragment.setMemberData(vm.shopInfo.get().getMember());
        }
        bundle = new Bundle();
        bundle.putString(IntentConfig.ID, vm.member_id);
        bundle.putSerializable(IntentConfig.MEMBER_INFO, vm.shopInfo.get().getMember());
        evaluateFragment.setArguments(bundle);
    }

    @Override
    public void callPhone() {
        CommonUtils.callPhone(this, vm.shopInfo.get().getMember().getCo_phone());
    }

    @Override
    public void showChooseMapWindow() {
        ChooseMapWindow.MapInfo mapInfo = new ChooseMapWindow.MapInfo();
        mapInfo.setLongitude(vm.shopInfo.get().getMember().getLongitude());
        mapInfo.setLatitude(vm.shopInfo.get().getMember().getLatitude());
        mapInfo.setName(vm.shopInfo.get().getMember().getCo_addr());
        chooseMapWindow.setMapInfo(mapInfo);
        chooseMapWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    private AMapLocationListener locationListener = aMapLocation -> {
        try {
            //测量距离
            LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());//当前定位
            LatLng shopLatLng = new LatLng(Double.valueOf(vm.shopInfo.get().getMember().getLatitude()), Double.valueOf(vm.shopInfo.get().getMember().getLongitude()));
            float distance = AMapUtils.calculateLineDistance(latLng, shopLatLng);
            //精确到小数点后两位
            distance = ((int) ((distance / 100000f) * 100 + 0.005)) / 100;
            vm.shopInfo.get().getMember().setDistance(distance + "km");
        } catch (Exception e) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MapUtils.getInstance().unRegisterListener(locationListener);
    }
}
