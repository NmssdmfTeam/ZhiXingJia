package com.zhihangjia.loginmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.callback.RegisterCB;
import com.zhixingjia.bean.loginmodule.LoginResult;
import com.zhixingjia.bean.loginmodule.WXUserInfo;
import com.zhixingjia.bean.mainmodule.Link;
import com.zhixingjia.service.LoginService;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public class BindRegisterVM extends BaseVM {
    public final ObservableField<String> phoneNumber = new ObservableField<>();
    public final ObservableField<String> verificationCode = new ObservableField<>();
    public final ObservableBoolean agree = new ObservableBoolean(false);
    private String openid;
    private WXUserInfo userInfo;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public BindRegisterVM(BaseCB callBack) {
        super(callBack);
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if(bundle != null) {
            openid = bundle.getString(IntentConfig.OPEN_ID);
            userInfo = (WXUserInfo) bundle.getSerializable(IntentConfig.WX_USERINFO);
        }
    }

    public void tvRegisterClick(View view){
        if (StringUtil.isEmpty(phoneNumber.get())) {
            ToastUtil.showMsg("请输入手机号");
            return;
        }

        if (StringUtil.isEmpty(verificationCode.get())) {
            ToastUtil.showMsg("请输入验证码");
            return;
        }

        if (!agree.get()) {
            ToastUtil.showMsg("请选择已阅读并同意《用户服务协议》");
            return;
        }

        wexinBind(openid);
    }

    public void sendVerificationCode(View view) {
        if (StringUtil.isEmpty(phoneNumber.get())) {
            ToastUtil.showMsg("请输入手机号");
            return;
        }

        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_SEND_SMS).sendVerificationCode("3", phoneNumber.get()),
                new ServiceCallback<BaseData>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData data) {
                        baseCallBck.showToast(data.getMessage());
                    }

                    @Override
                    public void onDefeated(BaseData data) {

                    }
                });
    }

    public void tvAgreementClick(View view){
        agree.set(!agree.get());
    }


    public void wexinBind(String openid){
        Map<String, String> map = new HashMap<>();
        map.put("is_agree", agree.get() ? "1" : "0");
        map.put("mobile", phoneNumber.get());
        map.put("verif_code", verificationCode.get());
        map.put("openid", openid);
        map.put("nickname", userInfo.getNickname());
        map.put("sex", userInfo.getSex());
        map.put("headimgurl", userInfo.getHeadimgurl());
        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_REGISTER).wexinBind(map),
                new ServiceCallback<BaseData<LoginResult>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<LoginResult> data) {
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.PHONE_NUM, phoneNumber.get());
                EventInfo eventInfo = new EventInfo();
                eventInfo.setContent(data.getData().getToken());
                RxBus.getInstance().send(RxEvent.LoginEvent.BIND_WEXIN, eventInfo);
                baseCallBck.setResultCode(RESULT_OK, bundle);
                baseCallBck.finishActivity();
            }

            @Override
            public void onDefeated(BaseData<LoginResult> data) {
            }
        });
    }

    private void getSingle(final String types) {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_SINGLE).getSingle(types),
                new ServiceCallback<BaseData<Link>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData<Link> stringBaseData) {
                        Bundle bundle = new Bundle();
                        bundle.putString(IntentConfig.LINK, stringBaseData.getData().getLink_url());
                        baseCallBck.doIntent(WebViewActivity.class, bundle);
                    }

                    @Override
                    public void onDefeated(BaseData<Link> stringBaseData) {

                    }
                });
    }

    public void tvRegisterProtocalClick(View view) {
        getSingle("1");
    }
}
