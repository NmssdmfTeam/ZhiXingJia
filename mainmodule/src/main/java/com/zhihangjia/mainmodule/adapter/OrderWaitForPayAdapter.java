package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.view.TagLayout;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.OrderDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemOrderMerchandiseBinding;
import com.zhihangjia.mainmodule.databinding.ItemOrderWaitForPayBinding;
import com.zhihangjia.mainmodule.view.OrderBtnTextView;
import com.zhixingjia.bean.mainmodule.Order;

import java.util.List;

public class OrderWaitForPayAdapter extends BaseDataBindingAdapter<Order, ItemOrderWaitForPayBinding> {
    public OrderWaitForPayAdapter(@Nullable List<Order> data) {
        super(R.layout.item_order_wait_for_pay, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemOrderWaitForPayBinding> helper, Order item, int position) {
        ItemOrderWaitForPayBinding binding = helper.getBinding();
        binding.setData(item);
        binding.llOrderMerchandise.removeAllViews();
        for (int i = 0; i < item.getItem().size(); i++) {
            ItemOrderMerchandiseBinding merchandiseBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_order_merchandise, null, false);
            merchandiseBinding.setData(item.getItem().get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dpToPx(mContext, 114));
            binding.llOrderMerchandise.addView(merchandiseBinding.getRoot(), params);
        }

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.ID, item.getOrder_id());
                intent.setClass(mContext, OrderDetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        //买家代付款：到店付、取消订单、支付
        //买家到店付后：取消订单
        //买家待发货：无按钮
        //买家待收货：确认收货
        //买家待评价：评价
        binding.tl.removeAllViews();
        switch (item.getOrder_status()) { //0=待支付 1=待发货 2=待收货 3=待评价 4=已完成  99=到店付
            case "0":{
                initWaitPay(binding.tl);
                break;
            }
            case "99":{
                initOffLinePay(binding.tl);
                break;
            }
        }
    }

    public void initOffLinePay(TagLayout layout){
        TextView payView = new OrderBtnTextView(mContext);
        payView.setText("取消订单");
        layout.addView(payView);
        payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void initWaitPay(TagLayout layout){
        TextView payView = new OrderBtnTextView(mContext);
        payView.setText("支付");
        layout.addView(payView);
        payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView offLinePayView = new OrderBtnTextView(mContext);
        offLinePayView.setText("到店付");
        layout.addView(offLinePayView);
        offLinePayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView cancelView = new OrderBtnTextView(mContext);
        cancelView.setText("取消订单");
        layout.addView(cancelView);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
