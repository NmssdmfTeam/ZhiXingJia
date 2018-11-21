package com.zhixingjia.goodsmanagemodule;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.activity.AddOrEditProductActivity;
import com.zhixingjia.goodsmanagemodule.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    public String getTAG() {
        return MainActivity.class.getSimpleName();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public BaseVM initViewModel() {
        return new BaseVM(this) {
        };
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityMainBinding) baseBinding;
        binding.btnPublishProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(AddOrEditProductActivity.class, null);
            }
        });
    }
}
