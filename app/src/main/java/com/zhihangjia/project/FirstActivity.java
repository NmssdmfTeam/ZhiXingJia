package com.zhihangjia.project;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.config.AcitivityNameConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

public class FirstActivity extends BaseActivity {
    private final String TAG = FirstActivity.class.getSimpleName();

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_first;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        doIntentClassName(AcitivityNameConfig.MAIN_ACTIVITY, null);
    }
}
