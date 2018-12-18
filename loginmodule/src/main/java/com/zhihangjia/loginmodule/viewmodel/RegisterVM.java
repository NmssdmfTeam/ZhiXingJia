package com.zhihangjia.loginmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.callback.RegisterCB;
import com.zhixingjia.bean.mainmodule.Link;
import com.zhixingjia.service.LoginService;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public class RegisterVM extends BaseVM {
    public final ObservableField<String> phoneNumber = new ObservableField<>();
    public final ObservableField<String> verificationCode = new ObservableField<>();
    public final ObservableField<String> pwd = new ObservableField<>();
    public final ObservableField<String> checkPwd = new ObservableField<>();
    public final ObservableBoolean agree = new ObservableBoolean(true);
    public final ObservableBoolean pwdShow = new ObservableBoolean(false);
    public final ObservableBoolean checkPwdShow = new ObservableBoolean(false);

    private RegisterCB cb;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public RegisterVM(RegisterCB callBack) {
        super(callBack);
        cb = callBack;
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

        if (StringUtil.isEmpty(pwd.get())) {
            ToastUtil.showMsg("请输入密码");
            return;
        }

        if (StringUtil.checkPwd(pwd.get())) {
            ToastUtil.showMsg("密码格式不正确");
            return;
        }

        if (StringUtil.isEmpty(checkPwd.get())) {
            ToastUtil.showMsg("请输入密码验证");
            return;
        }

        if (!checkPwd.get().equals(pwd.get())) {
            ToastUtil.showMsg("密码验证与密码不一样");
            return;
        }

        if (!agree.get()) {
            ToastUtil.showMsg("请选择已阅读并同意《用户服务协议》");
            return;
        }

        doRegister();
    }

    public void ivPwdShowClick(View view){
        pwdShow.set(!pwdShow.get());
        cb.setEtPwdInputType(pwdShow.get());
    }

    public void ivCheckPwdShowClick(View view){
        checkPwdShow.set(!checkPwdShow.get());
        cb.setEtCheckPwdInputType(checkPwdShow.get());
    }

    public void sendVerificationCode(View view) {
        if (StringUtil.isEmpty(phoneNumber.get())) {
            ToastUtil.showMsg("请输入手机号");
            return;
        }

        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_SEND_SMS).sendVerificationCode("1", phoneNumber.get()),
                new ServiceCallback<BaseData>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData data) {
                        cb.showToast(data.getMessage());
                    }

                    @Override
                    public void onDefeated(BaseData data) {

                    }
                });
    }

    public void tvAgreementClick(View view){
        agree.set(!agree.get());
    }


    public void doRegister(){
        Map<String, String> map = new HashMap<>();
        map.put("is_agree", agree.get() ? "1" : "0");
        map.put("mobile", phoneNumber.get());
        map.put("verif_code", verificationCode.get());
        map.put("password_one", pwd.get());
        map.put("password_two", checkPwd.get());
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_REGISTER).register(map), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.PHONE_NUM, phoneNumber.get());
                cb.setResultCode(RESULT_OK, bundle);
                cb.finishActivity();
            }

            @Override
            public void onDefeated(BaseData data) {
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

    public void tvRegisterProtocalClick(View view) {
        getSingle("1");
    }
}
