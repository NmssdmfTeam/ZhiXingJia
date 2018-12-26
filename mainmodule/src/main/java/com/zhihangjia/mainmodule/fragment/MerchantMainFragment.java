package com.zhihangjia.mainmodule.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.AdvertisingRotationViewPagerAdapter;
import com.zhihangjia.mainmodule.adapter.MerchantMainAdapter;
import com.zhihangjia.mainmodule.callback.MerchantMainFragmentCB;
import com.zhihangjia.mainmodule.databinding.HeaderMerchantMainBinding;
import com.zhihangjia.mainmodule.viewmodel.MerchantMainFragmentVM;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.Commodity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 店铺主页，首页
 */
public class MerchantMainFragment extends BaseRecyclerViewFragment implements MerchantMainFragmentCB {

    private final String TAG = MerchantMainFragment.class.getSimpleName();

    private MerchantMainFragmentVM vm;
    private MerchantMainAdapter adapter;
    private AdvertisingRotationViewPagerAdapter viewPagerAdapter;
    private HeaderMerchantMainBinding headerMerchantMainBinding;

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MerchantMainFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new MerchantMainAdapter(list);
        headerMerchantMainBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.header_merchant_main, null, false);
        adapter.addHeaderView(headerMerchantMainBinding.getRoot());
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        super.initAll(view, savedInstanceState);
        binding.crv.getSrl().setEnabled(false);
        binding.crv.setLoadMoreEnable(false);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        binding.crv.setLayoutManager(layoutManager);
        //初始化首页轮播图
        viewPagerAdapter = new AdvertisingRotationViewPagerAdapter(new ArrayList<>(), headerMerchantMainBinding.rpv);
        headerMerchantMainBinding.rpv.setAdapter(viewPagerAdapter);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == vm.getList().size() + 1 || position == 0) ? layoutManager.getSpanCount() : 1;
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            vm.commodityInfoBeans = (List<Commodity>) bundle.getSerializable(IntentConfig.COMMODITY_INFO);
            vm.bannersBeans = (List<Banner.CommomBanner>) bundle.getSerializable(IntentConfig.BANNERS);
        }
        if (vm.commodityInfoBeans != null){
            adapter.notifyDataChangedAfterLoadMore(true, vm.commodityInfoBeans);
        }
        if (vm.bannersBeans != null) {
            loadBannerData();
        }
    }

    public void setData(List<Banner.CommomBanner> bannersBeans, List<Commodity> commodityInfoBeans) {
        vm.bannersBeans = bannersBeans;
        vm.commodityInfoBeans = commodityInfoBeans;
        if (vm.commodityInfoBeans != null){
            adapter.notifyDataChangedAfterLoadMore(true, vm.commodityInfoBeans);
        }
        if (vm.bannersBeans != null) {
            loadBannerData();
        }
    }

    private void loadBannerData() {
        viewPagerAdapter.getAdvertisingRotations().clear();
        viewPagerAdapter.getAdvertisingRotations().addAll(vm.bannersBeans);
        viewPagerAdapter.notifyDataSetChanged();
    }
}
