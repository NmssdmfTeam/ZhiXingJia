package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.ImageUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.AllCategoriesActivity;
import com.zhihangjia.mainmodule.activity.MerchantMerchandiseActivity;
import com.zhihangjia.mainmodule.activity.SearchResultActivity;
import com.zhihangjia.mainmodule.databinding.ItemMaterialsCategoryBinding;
import com.zhihangjia.mainmodule.viewmodel.SearchFragmentVM;
import com.zhihangjia.mainmodule.viewmodel.SearchResultVM;
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
        if ("全部分类".equals(item.getCate_name())) {
            String allCategoryIcon = ImageUtil.getUriFromDrawableRes(mContext, R.drawable.all_category);
            item.setCate_img(allCategoryIcon);
        }
        binding.setData(item);
        binding.getRoot().setOnClickListener(v -> {
            if (TextUtils.isEmpty(item.getCate_id()) && "全部分类".equals(item.getCate_name())) {
                Intent intent = new Intent();
                intent.setClass(mContext, AllCategoriesActivity.class);
                mContext.startActivity(intent);
            } else {
                Intent intent = new Intent();
                intent.setClass(mContext, MerchantMerchandiseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.ID, item.getCate_id());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
