package com.zhixingjia.personmodule.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.UserInfo;
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
    protected void onResume() {
        super.onResume();
        String userinfo = PreferenceUtil.getString(PrefrenceConfig.USER_INFO,"");
        if (!TextUtils.isEmpty(userinfo)) {
            UserInfo userInfoBean = new Gson().fromJson(userinfo, UserInfo.class);
            if (userInfoBean.getMobile().length() >= 11)
                binding.tvAccount.setText(userInfoBean.getMobile().substring(0,3) + "****"+ userInfoBean.getMobile().substring(7,11));
        }
    }

    @Override
    public String setTitle() {
        return "设置";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivitySetBinding) baseViewBinding;
        binding.setVm(vm);
        vm.getCacheSize(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_set;
    }

    @Override
    public void confirmLogout() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("是否退出登录").setPositiveButton("确认", new DialogInterface.OnClickListener() {
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
        int width = DensityUtil.dpToPx(this, 340);
        alertDialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void confirmCleanCache() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("是否要清楚缓存").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                vm.cleanCache(SetActivity.this);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
        int width = DensityUtil.dpToPx(this, 340);
        alertDialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}
