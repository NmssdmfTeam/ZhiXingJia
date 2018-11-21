package com.zhixingjia.goodsmanagemodule;

import android.os.Bundle;
import android.view.View;
import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.activity.AddOrEditProductActivity;
import com.zhixingjia.goodsmanagemodule.activity.AddProductDescribeActivity;
import com.zhixingjia.goodsmanagemodule.activity.GoodManageActivity;
import com.zhixingjia.goodsmanagemodule.activity.LadderPriceSettingActivity;
import com.zhixingjia.goodsmanagemodule.activity.PriceSettingActivity;
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
        binding.btnProductDescrible.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                doIntent(AddProductDescribeActivity.class, null);
            }
        });
        binding.btnPriceSetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                doIntent(PriceSettingActivity.class, null);
            }
        });
        binding.btnLadderPriceSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(LadderPriceSettingActivity.class,null);
            }
        });

        binding.btnGoodManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(GoodManageActivity.class,null);
            }
        });
        binding.btnSelectStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(SelectStandardActivity.class,null);
            }
        });
    }
}
