package com.zhihangjia.loginmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public interface RegisterCB extends BaseCB {
    void setEtPwdInputType(boolean pwdShow);
    void setEtCheckPwdInputType(boolean pwdCheckShow);
}
