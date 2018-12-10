package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchantMerchandiseActivity;
import com.zhihangjia.mainmodule.activity.SearchResultActivity;
import com.zhihangjia.mainmodule.databinding.ItemCategoryBinding;
import com.zhihangjia.mainmodule.viewmodel.SearchFragmentVM;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.List;

/**
 * Create by chenbin on 2018/11/15
 * <p>
 * <p>
 */
public class CategoryAdapter extends BaseDataBindingAdapter<HouseBean.CateBean, ItemCategoryBinding> {

    public CategoryAdapter(@Nullable List<HouseBean.CateBean> data) {
        super(R.layout.item_category, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemCategoryBinding> helper, final HouseBean.CateBean item, int position) {
        ItemCategoryBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(mContext, MerchantMerchandiseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.ID, item.getCate_id());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }
}
