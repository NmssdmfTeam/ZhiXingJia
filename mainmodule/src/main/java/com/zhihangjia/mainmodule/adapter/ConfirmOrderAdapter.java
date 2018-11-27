package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.ConfirmOrderAdapterCB;
import com.zhihangjia.mainmodule.databinding.ItemConfirmOrderBinding;
import com.zhixingjia.bean.mainmodule.CommodityComfirm;

import java.util.List;

/**
 * 确认订单adapter
 */
public class ConfirmOrderAdapter extends BaseDataBindingAdapter<CommodityComfirm.InfoListBean, ItemConfirmOrderBinding> {
    private ConfirmOrderAdapterCB cb;
    public ConfirmOrderAdapter( @Nullable List<CommodityComfirm.InfoListBean> data, ConfirmOrderAdapterCB cb) {
        super(R.layout.item_confirm_order, data);
        this.cb = cb;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemConfirmOrderBinding> helper, CommodityComfirm.InfoListBean item, int position) {
        ItemConfirmOrderBinding binding = helper.getBinding();
        binding.tvDeliveryMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb.chooseDeliveryMethod();
            }
        });

        binding.ivCouponArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb.chooseCoupon();
            }
        });

        binding.tvCouponCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb.chooseCoupon();
            }
        });
    }
}
