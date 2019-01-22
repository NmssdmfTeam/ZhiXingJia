package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.CouponCenterAdapter;
import com.zhihangjia.mainmodule.viewmodel.CouponCenterVM;

import java.util.List;

/**
* @description 领券中心
* @author chenbin
* @date 2019/1/22 11:13
* @version v3.2.0
*/
public class CouponCenterFragment extends BaseRecyclerViewFragment {
    private CouponCenterAdapter adapter;
    private final String TAG = CouponCenterFragment.class.getSimpleName();
    private CouponCenterVM vm;

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new CouponCenterVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new CouponCenterAdapter(list);
        return adapter;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        super.initAll(view, savedInstanceState);
        binding.llc.setBackgroundColor(getResources().getColor(R.color.background_color));
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
