package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchantMainActivity;
import com.zhihangjia.mainmodule.callback.ConfirmOrderAdapterCB;
import com.zhihangjia.mainmodule.databinding.ItemConfirmOrderBinding;
import com.zhihangjia.mainmodule.databinding.ItemConfirmOrderCommodityInfoBinding;
import com.zhixingjia.bean.mainmodule.CommodityComfirm;

import java.math.BigDecimal;
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
        if (item.getFreight_type() == 1) {
            binding.tvDeliveryMethod.setText("上门自提");
        } else {
            binding.tvDeliveryMethod.setText("商家配送 ￥"+item.getCost_freight());
        }

        binding.tvShopDetail.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(mContext , MerchantMainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.ID, item.getProvider_id());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
        binding.tvDeliveryMethod.setOnClickListener(v -> cb.chooseDeliveryMethod(position));

        binding.ivCouponArrow.setOnClickListener(v -> {
            if (!StringUtil.isEmpty(item.getShop_coupon()) && !"0".equals(item.getShop_coupon()))
                cb.chooseCoupon(position,item.getProvider_id(), item.getProduct_amount());
        });

        binding.tvCouponCount.setOnClickListener(v -> {
            if (!StringUtil.isEmpty(item.getShop_coupon()) && !"0".equals(item.getShop_coupon()))
                cb.chooseCoupon(position,item.getProvider_id(), item.getProduct_amount());
        });
        if (StringUtil.isEmpty(item.getCoupon_code())) {
            binding.tvPrice.setText(item.getProduct_amount());
        } else {
            binding.tvPrice.setText(new BigDecimal(item.getProduct_amount()).subtract(new BigDecimal(item.getCoupon_price())).toString());
        }

        binding.llInfoList.removeAllViews();
        for (CommodityComfirm.InfoListBean.ListInfoBean listInfoBean : item.getList_info()) {
            ItemConfirmOrderCommodityInfoBinding infoBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_confirm_order_commodity_info, null,false);
            infoBinding.setData(listInfoBean);
            binding.llInfoList.addView(infoBinding.getRoot());
        }
    }
}
