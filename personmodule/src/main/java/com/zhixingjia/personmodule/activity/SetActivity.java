package com.zhixingjia.personmodule.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.callback.SetCB;
import com.zhixingjia.personmodule.viewmodule.SetVM;
import com.zhixingjia.personmodule.databinding.ActivitySetBinding;

public class SetActivity extends BaseTitleActivity implements SetCB {
    private final String TAG = SetActivity.class.getSimpleName();
    private SetVM vm;
    private ActivitySetBinding binding;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new SetVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "设置";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivitySetBinding) baseViewBinding;
        binding.setVm(vm);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_set;
    }

    @Override
    public void confirmLogout() {
        new AlertDialog.Builder(this).setMessage("请打开GPS或者WIFI开关").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                vm.logout();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
