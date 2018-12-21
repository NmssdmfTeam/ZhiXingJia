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
import com.zhihangjia.mainmodule.activity.LifeServiceDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemLifeSeviceBinding;
import com.zhixingjia.bean.mainmodule.LifeService;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class LifeServiceAdapter extends BaseDataBindingAdapter<LifeService, ItemLifeSeviceBinding> {

    public LifeServiceAdapter( @Nullable List<LifeService> data) {
        super(R.layout.item_life_sevice, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemLifeSeviceBinding> helper, LifeService item, int position) {
        ItemLifeSeviceBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(mContext, LifeServiceDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.ID, item.getInfo_id());
            bundle.putString(IntentConfig.NAME, item.getInfo_name());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }
}
