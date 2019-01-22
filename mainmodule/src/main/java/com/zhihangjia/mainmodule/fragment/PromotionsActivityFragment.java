package com.zhihangjia.mainmodule.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.AdvertisingRotationViewPagerAdapter;
import com.zhihangjia.mainmodule.adapter.PromotionsActivityAdapter;
import com.zhihangjia.mainmodule.callback.PromotionsCB;
import com.zhihangjia.mainmodule.databinding.ItemPromotionsActivityHeadvBinding;
import com.zhihangjia.mainmodule.viewmodel.PromotionsActivityVM;
import com.zhixingjia.bean.mainmodule.Banner;

import java.util.ArrayList;
import java.util.List;

/**
* @description 展示中心--促销活动
* @author chenbin
* @date 2019/1/21 17:35
* @version v3.2.0
*/
public class PromotionsActivityFragment extends BaseRecyclerViewFragment implements PromotionsCB {
    private PromotionsActivityVM vm;
    private final String TAG = PromotionsActivityFragment.class.getSimpleName();
    private PromotionsActivityAdapter adapter;
    private ItemPromotionsActivityHeadvBinding headvBinding;
    private AdvertisingRotationViewPagerAdapter viewPagerAdapter;

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new PromotionsActivityVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new PromotionsActivityAdapter(list);
        return adapter;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        super.initAll(view, savedInstanceState);
        //添加头部的广告栏
        headvBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.item_promotions_activity_headv, null, false);
        viewPagerAdapter = new AdvertisingRotationViewPagerAdapter(new ArrayList<Banner.CommomBanner>(), headvBinding.rpv);
        headvBinding.rpv.setAdapter(viewPagerAdapter);
        vm.getBanners();
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void setHeadV(List<Banner.CommomBanner> banners) {
        if (banners == null || banners.size() == 0) {
            adapter.removeAllHeaderView();
        } else {
            adapter.setHeaderView(headvBinding.getRoot());
        }
        viewPagerAdapter.getAdvertisingRotations().clear();
        viewPagerAdapter.getAdvertisingRotations().addAll(banners);
        viewPagerAdapter.notifyDataSetChanged();
    }
}
