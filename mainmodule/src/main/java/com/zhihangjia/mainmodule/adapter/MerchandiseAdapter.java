package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemMerchandiseBinding;
import com.zhihangjia.mainmodule.databinding.ItemMerchantBinding;

import java.util.List;

/**
 * 商品adapter
 */
public class MerchandiseAdapter extends BaseDataBindingAdapter<Base, ItemMerchandiseBinding> {
    public MerchandiseAdapter(@Nullable List<Base> data) {
        super(R.layout.item_merchandise, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMerchandiseBinding> helper, Base item, int position) {

    }


}
