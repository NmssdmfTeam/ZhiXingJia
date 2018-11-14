package com.zhihangjia.loginmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.callback.RegisterCB;
import com.zhixingjia.httplib.HttpUtils;
import com.zhixingjia.httplib.RxRequest;
import com.zhixingjia.httplib.ServiceCallback;
import com.zhixingjia.service.LoginService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public class RegisterVM extends BaseVM {
    public final ObservableField<String> phoneNumber = new ObservableField<>();
    public final ObservableField<String> verificationCode = new ObservableField<>();
    public final ObservableField<String> pwd = new ObservableField<>();
    public final ObservableField<String> checkPwd = new ObservableField<>();
    public final ObservableBoolean agree = new ObservableBoolean(false);
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
            }

            @Override
            public void onDefeated(BaseData data) {
            }
        });
    }
}
