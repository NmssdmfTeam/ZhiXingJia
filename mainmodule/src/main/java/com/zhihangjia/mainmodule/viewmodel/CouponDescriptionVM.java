package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.CenterCoupon;
import com.zhixingjia.service.MainService;

import java.math.BigDecimal;

/**
* @description 票券说明
* @author chenbin
* @date 2019/1/23 13:30
* @version v3.2.0
*/
public class CouponDescriptionVM extends BaseVM {
    public final ObservableField<CenterCoupon> centerCoupon = new ObservableField<>();
    public int position;
    public boolean isNeedFeedBack = false;//是否需要刷新列表上的数据

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public CouponDescriptionVM(BaseCB callBack) {
        super(callBack);
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            centerCoupon.set((CenterCoupon) bundle.getSerializable(IntentConfig.COUPONCENTER_INFO));
            position = bundle.getInt(IntentConfig.POSITION);
        }
    }

    public void onRecieveClick(View view) {
        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_ZHANSHI_COUPON_RECEIVE).recieve(centerCoupon.get().getCoupon_id()),
                new ServiceCallback<Base>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(Base base) {
                        centerCoupon.get().setReceive_sum(new BigDecimal(centerCoupon.get().getReceive_sum()).add(new BigDecimal(1)).toString());
                        isNeedFeedBack = true;
                    }

                    @Override
                    public void onDefeated(Base base) {

                    }
                });
    }
}
