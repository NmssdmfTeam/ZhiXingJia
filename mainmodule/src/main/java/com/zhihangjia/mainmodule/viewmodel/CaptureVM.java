package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.CaptureCB;
import com.zhixingjia.service.MainService;

import java.util.Map;

public class CaptureVM extends BaseVM {

    private CaptureCB cb;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public CaptureVM(CaptureCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getCouponWriteOff( Map<String, String> map){
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_MY_COUPON_WRITE_OFF).getCouponWriteOff(map),
                new ServiceCallback<BaseData>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData data) {
                        cb.showToast(data.getMessage());
                        cb.checkResult();
                    }

                    @Override
                    public void onDefeated(BaseData data) {
                        cb.scanError();
                    }
                });
    }
}
