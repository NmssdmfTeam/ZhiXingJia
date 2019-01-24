package com.zhihangjia.mainmodule.adapter;


import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemMyCouponCardBinding;
import com.zhihangjia.mainmodule.window.QrCodeWindow;
import com.zhixingjia.bean.mainmodule.CouponCard;

import java.util.List;

public class MyCouponCardAdapter extends BaseDataBindingAdapter<CouponCard, ItemMyCouponCardBinding> {
    private OnItemClickListener listener;

    public MyCouponCardAdapter(@Nullable List<CouponCard> data, OnItemClickListener listener) {
        super(R.layout.item_my_coupon_card, data);
        this.listener = listener;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMyCouponCardBinding> helper, CouponCard item, int position) {
        ItemMyCouponCardBinding binding = helper.getBinding();
        binding.setData(item);
        binding.tvRecieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUseClick(item);
            }
        });
    }

    public interface OnItemClickListener {
        void onUseClick(CouponCard item);
    }
}
