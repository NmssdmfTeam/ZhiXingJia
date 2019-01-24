package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MyCouponCardCB;
import com.zhixingjia.bean.mainmodule.CouponCardTicketSum;
import com.zhixingjia.service.MainService;

/**
* @description 我的卡券viewmodel
* @author chenbin
* @date 2019/1/24 16:46
* @version v3.2.0
*/
public class MyCouponCardVM extends BaseVM {
    private MyCouponCardCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MyCouponCardVM(MyCouponCardCB callBack) {
        super(callBack);
        this.callback = callBack;
        getCardTicketSum();
    }

    public void getCardTicketSum() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MY_CARD_TICKET).getCouponCardTicketSum(),
                new ServiceCallback<BaseData<CouponCardTicketSum>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<CouponCardTicketSum> couponCardTicketSumBaseData) {
                callback.setCouponTicketCount(couponCardTicketSumBaseData.getData());
            }

            @Override
            public void onDefeated(BaseData<CouponCardTicketSum> couponCardTicketSumBaseData) {

            }
        });
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.CouponEvent.REFRESH_MY_COUPON_TICKET_NUM, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.CouponEvent.REFRESH_MY_COUPON_TICKET_NUM, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.CouponEvent.REFRESH_MY_COUPON_TICKET_NUM:
                getCardTicketSum();
                break;
        }
    }
}
