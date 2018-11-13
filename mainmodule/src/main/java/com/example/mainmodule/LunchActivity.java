package com.example.mainmodule;

import android.os.Bundle;
import android.view.View;

import com.example.mainmodule.databinding.ActivityLunchBinding;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

public class LunchActivity extends BaseTitleActivity {
    private String TAG = LunchActivity.class.getSimpleName();
    private ActivityLunchBinding binding;

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
        binding = (ActivityLunchBinding) baseViewBinding;
        binding.btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_lunch;
    }
}
