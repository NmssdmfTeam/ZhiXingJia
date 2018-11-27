package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemMerchandiseBinding;
import com.zhixingjia.bean.mainmodule.Commodity;

import java.util.List;

/**
 * 商品adapter
 */
public class MerchandiseAdapter extends BaseDataBindingAdapter<Commodity, ItemMerchandiseBinding> {
    public MerchandiseAdapter(@Nullable List<Commodity> data) {
        super(R.layout.item_merchandise, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMerchandiseBinding> helper, final Commodity item, int position) {
        ItemMerchandiseBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(mContext, MerchandiseDetailActivity.class);
                bundle.putString(IntentConfig.COMMODITY_ID, item.getCommodity_id());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }


}
