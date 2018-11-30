package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.ShopCouponAdapter;
import com.zhihangjia.mainmodule.callback.ShopCouponListCB;
import com.zhihangjia.mainmodule.databinding.ActivityShopCouponListBinding;
import com.zhihangjia.mainmodule.viewmodel.ShopCouponListVM;
import com.zhixingjia.bean.mainmodule.CouponSeller;

import java.util.ArrayList;
import java.util.List;

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
        binding.setVm(vm);
        adapter = new ShopCouponAdapter(new ArrayList<>());
        binding.crv.setAdapter(adapter);
        setListener();
        vm.getCouponSeller(true);
    }

    private void setListener() {
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getCouponSeller(true);
            }

            @Override
            public void onLoadMore() {
                vm.getCouponSeller(false);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_shop_coupon_list;
    }

    @Override
    public void setData(List<CouponSeller> couponSellers, boolean isRefresh) {
        if (isRefresh) {
            binding.crv.setRefreshing(false);
        }
        adapter.notifyDataChangedAfterLoadMore(isRefresh, couponSellers);
    }

    @Override
    public void setData(CouponSeller couponSeller, int position) {
        adapter.getData().set(position,couponSeller);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void deletData(int position) {
        adapter.getData().remove(position);
        vm.page = adapter.getData().get(adapter.getData().size() - 1).getCoupon_id();
        adapter.notifyItemRemoved(position);
    }
}
