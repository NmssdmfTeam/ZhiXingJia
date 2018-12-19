package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.AllLifeCategoriesActivity;
import com.zhihangjia.mainmodule.activity.LifeServiceActivity;
import com.zhihangjia.mainmodule.databinding.ItemLifeServiceCategoryBinding;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.io.Serializable;
import java.util.List;

public class ServiceAdapter extends BaseDataBindingAdapter<IndexBean.LifeCate, ItemLifeServiceCategoryBinding> {
    public ServiceAdapter(@Nullable List<IndexBean.LifeCate> data) {
        super(R.layout.item_life_service_category, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemLifeServiceCategoryBinding> helper, IndexBean.LifeCate item, int position) {
        ItemLifeServiceCategoryBinding binding = helper.getBinding();
        binding.setData(item);
        if ("0".equals(item.getCate_id())) {
            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClass(mContext,AllLifeCategoriesActivity.class);
                mContext.startActivity(intent);
            });
        } else {
            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClass(mContext, LifeServiceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.ID, item.getCate_id());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            });
        }
    }
}
