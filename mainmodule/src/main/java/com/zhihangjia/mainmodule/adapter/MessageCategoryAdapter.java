package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemMessageCategoryInfoBinding;

import java.util.List;

public class MessageCategoryAdapter extends BaseDataBindingAdapter<Base, ItemMessageCategoryInfoBinding> {
    public MessageCategoryAdapter(@Nullable List<Base> data) {
        super(R.layout.item_message_category_info, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMessageCategoryInfoBinding> helper, Base item, int position) {

    }
}
