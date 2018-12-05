package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemHotGoodsInfoBinding;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.List;

public class ItemGoodsInfoAdapter extends BaseDataBindingAdapter<HouseBean.ProductBean,ItemHotGoodsInfoBinding> {
    public ItemGoodsInfoAdapter(@Nullable List<HouseBean.ProductBean> data) {
        super(R.layout.item_hot_goods_info, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemHotGoodsInfoBinding> helper, HouseBean.ProductBean item, int position) {
        ItemHotGoodsInfoBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
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
