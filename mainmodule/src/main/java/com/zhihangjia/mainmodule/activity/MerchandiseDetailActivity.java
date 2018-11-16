package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;

public class MerchandiseDetailActivity extends BaseActivity {

    private final String TAG = MerchandiseDetailActivity.class.getSimpleName();

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_merchandise_detail;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        WindowUtil.setWindowStatusBarTransParent(this);
    }
}
