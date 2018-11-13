package com.zhihangjia.loginmodule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.Activity.LoginActivity;
import com.zhihangjia.loginmodule.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;

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
        binding = (ActivityMainBinding) baseBinding;
        binding.bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
