package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.AlipayUtil;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.PaySuccessActivity;
import com.zhihangjia.mainmodule.adapter.ChooseCouponAdater;
import com.zhihangjia.mainmodule.callback.ConfirmPayCB;
import com.zhixingjia.bean.mainmodule.PayInfo;
import com.nmssdmf.commonlib.bean.Payment;
import com.zhixingjia.bean.personmodule.Coupon;
import com.zhixingjia.service.MainService;
import com.zhixingjia.service.PersonService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class ConfirmPayVM extends BaseVM implements ChooseCouponAdater.ChooseCouponAdaterListener {
    public final ObservableInt payMethod = new ObservableInt();         //0:支付宝支付 1:微信支付 2:翼支付

    private List<String> payIds = new ArrayList<>();
    public final ObservableField<PayInfo> payInfo = new ObservableField<>();
    public final ObservableField<String> payPrice = new ObservableField<>();
    public final ObservableField<String> couponString = new ObservableField<>();
    private ConfirmPayCB callback;
    public List<Coupon> coupons = new ArrayList<>();
    private String pages = "0";
    private Coupon coupon = new Coupon();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ConfirmPayVM(ConfirmPayCB callBack) {
        super(callBack);
        this.callback = callBack;
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            payIds = (List<String>) bundle.getSerializable(IntentConfig.PAY_IDS);
        }
    }

    public void confirmPay(View view) {
        payment();
    }

    public void payInfo() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_PAY).apiPay(new Gson().toJson(payIds)),
                new ServiceCallback<BaseData<PayInfo>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData<PayInfo> payInfoBaseData) {
                        payInfo.set(payInfoBaseData.getData());
                        payPrice.set(payInfo.get().getOrder_amount());
                        couponString.set(payInfo.get().getCoupon_sum() + baseCallBck.getStringFromId(R.string.enable_coupon_num));
                        callback.setListener();
                    }

                    @Override
                    public void onDefeated(BaseData<PayInfo> payInfoBaseData) {

                    }
                });
    }

    /**
     * 获取平台优惠券
     */
    public void getCoupons(boolean isRefresh, String money) {
        if (TextUtils.isEmpty(payInfo.get().getCoupon_sum()) || "0".equals(payInfo.get().getCoupon_sum())) {
            callback.showToast("没有优惠券可以选择");
            return;
        }
        if (isRefresh) {
            coupons.clear();
            pages = "0";
        }
        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        map.put("amount", money);
        map.put("page", pages);
        HttpUtils.doHttp(subscription,
                RxRequest.create(PersonService.class, HttpVersionConfig.API_COUPON_INFO).getMyCoupon(map),
                new ServiceCallback<BaseListData<Coupon>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseListData<Coupon> data) {
                        if (data.getData() != null && data.getData().size() > 0) {
                            coupons.addAll(data.getData());
                            pages = data.getData().get(data.getData().size() - 1).getCode_id();
                        }
                        callback.showCouponWindow(isRefresh);
                    }

                    @Override
                    public void onDefeated(BaseListData<Coupon> data) {

                    }
                });
    }

    public void payment() {
        Map<String, String> map = new HashMap<>();
        map.put("order_id", new Gson().toJson(payIds));
        String pay_method;
        if (payMethod.get() == 0) {
            pay_method = "alipay";
        } else if (payMethod.get() == 1) {
            pay_method = "weixin";
        } else {
            pay_method = "yipay";
        }
        map.put("pay_method", pay_method);
        if (!StringUtil.isEmpty(coupon.getCode_id()))
            map.put("coupon_code", coupon.getCode_id());
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_PAY_PAYMENT).payment(map),
                new ServiceCallback<BaseData<Payment>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData<Payment> baseData) {
                        if (payMethod.get() == 0) {
                            //调用alipay SDK
                            if (!StringUtil.isEmpty(baseData.getData().getAlipay().getPayment()))
                                callback.aliPay(baseData.getData().getAlipay().getPayment());
                        } else if (payMethod.get() == 1) {
                            if (baseData.getData().getWeixin() != null)
                                callback.wechatPay(baseData.getData().getWeixin());
                        }
                    }

                    @Override
                    public void onDefeated(BaseData<Payment> baseData) {

                    }
                });
    }


    public void onPlatformCouponClick(View view) {
        getCoupons(true, payInfo.get().getOrder_amount());
    }

    public void onAlipayClick(View view) {
        if (view.getId() == R.id.rlAlipay) {
            payMethod.set(0);
        } else if (view.getId() == R.id.rlWeChatAlipay) {
            payMethod.set(1);
        } else if (view.getId() == R.id.rlEpay) {
            payMethod.set(2);
        }
    }

    @Override
    public void useCoupon(Coupon item) {
        coupon.setCode_id(item.getCode_id());
        payPrice.set(new BigDecimal(payInfo.get().getOrder_amount()).subtract(new BigDecimal(item.getDecrease())).toString());
        couponString.set("-￥" + item.getDecrease());
        callback.closeCouponWindow();
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.OrderEvent.ALIPAY_FINISH, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.WECHATPAY_FINISH, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ALIPAY_FINISH, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.WECHATPAY_FINISH, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.OrderEvent.ALIPAY_FINISH:
                AlipayUtil.PayResult payResult = new AlipayUtil.PayResult((Map<String, String>) info.getContent());
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    RxBus.getInstance().send(RxEvent.OrderEvent.PAY_FINISH, null);
                    baseCallBck.finishActivity();
                    baseCallBck.doIntent(PaySuccessActivity.class, null);
                } else {
                    callback.showToast("支付失败: " + payResult);
                }
                break;
            case RxEvent.OrderEvent.WECHATPAY_FINISH:
                callback.finishActivity();
                baseCallBck.doIntent(PaySuccessActivity.class, null);
                RxBus.getInstance().send(RxEvent.OrderEvent.PAY_FINISH, null);
                break;
        }
    }
}
