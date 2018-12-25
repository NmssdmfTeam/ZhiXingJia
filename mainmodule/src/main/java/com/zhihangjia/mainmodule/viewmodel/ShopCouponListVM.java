package com.zhihangjia.mainmodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.SetCouponActivity;
import com.zhihangjia.mainmodule.callback.ShopCouponListCB;
import com.zhixingjia.bean.mainmodule.CouponSeller;
import com.zhixingjia.service.MainService;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public class ShopCouponListVM extends BaseVM {
    private ShopCouponListCB cb;
    public String page = "0";

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ShopCouponListVM(ShopCouponListCB callBack) {
        super(callBack);
        cb = callBack;
    }

    /**
     * 卖家优惠券列表
     */
    public void getCouponSeller(boolean isRefresh) {
        if (isRefresh)
            page = "0";
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_COUPON_SELLER).getCouponSeller(page),
                new ServiceCallback<BaseListData<CouponSeller>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<CouponSeller> couponSellerBaseListData) {
                cb.setData(couponSellerBaseListData.getData(),isRefresh);
                if (couponSellerBaseListData.getData().size() > 0) {
                    page = couponSellerBaseListData.getData().get(couponSellerBaseListData.getData().size() - 1).getCoupon_id();
                }
            }

            @Override
            public void onDefeated(BaseListData<CouponSeller> couponSellerBaseListData) {

            }
        });
    }

    /**
     * 添加优惠券
     */
    public void onAddCouponClick(View view) {
        cb.doIntent(SetCouponActivity.class, null);
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.COUPON_INSERT, this);
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.COUPON_SAVE, this);
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.COUPON_DELTE, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.COUPON_INSERT, this);
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.COUPON_SAVE, this);
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.COUPON_DELTE, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.PersonInfoEvent.COUPON_INSERT:
                getCouponSeller(true);
                break;
            case RxEvent.PersonInfoEvent.COUPON_SAVE:
                CouponSeller couponSeller = (CouponSeller) info.getContent();
                int index = info.getIndex();
                if ("morethan".equals(couponSeller.getCond())) {
                    couponSeller.setCond_name("满"+couponSeller.getMorethannumber()+"元使用");
                } else {
                    couponSeller.setCond_name("无门槛");
                }
                if ("1".equals(couponSeller.getTimetype())) {
                    couponSeller.setValidity(couponSeller.getStarttime()+"至"+couponSeller.getEndtime());
                } else {
                    couponSeller.setValidity("领取后"+couponSeller.getExpireday()+"天过期");
                }
                cb.setData(couponSeller, index);
                break;
            case RxEvent.PersonInfoEvent.COUPON_DELTE:
                cb.deletData(info.getIndex());
                break;
        }
    }
}
