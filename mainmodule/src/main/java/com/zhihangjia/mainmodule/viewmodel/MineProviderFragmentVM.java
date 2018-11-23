package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.OrderListSupplierActivity;

/**
* @description 我的--卖家
* @author chenbin
* @date 2018/11/20 16:40
* @version v3.2.0
*/
public class MineProviderFragmentVM extends BaseVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MineProviderFragmentVM(BaseCB callBack) {
        super(callBack);
    }

    public void orderListClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.POSITION, position);
        baseCallBck.doIntent(OrderListSupplierActivity.class, bundle);
    }

    public void publishGoodsClick(View view){
        baseCallBck.doIntentClassName(ActivityNameConfig.ADD_OR_EDIT_PRODUCT_ACTIVITY, null);
    }

    public void goodsManageClick(View view) {
        baseCallBck.doIntentClassName(ActivityNameConfig.GOOD_MANAGE_ACTIVITY, null);
    }
}
