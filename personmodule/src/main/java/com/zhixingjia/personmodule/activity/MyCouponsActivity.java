package com.zhixingjia.personmodule.activity;

import android.os.Bundle;
import android.view.Gravity;

import com.nmssdmf.commonlib.activity.BaseTitleRecyclerViewActivity;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhixingjia.personmodule.adapter.MyCouponAdater;
import com.zhixingjia.personmodule.callback.MyCouponsCB;
import com.zhixingjia.personmodule.viewmodule.MyCouponsVM;
import com.zhixingjia.personmodule.window.CouponDescriptionWindow;

import java.util.List;

public class MyCouponsActivity extends BaseTitleRecyclerViewActivity implements MyCouponsCB, MyCouponAdater.MyCouponAdaterListner{

    private final String TAG = MyCouponsActivity.class.getSimpleName();
    private MyCouponAdater adater;
    private MyCouponsVM vm;
    private CouponDescriptionWindow couponDescriptionWindow;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseTitleRecyclerViewVM initTitleRecyclerViewViewModel() {
        vm = new MyCouponsVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adater = new MyCouponAdater(list, this);
        return adater;
    }

    @Override
    public String setTitle() {
        return "我的优惠券";
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        super.initAll(savedInstanceState);
        binding.crv.setPadding(0, DensityUtil.dpToPx(this, 12), 0, 0);
    }

    @Override
    public void showCouponDescriptionWindow() {
        if (couponDescriptionWindow == null) {
            couponDescriptionWindow = new CouponDescriptionWindow(this, null);
        }

        couponDescriptionWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);
    }
}
