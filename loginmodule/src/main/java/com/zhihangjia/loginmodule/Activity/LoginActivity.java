package com.zhihangjia.loginmodule.Activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.R;

public class LoginActivity extends BaseTitleActivity {

    @Override
    public String getTAG() {
        return null;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    public String setTitle() {
        return null;
    }

    @Override
    public void initContent(Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }
}
