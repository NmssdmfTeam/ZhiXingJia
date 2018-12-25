package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.SetCouponActivity;
import com.zhihangjia.mainmodule.databinding.ItemShopCouponBinding;
import com.zhixingjia.bean.mainmodule.CouponSeller;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 * 商家优惠券列表
 */

public class ShopCouponAdapter extends BaseDataBindingAdapter<CouponSeller, ItemShopCouponBinding>{
    public final String TAG = ShopCouponAdapter.class.getSimpleName();

    public ShopCouponAdapter(@Nullable List<CouponSeller> data) {
        super(R.layout.item_shop_coupon, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemShopCouponBinding> helper, CouponSeller item, int position) {
        ItemShopCouponBinding binding = helper.getBinding();
        binding.setData(item);
        if ("1".equals(item.getStatus())) {
            binding.tvStatus.setText("状态:开启");
        } else {
            binding.tvStatus.setText("状态:关闭");
        }
        binding.tvNum.setText("领取数量:"+item.getNumber()+"张");
        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.COUPON_ID, item.getCoupon_id());
            bundle.putInt(IntentConfig.POSITION, position);
            bundle.putSerializable(IntentConfig.COUPON_SELLER, item);
            intent.putExtras(bundle);
            intent.setClass(mContext ,SetCouponActivity.class);
            mContext.startActivity(intent);
        });
    }
}
