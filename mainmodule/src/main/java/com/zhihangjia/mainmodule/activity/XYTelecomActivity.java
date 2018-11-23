package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.XYTelecomAdapter;
import com.zhihangjia.mainmodule.callback.XYTelecomCB;
import com.zhihangjia.mainmodule.databinding.ActivityXytelecomBinding;
import com.zhihangjia.mainmodule.viewmodel.XYTelecomVM;

public class XYTelecomActivity extends BaseActivity implements XYTelecomCB{
    private final String TAG = XYTelecomActivity.class.getSimpleName();

    private XYTelecomVM vm;
    private ActivityXytelecomBinding binding;
    private XYTelecomAdapter adapter;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_xytelecom;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new XYTelecomVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {

        WindowUtil.setWindowStatusBarTransParent(this);

        binding = (ActivityXytelecomBinding) baseBinding;
        vm.getData();
        adapter = new XYTelecomAdapter(vm.getList());

        binding.crv.setAdapter(adapter);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
