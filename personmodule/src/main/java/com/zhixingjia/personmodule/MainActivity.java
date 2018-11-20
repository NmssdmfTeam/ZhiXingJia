package com.zhixingjia.personmodule;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.personmodule.activity.MyCouponsActivity;
import com.zhixingjia.personmodule.activity.PersonInfoActivity;
import com.zhixingjia.personmodule.activity.SetActivity;
import com.zhixingjia.personmodule.databinding.ActivityMainBinding;

public class MainActivity extends BaseTitleActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
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
        return "个人信息模块";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityMainBinding) baseViewBinding;

        binding.btnPersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(PersonInfoActivity.class, null);
            }
        });

        binding.btnMyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(MyCouponsActivity.class, null);
            }
        });

        binding.btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(SetActivity.class, null);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }
}
