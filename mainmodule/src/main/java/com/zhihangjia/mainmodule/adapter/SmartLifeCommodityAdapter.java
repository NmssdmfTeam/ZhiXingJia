package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemSmartLifeCommodityBinding;
import com.zhixingjia.bean.mainmodule.YXTelecom;

import java.util.List;

public class SmartLifeCommodityAdapter extends BaseDataBindingAdapter<YXTelecom.CommodityBean.CommodityInfoBean, ItemSmartLifeCommodityBinding> {

    public SmartLifeCommodityAdapter(@Nullable List<YXTelecom.CommodityBean.CommodityInfoBean> data) {
        super(R.layout.item_smart_life_commodity, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemSmartLifeCommodityBinding> helper, YXTelecom.CommodityBean.CommodityInfoBean item, int position) {
        ItemSmartLifeCommodityBinding binding = helper.getBinding();
        binding.setData(item);
    }
}
