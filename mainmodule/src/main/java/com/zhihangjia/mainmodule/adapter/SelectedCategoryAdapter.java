package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemCategorySelectBinding;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.List;

/**
 * Create by chenbin on 2018/11/15
 * <p>
 * <p>
 */
public class SelectedCategoryAdapter extends BaseDataBindingAdapter<HouseBean.CateBean, ItemCategorySelectBinding> {

    public SelectedCategoryAdapter(@Nullable List<HouseBean.CateBean> data) {
        super(R.layout.item_category_select, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemCategorySelectBinding> helper, final HouseBean.CateBean item, int position) {
        ItemCategorySelectBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(v -> {
            item.setSelect(!item.isSelect());
        });
    }
}
