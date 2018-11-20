package com.zhihangjia.mainmodule.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemOrderBinding;
import com.zhihangjia.mainmodule.databinding.ItemOrderMerchandiseBinding;
import com.zhihangjia.mainmodule.databinding.ItemOrderWaitForPayBinding;

import java.util.List;

public class OrderWaitForPayAdapter extends BaseDataBindingAdapter<Base, ItemOrderWaitForPayBinding> {
    public OrderWaitForPayAdapter(@Nullable List<Base> data) {
        super(R.layout.item_order_wait_for_pay, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemOrderWaitForPayBinding> helper, Base item, int position) {
        ItemOrderWaitForPayBinding binding = helper.getBinding();

        for (int i = 0; i < 2; i++) {
            ItemOrderMerchandiseBinding merchandiseBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_order_merchandise, null, false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dpToPx(mContext, 114));
            binding.llOrderMerchandise.addView(merchandiseBinding.getRoot(), params);
        }
    }
}
