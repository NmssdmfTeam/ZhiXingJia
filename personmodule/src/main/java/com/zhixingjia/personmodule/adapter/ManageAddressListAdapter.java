package com.zhixingjia.personmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.databinding.ItemAddressListBinding;

import java.util.List;

/**
 * 地址管理adapter
 * Create by chenbin on 2018/11/20
 * <p>
 * <p>
 */
public class ManageAddressListAdapter extends BaseDataBindingAdapter<Base,ItemAddressListBinding> {

    public ManageAddressListAdapter(@Nullable List<Base> data) {
        super(R.layout.item_address_list, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemAddressListBinding> helper, Base item, int position) {

    }
}
