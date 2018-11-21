package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.AllCategoriesActivity;
import com.zhihangjia.mainmodule.bean.MaterialsCategoryBean;
import com.zhihangjia.mainmodule.databinding.ItemMaterialsCategoryBinding;

import java.util.List;

/**
 * Create by chenbin on 2018/11/15
 * <p>
 * <p>
 */
public class MaterialsCategoryAdapter extends BaseDataBindingAdapter<MaterialsCategoryBean,ItemMaterialsCategoryBinding> {

    public MaterialsCategoryAdapter(@Nullable List<MaterialsCategoryBean> data) {
        super(R.layout.item_materials_category,data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMaterialsCategoryBinding> helper, MaterialsCategoryBean item, int position) {
        ItemMaterialsCategoryBinding binding = helper.getBinding();
        binding.ivIcon.setImageResource(item.getResIconId());
        binding.tvName.setText(item.getTitle());
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, AllCategoriesActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
