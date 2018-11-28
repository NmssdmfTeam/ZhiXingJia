package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchantMainActivity;
import com.zhihangjia.mainmodule.callback.ConfirmOrderAdapterCB;
import com.zhihangjia.mainmodule.databinding.ItemConfirmOrderBinding;
import com.zhihangjia.mainmodule.databinding.ItemConfirmOrderCommodityInfoBinding;
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
        binding.setData(item);
        binding.tvAmount.setText("共"+item.getSum_total()+"件");
        if (item.getFreight_type() == 1) {
            binding.tvDeliveryMethod.setText("上门自提");
        } else {
            binding.tvDeliveryMethod.setText("商家配送 ￥"+item.getCost_freight());
        }

        binding.tvShopDetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext , MerchantMainActivity.class);
                mContext.startActivity(intent);
            }
        });
        binding.tvDeliveryMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb.chooseDeliveryMethod(position);
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
                if (!TextUtils.isEmpty(item.getShop_coupon()) && !"0".equals(item.getShop_coupon()))
                    cb.chooseCoupon();
            }
        });
        binding.llInfoList.removeAllViews();
        for (CommodityComfirm.InfoListBean.ListInfoBean listInfoBean : item.getList_info()) {
            ItemConfirmOrderCommodityInfoBinding infoBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_confirm_order_commodity_info, null,false);
            infoBinding.setData(listInfoBean);
            binding.llInfoList.addView(infoBinding.getRoot());
        }
    }
}
