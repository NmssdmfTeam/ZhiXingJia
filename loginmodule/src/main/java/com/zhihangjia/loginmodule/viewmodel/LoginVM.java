package com.zhihangjia.loginmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.callback.LoginCB;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public class LoginVM extends BaseVM {
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> pwd = new ObservableField<>();
    public final ObservableBoolean pwdShow = new ObservableBoolean(false);

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public LoginVM(LoginCB callBack) {
        super(callBack);
    }

    public void tvForgetPwdClick(View view) {

    }

    public void tvLoginClick(View view) {

    }

    public void ivHidePwdClick(View view) {

    }

    public void ivWechatClick(View view) {

    }
}
