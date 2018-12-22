package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.LifeServiceActivity;
import com.zhihangjia.mainmodule.databinding.ItemCategoryBinding;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Create by chenbin on 2018/11/15
 * <p>
 * <p>
 */
public class AllLifeCategoryAdapter extends BaseDataBindingAdapter<HouseBean.CateBean, ItemCategoryBinding> {

    public AllLifeCategoryAdapter(@Nullable List<HouseBean.CateBean> data) {
        super(R.layout.item_category, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemCategoryBinding> helper, final HouseBean.CateBean item, int position) {
        ItemCategoryBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(mContext, LifeServiceActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.ID, item.getCate_id());
            bundle.putString(IntentConfig.NAME, item.getCate_name());
            bundle.putSerializable(IntentConfig.LIFE_CATE, (Serializable) getData());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }
}
