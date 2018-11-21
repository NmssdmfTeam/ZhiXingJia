package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.ShopCouponAdapter;
import com.zhihangjia.mainmodule.callback.ShopCouponListCB;
import com.zhihangjia.mainmodule.databinding.ActivityShopCouponListBinding;
import com.zhihangjia.mainmodule.viewmodel.ShopCouponListVM;

/**
 * 商家优惠券
 */
public class ShopCouponListActivity extends BaseTitleActivity implements ShopCouponListCB{

    private final String TAG = ShopCouponListActivity.class.getSimpleName();
    private ShopCouponAdapter adapter;
    private ActivityShopCouponListBinding binding;
    private ShopCouponListVM vm;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ShopCouponListVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "商家优惠券";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityShopCouponListBinding) baseViewBinding;
        vm.initData();
        adapter = new ShopCouponAdapter(vm.getList());
        binding.crv.setAdapter(adapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_shop_coupon_list;
    }
}
