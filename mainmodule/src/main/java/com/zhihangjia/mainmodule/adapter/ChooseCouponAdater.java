package com.zhihangjia.mainmodule.adapter;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemChooseCouponBinding;
import com.zhixingjia.bean.personmodule.Coupon;

/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 * 平台/商家优惠券选择
 */

public class ChooseCouponAdater extends BaseDataBindingAdapter<Coupon, ItemChooseCouponBinding> {
    private ChooseCouponAdaterListener listener;
    public ChooseCouponAdater( ChooseCouponAdaterListener listener) {
        super(R.layout.item_choose_coupon);
        this.listener = listener;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemChooseCouponBinding> helper, Coupon item, int position) {
        ItemChooseCouponBinding binding = helper.getBinding();
        binding.setData(item);

        binding.tvUse.setOnClickListener(v -> listener.useCoupon(item));
    }

    public interface  ChooseCouponAdaterListener{
        void useCoupon(Coupon item);
    }
}
