package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemMerchantMainBinding;
import com.zhixingjia.bean.mainmodule.Commodity;

import java.util.List;

/**
 * 店铺主页，所有商品
 */
public class MerchantAllAdapter extends BaseDataBindingAdapter<Commodity, ItemMerchantMainBinding> {
    public MerchantAllAdapter(@Nullable List<Commodity> data) {
        super(R.layout.item_merchant_main, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMerchantMainBinding> helper, Commodity item, int position) {
        ItemMerchantMainBinding itemMerchantMainBinding = helper.getBinding();
        itemMerchantMainBinding.setData(item);
        int margin = DensityUtil.dpToPx(mContext, 16);
        int mid = DensityUtil.dpToPx(mContext, 7.5f);
        if (position > 0) {
            if (position % 2 == 1) {
                helper.getBinding().getRoot().setPadding(margin, 0, mid, 0);
            } else {
                helper.getBinding().getRoot().setPadding(mid, 0, margin, 0);
            }
        }
        itemMerchantMainBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.COMMODITY_ID, item.getCommodity_id());
                intent.putExtras(bundle);
                intent.setClass(mContext, MerchandiseDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
