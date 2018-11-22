package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemFlipOverBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/22 0022.
 */

public class FlipOverAdapter extends BaseDataBindingAdapter<String, ItemFlipOverBinding> {

    public FlipOverAdapter(@Nullable List<String> data) {
        super(R.layout.item_flip_over, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemFlipOverBinding> helper, String item, int position) {
        helper.getBinding().tvPage.setText(item);
    }

}
