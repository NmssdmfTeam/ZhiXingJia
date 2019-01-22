package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemCouponCenterBinding;
import com.zhixingjia.bean.mainmodule.CenterCoupon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CouponCenterAdapter extends BaseDataBindingAdapter<CenterCoupon, ItemCouponCenterBinding> {
    public CouponCenterAdapter(@Nullable List<CenterCoupon> data) {
        super(R.layout.item_coupon_center,data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemCouponCenterBinding> helper, CenterCoupon item, int position) {
        ItemCouponCenterBinding binding = helper.getBinding();
        binding.setData(item);
        float persent = 100;
        if (!item.getAllsum().equals(item.getReceive_sum())) {
            persent = new BigDecimal(item.getReceive_sum()).divide(new BigDecimal(item.getAllsum()), 3, RoundingMode.DOWN).multiply(new BigDecimal(100)).floatValue();
        }
        binding.couponSurplusBar.setCurrentValues(persent);
    }
}
