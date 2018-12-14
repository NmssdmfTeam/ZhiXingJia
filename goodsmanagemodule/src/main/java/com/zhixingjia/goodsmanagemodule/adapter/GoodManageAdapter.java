package com.zhixingjia.goodsmanagemodule.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.databinding.ItemGoodManageBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public class GoodManageAdapter extends BaseDataBindingAdapter<Commodity, ItemGoodManageBinding> {
    public GoodManageAdapter(@Nullable List<Commodity> data) {
        super(R.layout.item_good_manage, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemGoodManageBinding> helper, final Commodity item, int position) {
        ItemGoodManageBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setIs_opened(!item.isIs_opened());
            }
        });
    }
}
