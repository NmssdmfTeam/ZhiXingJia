package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.ApplySupplierActivity;
import com.zhihangjia.mainmodule.activity.OrderListActivity;

import static com.nmssdmf.commonlib.config.AcitivityNameConfig.MY_COUPONS_ACTIVITY;

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

    public void orderClick(View view, int postion){
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.POSITION, postion);
        baseCallBck.doIntent(OrderListActivity.class, bundle);
    }

    public void myCouponClick(View view){
        baseCallBck.doIntentClassName(MY_COUPONS_ACTIVITY, null);
    }
}
