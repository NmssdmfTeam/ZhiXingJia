package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemConfirmOrderBinding;

import java.util.List;

/**
 * 确认订单adapter
 */
public class ConfirmOrderAdapter extends BaseDataBindingAdapter<Base, ItemConfirmOrderBinding> {

    public ConfirmOrderAdapter( @Nullable List<Base> data) {
        super(R.layout.item_confirm_order, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemConfirmOrderBinding> helper, Base item, int position) {

    }
}
