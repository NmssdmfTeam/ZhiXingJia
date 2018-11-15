package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemMerchantMainBinding;

import java.util.List;

/**
 * 店铺主页，首页adapter
 */
public class MerchantMainAdapter extends BaseDataBindingAdapter<Base, ItemMerchantMainBinding> {

    public MerchantMainAdapter(@Nullable List<Base> data) {
        super(R.layout.item_merchant_main, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMerchantMainBinding> helper, Base item, int position) {

    }
}
