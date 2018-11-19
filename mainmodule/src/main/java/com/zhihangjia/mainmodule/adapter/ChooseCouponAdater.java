package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemChooseCouponBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 * 平台/商家优惠券选择
 */

public class ChooseCouponAdater extends BaseDataBindingAdapter<Base, ItemChooseCouponBinding> {

    public ChooseCouponAdater(@Nullable List<Base> data) {
        super(R.layout.item_choose_coupon, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemChooseCouponBinding> helper, Base item, int position) {

    }
}
