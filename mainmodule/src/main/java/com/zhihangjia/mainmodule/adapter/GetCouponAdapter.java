package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemGetCouponBinding;

import java.util.List;

public class GetCouponAdapter extends BaseDataBindingAdapter<Base, ItemGetCouponBinding>{

    public GetCouponAdapter(@Nullable List<Base> data) {
        super(R.layout.item_get_coupon, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemGetCouponBinding> helper, Base item, int position) {

    }
}
