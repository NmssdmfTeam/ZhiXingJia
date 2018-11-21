package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;

public class OrderDetailActivity extends BaseTitleActivity {
    private final String TAG = OrderDetailActivity.class.getSimpleName();

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    public String setTitle() {
        return "我的订单";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_order_detail;
    }
}