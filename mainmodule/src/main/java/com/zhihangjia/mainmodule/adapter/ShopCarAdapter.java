package com.zhihangjia.mainmodule.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemShopCarBinding;
import com.zhihangjia.mainmodule.databinding.ItemShopCarMerchandiseBinding;
import com.zhihangjia.mainmodule.databinding.ItemShopCarMerchandiseSpecificationBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 */

public class ShopCarAdapter extends BaseDataBindingAdapter<Base, ItemShopCarBinding>{
    public ShopCarAdapter( @Nullable List<Base> data) {
        super(R.layout.item_shop_car, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemShopCarBinding> helper, Base item, int position) {
        ItemShopCarBinding binding = helper.getBinding();

        for (int i = 0; i< 3; i++) {
            ItemShopCarMerchandiseBinding merchandiseBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_shop_car_merchandise, null, false);
            for (int j = 0; j < 3 ; j++) {
                ItemShopCarMerchandiseSpecificationBinding specificationBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_shop_car_merchandise_specification, null, false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dpToPx(mContext, 46));
                merchandiseBinding.llSpecification.addView(specificationBinding.getRoot(), params);
            }
            binding.llMerchandise.addView(merchandiseBinding.getRoot());
        }
    }
}
