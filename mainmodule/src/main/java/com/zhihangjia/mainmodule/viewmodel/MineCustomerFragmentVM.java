package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.OrderListPurchaserActivity;


/**
* @description 我的买家版
* @author chenbin
* @date 2018/11/20 13:26
* @version v3.2.0
*/
public class MineCustomerFragmentVM extends BaseVM {

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MineCustomerFragmentVM(BaseCB callBack) {
        super(callBack);
    }

    public void applySupplierActivity(View view) {
        baseCallBck.doIntentClassName(ActivityNameConfig.APPLY_SUPPLIER_ACTIVITY, null);
    }

    public void orderClick(View view, int postion){
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.POSITION, postion);
        baseCallBck.doIntent(OrderListPurchaserActivity.class, bundle);
    }

    public void myCouponClick(View view){
        baseCallBck.doIntentClassName(ActivityNameConfig.MY_COUPONS_ACTIVITY, null);
    }

    public void personInfoClick(View view) {
        baseCallBck.doIntentClassName(ActivityNameConfig.PERSON_INFO_ACTIVITY, null);
    }

    public void receiveAddress(View view){
        baseCallBck.doIntentClassName(ActivityNameConfig.MANAGE_ADDRESS_LIST_ACTIVITY, null);
    }
}
