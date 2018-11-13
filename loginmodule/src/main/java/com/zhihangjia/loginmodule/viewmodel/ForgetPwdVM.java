package com.zhihangjia.loginmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.callback.ForgetPwdCB;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public class ForgetPwdVM extends BaseVM {

    public final ObservableField<String> phoneNumber = new ObservableField<>();
    public final ObservableField<String> verificationCode = new ObservableField<>();
    public final ObservableField<String> pwd = new ObservableField<>();
    public final ObservableBoolean pwdShow = new ObservableBoolean(false);
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ForgetPwdVM(ForgetPwdCB callBack) {
        super(callBack);
    }

    public void tvSendCodeClick(View view){

    }

    public void tvSureClick(View view){

    }

    public void ivPwdShowClick(View view){

    }
}
