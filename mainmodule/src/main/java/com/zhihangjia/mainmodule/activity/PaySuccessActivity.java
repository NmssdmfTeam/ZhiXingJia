package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityPaySuccessBinding;

public class PaySuccessActivity extends BaseTitleActivity {
    private final String TAG = PaySuccessActivity.class.getSimpleName();
    private ActivityPaySuccessBinding binding;
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
        return "支付成功";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityPaySuccessBinding) baseViewBinding;

        binding.tvViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.tvContinueStroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_pay_success;
    }
}
