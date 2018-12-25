package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.bean.Payment;
import com.nmssdmf.commonlib.callback.BaseCB;

/**
 * 选择支付callback
 * Create by chenbin on 2018/12/3
 * <p>
 * <p>
 */
public interface ConfirmPayCB extends BaseCB {

    void setListener();
    void showCouponWindow(boolean isRefresh);
    void aliPay(String payment);
    void wechatPay(Payment.Weixin weixin);
    void closeCouponWindow();
}
