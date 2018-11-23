package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemXyTelecomBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class XYTelecomAdapter extends BaseDataBindingAdapter<Base, ItemXyTelecomBinding> {

    public XYTelecomAdapter( @Nullable List<Base> data) {
        super(R.layout.item_xy_telecom, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemXyTelecomBinding> helper, Base item, int position) {

    }
}
