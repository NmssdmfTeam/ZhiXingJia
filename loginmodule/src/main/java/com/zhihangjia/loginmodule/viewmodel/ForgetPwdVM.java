package com.zhihangjia.loginmodule.viewmodel;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.callback.ForgetPwdCB;
import com.zhixingjia.httplib.HttpUtils;
import com.zhixingjia.httplib.RxRequest;
import com.zhixingjia.httplib.ServiceCallback;
import com.zhixingjia.service.LoginService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public class ForgetPwdVM extends BaseVM {

    public final ObservableField<String> phoneNumber = new ObservableField<>();
    public final ObservableField<String> verificationCode = new ObservableField<>();
    public final ObservableField<String> pwd = new ObservableField<>();
    public final ObservableBoolean pwdShow = new ObservableBoolean(false);

    private ForgetPwdCB cb;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ForgetPwdVM(ForgetPwdCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void tvSendCodeClick(View view){
        if (StringUtil.isEmpty(phoneNumber.get())) {
            ToastUtil.showMsg("请输入手机号");
            return;
        }

        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_SEND_SMS).sendVerificationCode("2", phoneNumber.get()),
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

    public void tvSureClick(View view){
        if (StringUtil.isEmpty(phoneNumber.get())) {
            cb.showToast("请输入手机号");
            return;
        }

        if (StringUtil.isEmpty(verificationCode.get())) {
            cb.showToast("请输入验证码");
            return;
        }

        if (StringUtil.isEmpty(pwd.get())) {
            cb.showToast("请输入新密码");
            return;
        }

        if (StringUtil.checkPwd(pwd.get())) {
            cb.showToast("密码格式错误");
        }

        Map<String ,String> map = new HashMap<>();
        map.put("mobile", phoneNumber.get());
        map.put("verif_code", verificationCode.get());
        map.put("password_one", pwd.get());
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_FIND_PASSWORD).findPassword(map), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                cb.showToast(data.getMessage());
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.PHONE_NUM, phoneNumber.get());
                cb.setResultCode(Activity.RESULT_OK, bundle);
                cb.finishActivity();
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    public void ivPwdShowClick(View view){
        pwdShow.set(!pwdShow.get());
        cb.setInputType(pwdShow.get());
    }
}
