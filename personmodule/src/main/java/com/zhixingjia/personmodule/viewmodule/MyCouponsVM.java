package com.zhixingjia.personmodule.viewmodule;


import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.zhixingjia.bean.personmodule.Coupon;
import com.zhixingjia.personmodule.callback.MyCouponsCB;
import com.zhixingjia.service.PersonService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class MyCouponsVM extends BaseTitleRecyclerViewVM {
    private int page;    //分页
    private MyCouponsCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MyCouponsVM(MyCouponsCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    @Override
    public void initData(final boolean isRefresh) {
        if (isRefresh)
            page = 0;
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");
        map.put("page", String.valueOf(page));
        HttpUtils.doHttp(subscription, RxRequest.create(PersonService.class, HttpVersionConfig.API_COUPON_INFO).getMyCoupon(map), new ServiceCallback<BaseListData<Coupon>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Coupon> base) {
                baseTitleRecyclerViewCB.refreshAdapter(isRefresh, base.getData());
                page++;
            }

            @Override
            public void onDefeated(BaseListData<Coupon> base) {

            }
        });
    }
}
