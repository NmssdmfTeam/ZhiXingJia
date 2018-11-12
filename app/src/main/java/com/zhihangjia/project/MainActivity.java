package com.zhihangjia.project;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {

    }
}
