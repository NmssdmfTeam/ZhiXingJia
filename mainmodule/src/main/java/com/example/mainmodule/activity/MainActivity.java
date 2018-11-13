package com.example.mainmodule.activity;

import android.os.Bundle;

import com.example.mainmodule.R;
import com.example.mainmodule.databinding.ActivityMainBinding;
import com.example.mainmodule.viewmodel.MainVM;
import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

/**
* @description 知行家首页
* @author chenbin
* @date 2018/11/13 15:00
* @version v3.2.0
*/
public class MainActivity extends BaseActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private MainVM vm;
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
        vm = new MainVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {

    }
}
