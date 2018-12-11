package com.zhihangjia.loginmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
* @description 修改绑定手机号
* @author chenbin
* @date 2018/12/11 13:55
* @version v3.2.0
*/
public interface ChangeAccountCB extends BaseCB {
    void setInputType(boolean pwdShow);
}
