package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemShopCouponBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 * 商家优惠券列表
 */

public class ShopCouponAdapter extends BaseDataBindingAdapter<Base, ItemShopCouponBinding>{
    public final String TAG = ShopCouponAdapter.class.getSimpleName();

    public ShopCouponAdapter(@Nullable List<Base> data) {
        super(R.layout.item_shop_coupon, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemShopCouponBinding> helper, Base item, int position) {
        ItemShopCouponBinding binding = helper.getBinding();
    }
}
