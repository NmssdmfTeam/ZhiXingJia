package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.ConfirmOrderActivity;
import com.zhihangjia.mainmodule.activity.MerchantMainActivity;
import com.zhihangjia.mainmodule.callback.MerchandiseDetailCB;

/**
* @description 商品详情viewmodel
* @author chenbin
* @date 2018/11/27 17:15
* @version v3.2.0
*/
public class MerchandiseDetailVM extends BaseVM {
    private MerchandiseDetailCB callback;
    public String commodityId;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchandiseDetailVM(MerchandiseDetailCB callBack) {
        super(callBack);
        this.callback = callBack;
        initData();
    }

    private void initData() {
        Bundle bundle = callback.getIntentData();
        if (bundle != null) {
            commodityId = bundle.getString(IntentConfig.COMMODITY_ID, "0");
        }
    }

    public void onAddCartClick(View view) {
        callback.addCart();
    }

    public void onPhoneCallClick(View view) {
        callback.callPhone();
    }

    public void onPayClick(View view) {
        callback.buyNow();
    }

    public void onShopClick(View view) {
        callback.toMerchants();
    }
}
