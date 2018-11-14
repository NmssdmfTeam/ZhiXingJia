package com.zhihangjia.mainmodule.adapter.message;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemLifeServiceCategoryBinding;
import com.zhihangjia.mainmodule.databinding.ItemMessageBinding;

import java.util.List;

/**
* @description 消息中心adapter
* @author chenbin
* @date 2018/11/14 15:14
* @version v3.2.0
*/
public class MessageAdapter extends BaseDataBindingAdapter<Base, ItemMessageBinding> {
    public MessageAdapter(@Nullable List<Base> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMessageBinding> helper, Base item, int position) {

    }
}
