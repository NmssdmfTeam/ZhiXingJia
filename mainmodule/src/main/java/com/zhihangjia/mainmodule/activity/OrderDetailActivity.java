package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.view.TagLayout;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.OrderDetailCB;
import com.zhihangjia.mainmodule.databinding.ActivityOrderDetailBinding;
import com.zhihangjia.mainmodule.databinding.ItemOrderDetailMerchandiseBinding;
import com.zhihangjia.mainmodule.view.OrderBtnTextView;
import com.zhihangjia.mainmodule.viewmodel.OrderDetailVM;
import com.zhixingjia.bean.mainmodule.OrderDetail;

public class OrderDetailActivity extends BaseTitleActivity implements OrderDetailCB{
    private final String TAG = OrderDetailActivity.class.getSimpleName();
    private ActivityOrderDetailBinding binding;
    private OrderDetailVM vm;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new OrderDetailVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "我的订单";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityOrderDetailBinding) baseViewBinding;
        binding.setVm(vm);
        vm.getIntentData();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void addOrderStatusBtn(String status) {
        //买家代付款：到店付、取消订单、支付
        //买家到店付后：取消订单
        //买家待发货：无按钮
        //买家待收货：确认收货
        //买家待评价：评价
        binding.tl.removeAllViews();
        switch (status) { //0=待支付 1=待发货 2=待收货 3=待评价 4=已完成  99=到店付
            case "0":{
                initWaitPay(binding.tl);
                break;
            }
            case "2":{
                initWaitSend(binding.tl);
                break;
            }
            case "3":{
                initWaitComment(binding.tl);
                break;
            }
            case "99":{
                initOffLinePay(binding.tl);
                break;
            }
        }
    }

    @Override
    public void addGoods() {
        for (OrderDetail.ItemBean bean : vm.detail.get().getItem()) {
            ItemOrderDetailMerchandiseBinding itemOrderDetailMerchandiseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_order_detail_merchandise, null, false);
            itemOrderDetailMerchandiseBinding.setData(bean);

            binding.llOrderMerchandise.addView(itemOrderDetailMerchandiseBinding.getRoot(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dpToPx(this, 114)));
        }
    }

    public void initOffLinePay(TagLayout layout){
        TextView payView = new OrderBtnTextView(this);
        payView.setText("取消订单");
        layout.addView(payView);
        payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.cancelOrder();
            }
        });
    }

    public void initWaitComment(TagLayout layout){
        TextView payView = new OrderBtnTextView(this);
        payView.setText("评价");
        layout.addView(payView);
        payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void initWaitSend(TagLayout layout){
        TextView payView = new OrderBtnTextView(this);
        payView.setText("确认收货");
        layout.addView(payView);
        payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.checkReceiver();
            }
        });
    }

    public void initWaitPay(TagLayout layout){
        TextView payView = new OrderBtnTextView(this);
        payView.setText("支付");
        layout.addView(payView);
        payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView offLinePayView = new OrderBtnTextView(this);
        offLinePayView.setText("到店付");
        layout.addView(offLinePayView);
        offLinePayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.offlinePayOrder();
            }
        });

        TextView cancelView = new OrderBtnTextView(this);
        cancelView.setText("取消订单");
        layout.addView(cancelView);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.cancelOrder();
            }
        });
    }
}
