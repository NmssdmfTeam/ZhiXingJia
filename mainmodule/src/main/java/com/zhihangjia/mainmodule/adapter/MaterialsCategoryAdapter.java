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
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.List;

/**
 * Create by chenbin on 2018/11/15
 * <p>
 * <p>
 */
public class MaterialsCategoryAdapter extends BaseDataBindingAdapter<HouseBean.CateBean,ItemMaterialsCategoryBinding> {

    public MaterialsCategoryAdapter(@Nullable List<HouseBean.CateBean> data) {
        super(R.layout.item_materials_category,data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMaterialsCategoryBinding> helper, final HouseBean.CateBean item, int position) {
        ItemMaterialsCategoryBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equals(item.getCate_id()) && "全部分类".equals(item.getCate_name())) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, AllCategoriesActivity.class);
                    mContext.startActivity(intent);
                } else { //TODO 跳转商品搜索结果页面

                }
            }
        });
    }
}
