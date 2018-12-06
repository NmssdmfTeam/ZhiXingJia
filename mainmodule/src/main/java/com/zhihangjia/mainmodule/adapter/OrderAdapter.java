package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.view.TagLayout;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.CommentActivity;
import com.zhihangjia.mainmodule.activity.ConfirmPayActivity;
import com.zhihangjia.mainmodule.activity.OrderDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemOrderBinding;
import com.zhihangjia.mainmodule.databinding.ItemOrderMerchandiseBinding;
import com.zhihangjia.mainmodule.view.OrderBtnTextView;
import com.zhixingjia.bean.mainmodule.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends BaseDataBindingAdapter<Order, ItemOrderBinding> {
    private OrderAdapterListener listener;
    private String identity;
    private AlertDialog.Builder normalDialog;

    public OrderAdapter(String identity, @Nullable List<Order> data, OrderAdapterListener listener) {
        super(R.layout.item_order, data);
        this.listener = listener;
        this.identity = identity;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemOrderBinding> helper, Order item,final int position) {
        ItemOrderBinding binding = helper.getBinding();
        binding.setData(item);
        binding.llOrderMerchandise.removeAllViews();
        for (int i = 0; i < item.getItem().size(); i++) {
            ItemOrderMerchandiseBinding merchandiseBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_order_merchandise, null, false);
            merchandiseBinding.setData(item.getItem().get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dpToPx(mContext, 114));
            binding.llOrderMerchandise.addView(merchandiseBinding.getRoot(), params);
        }

        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.ID, item.getOrder_id());
            bundle.putInt(IntentConfig.POSITION, position);
            intent.setClass(mContext, OrderDetailActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
        //买家代付款：到店付、取消订单、支付
        //买家到店付后：取消订单
        //买家待发货：无按钮
        //买家待收货：确认收货
        //买家待评价：评价
        binding.tl.removeAllViews();
        switch (item.getOrder_status()) { //0=待支付 1=待发货 2=待收货 3=待评价 4=已完成  99=到店付
            case "0": {
                initWaitPay(binding.tl, item, position);
                break;
            }
            case "1": {
                initWaitSend(binding.tl, item, position);
                break;
            }
            case "2": {
                initWaitReceive(binding.tl, item, position);
                break;
            }
            case "3": {
                initWaitComment(binding.tl, item, position);
                break;
            }
            case "99": {
                initOffLinePay(binding.tl, item, position);
                break;
            }
        }

    }

    /**
     * 代发货
     *
     * @param layout
     * @param item
     * @param index
     */
    public void initWaitSend(TagLayout layout, Order item, int index) {
        if (identity.equals("buyer")) {

        } else {
            TextView payView = new OrderBtnTextView(mContext);
            payView.setText("发货");
            layout.addView(payView);
            payView.setOnClickListener(v -> showDialog(index, 5, "确认发货么"));
        }
    }

    /**
     * 到店付
     *
     * @param layout
     * @param item
     * @param index
     */
    public void initOffLinePay(TagLayout layout, Order item, int index) {
        if (identity.equals("buyer")) {
            TextView payView = new OrderBtnTextView(mContext);
            payView.setText("取消订单");
            layout.addView(payView);
            payView.setOnClickListener(v -> showDialog(index, 2, "确认取消订单么"));
        } else {
            TextView payView = new OrderBtnTextView(mContext);
            payView.setText("确认收款");
            layout.addView(payView);
            payView.setOnClickListener(v -> showDialog(index, 4, "确认收款么"));
        }
    }

    /**
     * 待评价
     *
     * @param layout
     */
    public void initWaitComment(TagLayout layout,Order item, final int position) {
        if (identity.equals("buyer")) {
            TextView payView = new OrderBtnTextView(mContext);
            payView.setText("评价");
            layout.addView(payView);
            payView.setOnClickListener(v -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.ORDER_ID, item.getOrder_id());
                intent.putExtras(bundle);
                intent.setClass(mContext ,CommentActivity.class);
                mContext.startActivity(intent);
            });
        } else {

        }
    }

    /**
     * 待收货
     *
     * @param layout
     */
    public void initWaitReceive(TagLayout layout, Order item, int index) {
        if (identity.equals("buyer")) {
            TextView payView = new OrderBtnTextView(mContext);
            payView.setText("确认收货");
            layout.addView(payView);
            payView.setOnClickListener(v -> showDialog(index, 3, "确认收货么"));
        } else {

        }
    }

    /**
     * 待支付
     *
     * @param layout
     * @param item
     * @param index
     */
    public void initWaitPay(TagLayout layout, Order item, int index) {
        if (identity.equals("buyer")) {
            TextView payView = new OrderBtnTextView(mContext);
            payView.setText("支付");
            layout.addView(payView);
            payView.setOnClickListener(v -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                List<String> ids = new ArrayList<>();
                ids.add(getData().get(index).getOrder_id());
                bundle.putSerializable(IntentConfig.PAY_IDS, (Serializable) ids);
                bundle.putInt(IntentConfig.POSITION, index);
                bundle.putString(IntentConfig.IDENTITY, identity);
                intent.putExtras(bundle);
                intent.setClass(mContext, ConfirmPayActivity.class);
                mContext.startActivity(intent);
            });

            TextView offLinePayView = new OrderBtnTextView(mContext);
            offLinePayView.setText("到店付");
            layout.addView(offLinePayView);
            offLinePayView.setOnClickListener(v -> showDialog(index, 1, "确认到店付么"));

            TextView cancelView = new OrderBtnTextView(mContext);
            cancelView.setText("取消订单");
            layout.addView(cancelView);
            cancelView.setOnClickListener(v -> showDialog(index, 2, "确认取消订单么"));
        } else {

        }
    }


    public void showDialog(int index, int i, String message) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        if (normalDialog == null) {
            normalDialog = new AlertDialog.Builder(mContext);
            normalDialog.setTitle("提示");
            normalDialog.setNegativeButton("取消",
                    (dialog, which) -> dialog.dismiss());
        }
        normalDialog.setPositiveButton("确定",
                (dialog, which) -> {
                    JLog.d(TAG, "index = " + index + ": i = " + i);
                    switch (i) {
                        case 1: {//到店付
                            listener.offlinePayOrder(index);
                            break;
                        }
                        case 2: {//取消订单
                            listener.cancelOrder(index);
                            break;
                        }
                        case 3: {
                            listener.checkReceiver(index);
                            break;
                        }
                        case 4: {
                            listener.checkOfflinePayOrder(index);
                            break;
                        }
                        case 5: {
                            listener.sendOrder(index);
                            break;
                        }
                    }
                });
        normalDialog.setMessage(message);

        // 显示
        normalDialog.show();
    }

    public interface OrderAdapterListener {
        void cancelOrder(int index);

        void offlinePayOrder(int index);

        void checkOfflinePayOrder(int index);

        void sendOrder(int index);

        void checkReceiver(int index);
    }
}
