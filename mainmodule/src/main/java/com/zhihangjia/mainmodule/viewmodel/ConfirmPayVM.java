package com.zhihangjia.mainmodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.PaySuccessActivity;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class ConfirmPayVM extends BaseVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ConfirmPayVM(BaseCB callBack) {
        super(callBack);
    }

    public void confirmPay(View view){
        baseCallBck.doIntent(PaySuccessActivity.class, null);
    }
}
