package com.zhixingjia.personmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhixingjia.bean.personmodule.Address;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.activity.AddOrEditAddressActivity;
import com.zhixingjia.personmodule.databinding.ItemAddressListBinding;

import java.util.List;

/**
 * 地址管理adapter
 * Create by chenbin on 2018/11/20
 * <p>
 * <p>
 */
public class ManageAddressListAdapter extends BaseDataBindingAdapter<Address,ItemAddressListBinding> {

    private int defaultPosition = -1;

    public ManageAddressListAdapter(@Nullable List<Address> data) {
        super(R.layout.item_address_list, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemAddressListBinding> helper, final Address item, final int position) {
        ItemAddressListBinding itemAddressListBinding = helper.getBinding();
        itemAddressListBinding.setData(item);
        if ("1".equals(item.getIs_default())) {
            itemAddressListBinding.tvIsDefault.setVisibility(View.VISIBLE);
            defaultPosition = position;
        } else {
            itemAddressListBinding.tvIsDefault.setVisibility(View.GONE);
        }
        itemAddressListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.ADDRID, item.getAddr_id());
                bundle.putInt(IntentConfig.POSITION, position);
                bundle.putSerializable(IntentConfig.ADDR, item);
                intent.putExtras(bundle);
                intent.setClass(mContext, AddOrEditAddressActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    public int getDefaultPosition() {
        return defaultPosition;
    }

    public void setDefaultPosition(int defaultPosition) {
        this.defaultPosition = defaultPosition;
    }
}
