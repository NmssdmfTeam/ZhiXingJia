package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.bean.mainmodule.CenterCoupon;
import com.zhixingjia.service.MainService;

/**
* @description 领券中心
* @author chenbin
* @date 2019/1/22 11:17
* @version v3.2.0
*/
public class CouponCenterVM extends BaseRecyclerViewFragmentVM {
    private int pages = 1;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public CouponCenterVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
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
}
