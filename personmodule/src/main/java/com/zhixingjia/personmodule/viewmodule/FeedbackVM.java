package com.zhixingjia.personmodule.viewmodule;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.personmodule.callback.FeedBackCB;
import com.zhixingjia.service.PersonService;


/**
 * Create by chenbin on 2017/12/16
 * <p>
 * 意见反馈viewmodel
 */
public class FeedbackVM extends BaseVM {
    public ObservableField<String> content = new ObservableField<>();
    public ObservableBoolean isCommitBtnEnable = new ObservableBoolean();
    public ObservableField<String> count = new ObservableField<>("0/200");
    private FeedBackCB callback;

    public FeedbackVM(FeedBackCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    /**
     * 发送意见反馈请求
     */
    public void feedback() {
        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(PersonService.class, HttpVersionConfig.API_MY_FEEDBACK).feedBack(content.get()),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                callback.showToast("谢谢您的宝贵意见");
                callback.finishActivity();
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s)) {
            isCommitBtnEnable.set(true);
        } else {
            isCommitBtnEnable.set(false);
        }
        String str = s + "";
        if (str.length() > 200) {
            str = str.substring(0, 200);
            content.set(str);
            callback.showToast("输入的文字超过上限");
        }
        count.set(str.length() + "/200");
    }

    public void onCommitClick(View view) {
        feedback();
    }
}
