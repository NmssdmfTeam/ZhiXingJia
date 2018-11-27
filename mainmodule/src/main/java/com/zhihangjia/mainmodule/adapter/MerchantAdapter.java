package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchantMainActivity;
import com.zhihangjia.mainmodule.databinding.ItemMerchantBinding;
import com.zhixingjia.bean.mainmodule.Seller;

import java.util.List;

/**
 * 商家adapter
 */
public class MerchantAdapter extends BaseDataBindingAdapter<Seller, ItemMerchantBinding> {
    public MerchantAdapter(@Nullable List<Seller> data) {
        super(R.layout.item_merchant, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMerchantBinding> helper, Seller item, int position) {
        ItemMerchantBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, MerchantMainActivity.class);
                mContext.startActivity(intent);
            }
        });

    }
}
