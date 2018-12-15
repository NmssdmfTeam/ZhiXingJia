package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.bean.PriceSet;
import com.zhixingjia.goodsmanagemodule.callback.PriceSettingCB;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 设置价格库存
 * @date 2018/11/21 14:23
 */
public class PriceSettingVM extends BaseVM {
    public List<String> unit = new ArrayList<>();
    public ObservableField<PriceSet> priceSet = new ObservableField<>();
    private Bundle bundle;
    private PriceSettingCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public PriceSettingVM(PriceSettingCB callBack) {
        super(callBack);
        initData();
        this.callback = callBack;
    }

    private void initData() {
        bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            unit = (List<String>) bundle.getSerializable(IntentConfig.UNIT);
            PriceSet priceSetBean = (PriceSet) bundle.getSerializable(IntentConfig.STOCK_PRICE);
            if (priceSetBean == null)
                priceSetBean = new PriceSet();
            priceSet.set(priceSetBean);
        }
    }

    public void onSelectUnitClick(View view) {
        callback.showUnitWheelPickWindow();
    }

    public void onConfirmClick(View view) {
        if (TextUtils.isEmpty(priceSet.get().getPrice())) {
            baseCallBck.showToast("请输入价格");
            return;
        }
        if (TextUtils.isEmpty(priceSet.get().getStock())) {
            baseCallBck.showToast("请输入库存");
        }
        if (TextUtils.isEmpty(priceSet.get().getUnit())) {
            baseCallBck.showToast("请选择单位");
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.STOCK_PRICE, priceSet.get());
        callback.setResultCode(Activity.RESULT_OK, bundle);
        callback.finishActivity();
    }
}
