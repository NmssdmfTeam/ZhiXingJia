package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemMyPostBinding;

import java.util.List;

public class MyPostAdapter extends BaseDataBindingAdapter<Base, ItemMyPostBinding> {
    public MyPostAdapter(@Nullable List<Base> data) {
        super(R.layout.item_my_post, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMyPostBinding> helper, Base item, int position) {

    }
}
