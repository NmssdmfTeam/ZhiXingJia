package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.CouponCenterCB;
import com.zhixingjia.bean.mainmodule.CenterCoupon;
import com.zhixingjia.service.MainService;

import java.math.BigDecimal;

/**
* @description 领券中心
* @author chenbin
* @date 2019/1/22 11:17
* @version v3.2.0
*/
public class CouponCenterVM extends BaseRecyclerViewFragmentVM {
    private int pages = 1;
    private CouponCenterCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public CouponCenterVM(CouponCenterCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh) {
            pages = 1;
        }
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_ZHANSHI_COUPON).getCenterCoupon(String.valueOf(pages)),
                new ServiceCallback<BaseListData<CenterCoupon>>() {
            @Override
            public void onError(Throwable error) {
                baseCB.stopRefreshAnim();
            }

            @Override
            public void onSuccess(BaseListData<CenterCoupon> centerCouponBaseListData) {
                baseCB.refreshAdapter(isRefresh, centerCouponBaseListData.getData());
                pages++;
            }

            @Override
            public void onDefeated(BaseListData<CenterCoupon> centerCouponBaseListData) {
                baseCB.stopRefreshAnim();
            }
        });
    }

    /**
     * 领券中心列表 - 立即领取
     * @param item
     * @param position
     */
    public void recieve(CenterCoupon item, int position) {
        callback.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_ZHANSHI_COUPON_RECEIVE).recieve(item.getCoupon_id()),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                callback.showToast(base.getMessage());
                item.setReceive_sum(new BigDecimal(item.getReceive_sum()).add(new BigDecimal(1)).toString());
                callback.notifyItemChange(item, position);
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.CouponEvent.COUPON_RECIEVE, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().register(RxEvent.CouponEvent.COUPON_RECIEVE, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.CouponEvent.COUPON_RECIEVE://变更
                int position = info.getIndex();
                CenterCoupon coupon = (CenterCoupon) info.getContent();
                callback.notifyItemChange(coupon, position);
                break;
        }
    }
}
