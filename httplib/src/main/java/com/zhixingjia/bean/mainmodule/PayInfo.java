package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
 * 确认支付页面
 * Create by chenbin on 2018/12/3
 * <p>
 * <p>
 */
public class PayInfo extends BaseObservable {
    private String order_amount;
    private String coupon_sum;

    @Bindable
    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
        notifyPropertyChanged(BR.order_amount);
    }

    @Bindable
    public String getCoupon_sum() {
        return coupon_sum;
    }

    public void setCoupon_sum(String coupon_sum) {
        this.coupon_sum = coupon_sum;
        notifyPropertyChanged(BR.coupon_sum);
    }
}
