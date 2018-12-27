package com.zhixingjia.personmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.callback.AddOrEditAddressCB;
import com.zhixingjia.personmodule.databinding.ActivityAddOrEditAddressBinding;
import com.zhixingjia.personmodule.viewmodule.AddOrEditAddressVM;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加或编辑地址
 * Create by chenbin on 2018/11/20
 * <p>
 * <p>
 */
public class AddOrEditAddressActivity extends BaseTitleActivity implements AddOrEditAddressCB {
    private final String TAG = AddOrEditAddressActivity.class.getSimpleName();
    private AddOrEditAddressVM vm;
    private ActivityAddOrEditAddressBinding binding;
    private WheelPickerWindow selectAddressWindow;

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityAddOrEditAddressBinding) baseViewBinding;
        selectAddressWindow = new WheelPickerWindow(this, new ArrayList<String>(), new WheelPickerWindowCB() {
            @Override
            public void tvSureClick(String item, int position) {
                vm.area.set(item);
            }
        });

        vm.iniData();
        vm.getArea();
        if (!TextUtils.isEmpty(vm.getAddrId()))
            baseTitleBinding.tTitle.inflateMenu(R.menu.delete);
    }

    private void setListener() {
        binding.tvNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAddressWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
            }
        });
        baseTitleBinding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                vm.deleteAddress();
                return false;
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_or_edit_address;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new AddOrEditAddressVM(this);
        return vm;
    }

    @Override
    public void setArea(List<String> areas) {
        selectAddressWindow.changeDataList(areas);
        binding.setVm(vm);
        setListener();
    }

    @Override
    public void chnageTitle(String title) {
        setTitle(title);
    }
}
