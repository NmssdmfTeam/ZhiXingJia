package com.zhixingjia.personmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.databinding.ActivityChangeNameBinding;
import com.zhixingjia.personmodule.viewmodule.ChangeNameVM;

/**
 * 修改名称
 */
public class ChangeNameActivity extends BaseTitleActivity {
    private final String TAG = ChangeNameActivity.class.getSimpleName();
    private ActivityChangeNameBinding binding;
    private ChangeNameVM vm;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ChangeNameVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "个人信息";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityChangeNameBinding) baseViewBinding;
        binding.setVm(vm);
        vm.getData();
        baseTitleBinding.tTitle.inflateMenu(R.menu.menu_save);
        baseTitleBinding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() ==  R.id.iSave) {
                    vm.changeName();
                }
                return true;
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_change_name;
    }
}
