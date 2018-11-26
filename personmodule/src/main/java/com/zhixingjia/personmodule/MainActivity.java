package com.zhixingjia.personmodule;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.personmodule.activity.AddOrEditAddressActivity;
import com.zhixingjia.personmodule.activity.ManageAddressListActivity;
import com.zhixingjia.personmodule.activity.MyCouponsActivity;
import com.zhixingjia.personmodule.activity.PersonInfoActivity;
import com.zhixingjia.personmodule.activity.SetActivity;
import com.zhixingjia.personmodule.databinding.ActivityMainBinding;
import com.zhixingjia.personmodule.viewmodule.MainVM;

public class MainActivity extends BaseTitleActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private MainVM vm;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MainVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "个人信息模块";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        vm.doLogin();
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
        binding.btnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(AddOrEditAddressActivity.class,null);
            }
        });
        binding.btnAddressList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(ManageAddressListActivity.class,null);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }
}
