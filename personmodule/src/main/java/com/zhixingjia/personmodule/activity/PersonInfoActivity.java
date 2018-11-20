package com.zhixingjia.personmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.callback.PersonInfoCB;
import com.zhixingjia.personmodule.databinding.ActivityPersonInfoBinding;
import com.zhixingjia.personmodule.viewmodule.PersonInfoVM;

public class PersonInfoActivity extends BaseTitleActivity implements PersonInfoCB{
    private final String TAG = PersonInfoActivity.class.getSimpleName();

    private ActivityPersonInfoBinding binding;
    private WheelPickerWindow selectSexWindow;

    private PersonInfoVM vm;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new PersonInfoVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "个人信息";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityPersonInfoBinding) baseViewBinding;
        binding.setVm(vm);

        vm. initData();
        binding.tvSelectSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectSexWindow == null) {
                    selectSexWindow = new WheelPickerWindow(PersonInfoActivity.this, vm.getSexList(), vm);
                }
                selectSexWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_person_info;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PersonInfoVM.CHANGE_NAME_REQUEST_CODE:{
                vm.nickName.set(data.getExtras().getString(IntentConfig.NAME));
                break;
            }
        }
    }
}
