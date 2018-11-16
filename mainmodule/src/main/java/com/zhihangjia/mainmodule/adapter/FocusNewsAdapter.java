package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemHeadlineBinding;

import java.util.List;

/**
* @description 要闻动态
* @author chenbin
* @date 2018/11/16 17:09
* @version v3.2.0
*/
public class FocusNewsAdapter extends BaseDataBindingAdapter<Base, ItemHeadlineBinding> {
    public FocusNewsAdapter(@Nullable List<Base> data) {
        super(R.layout.item_headline, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemHeadlineBinding> helper, Base item, int position) {

    }
}
