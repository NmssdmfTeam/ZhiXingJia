package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityAddOrEditAddressBinding;
import com.zhihangjia.mainmodule.viewmodel.AddOrEditAddressVM;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加或编辑地址
 * Create by chenbin on 2018/11/20
 * <p>
 * <p>
 */
public class AddOrEditAddressActivity extends BaseTitleActivity {
    private final String TAG = AddOrEditAddressActivity.class.getSimpleName();
    private AddOrEditAddressVM vm;
    private ActivityAddOrEditAddressBinding binding;
    private WheelPickerWindow selectAddressWindow;

    @Override
    public String setTitle() {
        return "新增地址";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityAddOrEditAddressBinding) baseViewBinding;
        List<String> list = new ArrayList<>();
        list.add("宜城街道");
        list.add("新街街道");
        list.add("新庄街道");
        selectAddressWindow = new WheelPickerWindow(this, list, new WheelPickerWindowCB() {
            @Override
            public void tvSureClick(String item, int position) {

            }
        });
        binding.tvNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAddressWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
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
}
