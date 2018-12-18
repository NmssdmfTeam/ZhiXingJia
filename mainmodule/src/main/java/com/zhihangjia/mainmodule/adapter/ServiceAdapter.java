package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemLifeServiceCategoryBinding;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

public class ServiceAdapter extends BaseDataBindingAdapter<IndexBean.LifeCate, ItemLifeServiceCategoryBinding> {
    public ServiceAdapter(@Nullable List<IndexBean.LifeCate> data) {
        super(R.layout.item_life_service_category, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemLifeServiceCategoryBinding> helper, IndexBean.LifeCate item, int position) {
        ItemLifeServiceCategoryBinding binding = helper.getBinding();
        binding.setData(item);
    }
}
