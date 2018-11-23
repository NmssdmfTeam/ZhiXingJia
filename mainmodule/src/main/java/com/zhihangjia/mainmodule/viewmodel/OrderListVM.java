package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.OrderListCB;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class OrderListVM extends BaseVM {
    private OrderListCB cb;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public OrderListVM(OrderListCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getData() {
        Bundle bundle = cb.getIntentData();
        if (bundle != null) {
            int position = bundle.getInt(IntentConfig.POSITION, 0);
            cb.setCurrentTab(position);
        }
    }
}
