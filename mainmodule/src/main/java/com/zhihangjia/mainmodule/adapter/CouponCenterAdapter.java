package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.CouponDescriptionActivity;
import com.zhihangjia.mainmodule.databinding.ItemCouponCenterBinding;
import com.zhixingjia.bean.mainmodule.CenterCoupon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CouponCenterAdapter extends BaseDataBindingAdapter<CenterCoupon, ItemCouponCenterBinding> {
    public OnItemClickListener itemClickListener;


    public CouponCenterAdapter(@Nullable List<CenterCoupon> data, OnItemClickListener itemClickListener) {
        super(R.layout.item_coupon_center,data);
        this.itemClickListener = itemClickListener;
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
        binding.tvRecieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onRecieveClick(item, position);
            }
        });
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, CouponDescriptionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentConfig.COUPONCENTER_INFO, item);
                bundle.putInt(IntentConfig.POSITION, position);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    public interface OnItemClickListener {
        void onRecieveClick(CenterCoupon item, int position);
    }
}
