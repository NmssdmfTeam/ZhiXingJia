package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemHotGoodsInfoBinding;
import java.util.List;

public class ItemGoodsInfoAdapter extends BaseDataBindingAdapter<Base,ItemHotGoodsInfoBinding> {
    public ItemGoodsInfoAdapter(@Nullable List<Base> data) {
        super(R.layout.item_hot_goods_info, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemHotGoodsInfoBinding> helper, Base item, int position) {
        ItemHotGoodsInfoBinding binding = helper.getBinding();
    }
}
