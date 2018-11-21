package com.zhixingjia.goodsmanagemodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.databinding.ItemGoodManageBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public class GoodManageAdapter extends BaseDataBindingAdapter<Base, ItemGoodManageBinding> {
    public GoodManageAdapter(@Nullable List<Base> data) {
        super(R.layout.item_good_manage, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemGoodManageBinding> helper, Base item, int position) {

    }
}
