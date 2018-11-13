package com.zhihangjia.loginmodule.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.R;

public class RegisterActivity extends BaseTitleActivity {
    private final String TAG = RegisterActivity.class.getSimpleName();
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
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }
}
