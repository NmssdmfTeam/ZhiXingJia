package com.zhihangjia.loginmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.Activity.ForgetPwdActivity;
import com.zhihangjia.loginmodule.callback.LoginCB;
import com.zhixingjia.httplib.HttpUtils;
import com.zhixingjia.httplib.RxRequest;
import com.zhixingjia.httplib.ServiceCallback;
import com.zhixingjia.service.LoginService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public class LoginVM extends BaseVM {
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
        cb.doIntent(ForgetPwdActivity.class, null);
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
    }

    public void ivWechatClick(View view) {

    }

    private void doLogin() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.doHttp(subscription, RxRequest.create(LoginService.class, 1).login(map), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onNext(BaseData baseData) {

            }
        });
    }
}
