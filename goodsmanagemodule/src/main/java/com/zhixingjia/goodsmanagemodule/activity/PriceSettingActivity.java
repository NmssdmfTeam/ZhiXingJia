package com.zhixingjia.goodsmanagemodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.viewmodel.PriceSettingVM;

/**
* @description 设置价格库存
* @author chenbin
* @date 2018/11/21 14:21
* @version v3.2.0
*/
public class PriceSettingActivity extends BaseTitleActivity {
    private final String TAG = PriceSettingActivity.class.getSimpleName();
    private PriceSettingVM vm;

    @Override
    public String setTitle() {
        return "设置价格库存";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_price_setting;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new PriceSettingVM(this);
        return vm;
    }
}
