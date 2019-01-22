package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.service.MainService;

/**
* @description 举报viewmodel
* @author chenbin
* @date 2019/1/21 14:35
* @version v3.2.0
*/
public class ReportVM extends BaseVM {
    public final ObservableField<String> content = new ObservableField<>();
    private String bbsId;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ReportVM(BaseCB callBack) {
        super(callBack);
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            bbsId = bundle.getString(IntentConfig.BBS_ID);
        }
    }

    /**
     * 举报
     */
    public void report() {
        if (TextUtils.isEmpty(content.get())) {
            baseCallBck.showToast("请填写举报内容");
            return;
        }
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_REPORT).bbsReport(bbsId, content.get()),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                baseCallBck.finishActivity();
                baseCallBck.showToast("举报成功");
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }
}
