package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemDailyHotNewsBinding;

import java.util.List;

public class DailyHotNewsAdapter extends BaseDataBindingAdapter<Base, ItemDailyHotNewsBinding> {
    public DailyHotNewsAdapter(@Nullable List<Base> data) {
        super(R.layout.item_daily_hot_news, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemDailyHotNewsBinding> helper, Base item, int position) {

    }
}
