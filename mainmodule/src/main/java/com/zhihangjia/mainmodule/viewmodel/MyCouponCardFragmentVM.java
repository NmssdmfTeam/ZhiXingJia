package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.MyCouponCardFragmentCB;
import com.zhixingjia.bean.mainmodule.CouponCard;
import com.zhixingjia.service.MainService;

public class MyCouponCardFragmentVM extends BaseRecyclerViewFragmentVM {
    private int type = 0;//类型 ， 0=未使用 1=已使用 2=已失效
    private String page = "0";
    private MyCouponCardFragmentCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MyCouponCardFragmentVM(MyCouponCardFragmentCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            page = "0";
        else
            page = callback.getPage();
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MY_CARD_TICKET).getCouponCard(page, String.valueOf(type)),
                new ServiceCallback<BaseListData<CouponCard>>() {
                    @Override
                    public void onError(Throwable error) {
                        callback.stopRefreshAnim();
                    }

                    @Override
                    public void onSuccess(BaseListData<CouponCard> couponCardBaseListData) {
                        for (CouponCard couponCard : couponCardBaseListData.getData()) {
                            couponCard.setType(type);
                        }
                        callback.refreshAdapter(isRefresh, couponCardBaseListData.getData());
                    }

                    @Override
                    public void onDefeated(BaseListData<CouponCard> couponCardBaseListData) {
                        callback.stopRefreshAnim();
                    }
                });
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.CouponEvent.REFRESH_MY_COUPON, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.CouponEvent.REFRESH_MY_COUPON, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.CouponEvent.REFRESH_MY_COUPON: {
                initData(true);
                break;
            }
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
