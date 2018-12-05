package com.zhihangjia.loginmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.Activity.ForgetPwdActivity;
import com.zhihangjia.loginmodule.callback.LoginCB;
import com.zhixingjia.bean.loginmodule.LoginResult;
import com.zhixingjia.bean.mainmodule.Link;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.service.LoginService;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public class LoginVM extends BaseVM {
    public static final int REGISTER_REQUEST_CODE = 1;
    public static final int FORGET_PWD_REQUEST_CODE = 2;
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> pwd = new ObservableField<>();
    public final ObservableBoolean pwdShow = new ObservableBoolean(false);

    private LoginCB cb;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public LoginVM(LoginCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void tvForgetPwdClick(View view) {
        cb.doIntentForResult(ForgetPwdActivity.class, null, FORGET_PWD_REQUEST_CODE);
    }

    public void tvLoginClick(View view) {
        if (StringUtil.isEmpty(phoneNum.get())) {
            ToastUtil.showMsg("请输入手机号");
            return;
        }

        if (StringUtil.isEmpty(pwd.get())) {
            ToastUtil.showMsg("请输入密码");
            return;
        }

        if (StringUtil.checkPwd(pwd.get())) {
            ToastUtil.showMsg("密码格式不正确");
            return;
        }

        doLogin();
    }

    public void ivHidePwdClick(View view) {
        pwdShow.set(!pwdShow.get());

        cb.setInputType(pwdShow.get());
    }

    public void ivWechatClick(View view) {

    }

    private void doLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("login_account", phoneNum.get());
        map.put("password", pwd.get());
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_LOGIN).login(map), new ServiceCallback<BaseData<LoginResult>>() {
            @Override
            public void onError(Throwable error) {
            }

            @Override
            public void onSuccess(BaseData<LoginResult> data) {
                ToastUtil.showMsg(data.getMessage());
                PreferenceUtil.setStringValue(PrefrenceConfig.TOKEN, data.getData().getToken());
                getUserInfo();
            }

            @Override
            public void onDefeated(BaseData<LoginResult> data) {
            }
        });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MY).getUserInfo("buyer"),
                new ServiceCallback<BaseData<UserInfo>>() {
                    @Override
                    public void onError(Throwable error) {
                    }

                    @Override
                    public void onSuccess(BaseData<UserInfo> userInfoBaseData) {
                        if (StringConfig.OK.equals(userInfoBaseData.getStatus_code())) {
                            PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, new Gson().toJson(userInfoBaseData.getData()));
                            RxBus.getInstance().send(RxEvent.LoginEvent.LOGIN_SUCCESS, null);
                            cb.doIntentClassName(ActivityNameConfig.MAIN_ACTIVITY, null);
                            cb.finishActivity();
                        }
                    }

                    @Override
                    public void onDefeated(BaseData<UserInfo> userInfoBaseData) {
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
                        cb.doIntent(WebViewActivity.class, bundle);
                    }

                    @Override
                    public void onDefeated(BaseData<Link> stringBaseData) {

                    }
                });
    }

    public void onServiceProtocalClick(View view) {
        getSingle("1");
    }

    public void onPrivacyPolicyClick(View view) {
        getSingle("3");
    }
}
