package com.zhihangjia.mainmodule;

import android.os.Bundle;
import android.view.View;

import com.zhihangjia.mainmodule.activity.MainActivity;
import com.zhihangjia.mainmodule.databinding.ActivityLunchBinding;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

public class LunchActivity extends BaseTitleActivity {
    private String TAG = LunchActivity.class.getSimpleName();
    private ActivityLunchBinding binding;

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
                doIntent(MainActivity.class,null);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_lunch;
    }
}
