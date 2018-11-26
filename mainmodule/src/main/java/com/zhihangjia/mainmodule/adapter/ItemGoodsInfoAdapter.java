package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemHotGoodsInfoBinding;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.List;

public class ItemGoodsInfoAdapter extends BaseDataBindingAdapter<HouseBean.ProductBean,ItemHotGoodsInfoBinding> {
    public ItemGoodsInfoAdapter(@Nullable List<HouseBean.ProductBean> data) {
        super(R.layout.item_hot_goods_info, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemHotGoodsInfoBinding> helper, HouseBean.ProductBean item, int position) {
        ItemHotGoodsInfoBinding binding = helper.getBinding();
        binding.setData(item);
    }
}
