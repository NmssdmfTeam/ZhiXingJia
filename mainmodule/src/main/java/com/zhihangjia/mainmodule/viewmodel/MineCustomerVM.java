package com.zhihangjia.mainmodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.ApplySupplierActivity;

/**
* @description 我的买家版
* @author chenbin
* @date 2018/11/20 13:26
* @version v3.2.0
*/
public class MineCustomerVM extends BaseVM {

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MineCustomerVM(BaseCB callBack) {
        super(callBack);
    }

    public void applySupplierActivity(View view) {
        baseCallBck.doIntent(ApplySupplierActivity.class, null);
    }
}
