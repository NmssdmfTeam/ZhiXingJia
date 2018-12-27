package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.util.MapUtils;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.MerchantAdapter;
import com.zhihangjia.mainmodule.callback.MerchantFragmentCB;
import com.zhihangjia.mainmodule.viewmodel.MerchantFragmentVM;

import java.util.List;

/**
 * 商家fragment
 */
public class MerchantFragment extends BaseRecyclerViewFragment implements MerchantFragmentCB {
    private final String TAG = MerchantFragment.class.getSimpleName();
    private MerchantFragmentVM vm;
    private MerchantAdapter adapter;
    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MerchantFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new MerchantAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        vm.getIntentData();
        super.initAll(view, savedInstanceState);
        MapUtils.getInstance().getLocation(getActivity(),mLocationListener);
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            adapter.setLocation(aMapLocation.getLongitude(),aMapLocation.getLatitude());
        }
    };

    public MerchantFragmentVM getVm() {
        return vm;
    }

    public void setVm(MerchantFragmentVM vm) {
        this.vm = vm;
    }

}
