package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemLifeSeviceBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class LifeServiceAdapter extends BaseDataBindingAdapter<Base, ItemLifeSeviceBinding> {

    public LifeServiceAdapter( @Nullable List<Base> data) {
        super(R.layout.item_life_sevice, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemLifeSeviceBinding> helper, Base item, int position) {

    }
}
