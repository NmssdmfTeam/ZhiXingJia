package com.zhihangjia.loginmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.loginmodule.LoginResult;
import com.zhixingjia.bean.loginmodule.WXUserInfo;
import com.zhixingjia.bean.mainmodule.Link;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.service.LoginService;
import com.zhixingjia.service.MainService;
import com.zhixingjia.service.PersonService;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public class ChangeAccountVM extends BaseVM {
    public final ObservableField<String> phoneNumber = new ObservableField<>();
    public final ObservableField<String> verificationCode = new ObservableField<>();
    public final ObservableField<String> newPhoneNumber = new ObservableField<>();
    public final ObservableField<String> newAccountVerificationCode = new ObservableField<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ChangeAccountVM(BaseCB callBack) {
        super(callBack);
        initData();
    }

    private void initData() {
        String userinfo = PreferenceUtil.getString(PrefrenceConfig.USER_INFO,"");
        if (!TextUtils.isEmpty(userinfo)) {
            UserInfo userInfoBean = new Gson().fromJson(userinfo, UserInfo.class);
            phoneNumber.set(userInfoBean.getMobile());
        }
    }

    public void tvChangeAccountClick(View view){

        if (StringUtil.isEmpty(phoneNumber.get())) {
            ToastUtil.showMsg("请输入手机号");
            return;
        }

        if (StringUtil.isEmpty(verificationCode.get())) {
            ToastUtil.showMsg("请输入验证码");
            return;
        }

        if (StringUtil.isEmpty(newPhoneNumber.get())) {
            ToastUtil.showMsg("请输入新手机号");
            return;
        }

        if (StringUtil.isEmpty(newAccountVerificationCode.get())) {
            ToastUtil.showMsg("请输入新手机号的密码");
            return;
        }
        baseCallBck.showLoaddingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("old_verif_code", verificationCode.get());
        map.put("mobile", newPhoneNumber.get());
        map.put("verif_code", newAccountVerificationCode.get());
        HttpUtils.doHttp(subscription,
                RxRequest.create(PersonService.class, HttpVersionConfig.API_MY_CHANGE_ACCOUNT).changeAccount(map),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                baseCallBck.showToast(base.getMessage());
                UserInfo userInfo = PreferenceUtil.getSerializables(PrefrenceConfig.USER_INFO);
                userInfo.setMobile(newPhoneNumber.get());
                PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, new Gson().toJson(userInfo));
                baseCallBck.finishActivity();
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    public void sendVerificationCode(View view) {
        sendVerificationCode(phoneNumber.get(), "2");
    }

    public void sendNewAccountVerificationCode(View view) {
        sendVerificationCode(newPhoneNumber.get(), "1");
    }

    public void sendVerificationCode(String phoneNumber,String type) {
        if (StringUtil.isEmpty(phoneNumber)) {
            ToastUtil.showMsg("请输入手机号");
            return;
        }
        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_SEND_SMS).sendVerificationCode(type, phoneNumber),
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

}
