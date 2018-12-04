package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.PaySuccessActivity;
import com.zhihangjia.mainmodule.callback.ConfirmPayCB;
import com.zhixingjia.bean.mainmodule.PayInfo;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class ConfirmPayVM extends BaseVM {
    public final ObservableInt payMethod = new ObservableInt();         //0:支付宝支付 1:微信支付 2:翼支付

    private List<String> payIds = new ArrayList<>();
    public final ObservableField<PayInfo> payInfo = new ObservableField<>();
    private ConfirmPayCB callback;

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
        baseCallBck.doIntent(PaySuccessActivity.class, null);
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
                        callback.setListener();
                    }

                    @Override
                    public void onDefeated(BaseData<PayInfo> payInfoBaseData) {

                    }
                });
    }
}
