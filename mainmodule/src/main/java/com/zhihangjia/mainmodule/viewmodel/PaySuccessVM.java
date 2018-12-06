package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.MainActivity;
import com.zhihangjia.mainmodule.activity.OrderDetailActivity;
import com.zhihangjia.mainmodule.activity.OrderListPurchaserActivity;
import com.zhihangjia.mainmodule.activity.OrderListSupplierActivity;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class PaySuccessVM extends BaseVM {

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public PaySuccessVM(BaseCB callBack) {
        super(callBack);
    }

    public void tvViewOrderClick(View view){
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.POSITION, 2);
        baseCallBck.doIntent(OrderListPurchaserActivity.class, bundle);
    }

    public void tvContinueStrollClick(View view){
        baseCallBck.doIntent(MainActivity.class, null);
    }
}
