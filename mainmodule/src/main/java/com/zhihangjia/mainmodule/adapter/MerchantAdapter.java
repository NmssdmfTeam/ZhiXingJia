package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemMerchantBinding;

import java.util.List;

/**
 * 商家adapter
 */
public class MerchantAdapter extends BaseDataBindingAdapter<Base, ItemMerchantBinding> {
    public MerchantAdapter(@Nullable List<Base> data) {
        super(R.layout.item_merchant, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMerchantBinding> helper, Base item, int position) {

    }
}
