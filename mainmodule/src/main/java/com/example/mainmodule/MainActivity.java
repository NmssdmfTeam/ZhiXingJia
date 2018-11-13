package com.example.mainmodule;

import android.os.Bundle;
import android.view.View;

import com.example.mainmodule.databinding.ActivityMainBinding;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

public class MainActivity extends BaseTitleActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        return new BaseVM(this) {
        };
    }

    @Override
    public String setTitle() {
        return "跳转首页";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityMainBinding)baseViewBinding;
        binding.btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }
}
