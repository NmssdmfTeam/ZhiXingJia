package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.databinding.ItemLifeServiceCategoryBinding;
import com.zhihangjia.mainmodule.R;

import java.util.List;

public class ServiceAdapter extends BaseDataBindingAdapter<Base, ItemLifeServiceCategoryBinding> {
    public ServiceAdapter(@Nullable List<Base> data) {
        super(R.layout.item_life_service_category, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemLifeServiceCategoryBinding> helper, Base item, int position) {
        helper.getBinding().tvServiceName.setText(item.getMessage());
    }
}
