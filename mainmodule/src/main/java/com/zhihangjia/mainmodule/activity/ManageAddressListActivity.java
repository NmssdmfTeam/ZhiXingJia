package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.ManageAddressListAdapter;
import com.zhihangjia.mainmodule.databinding.ActivityManageAddresslistBinding;
import com.zhihangjia.mainmodule.viewmodel.ManageAddressListVM;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址管理
 * Create by chenbin on 2018/11/20
 * <p>
 * <p>
 */
public class ManageAddressListActivity extends BaseTitleActivity {
    private String TAG = ManageAddressListActivity.class.getSimpleName();
    private ManageAddressListVM vm;
    private ManageAddressListAdapter adapter;
    private ActivityManageAddresslistBinding binding;

    @Override
    public String setTitle() {
        return "收货地址";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityManageAddresslistBinding) baseViewBinding;
        List<Base> list = new ArrayList<>();
        for (int i=0; i < 2; i++) {
            list.add(new Base());
        }
        adapter = new ManageAddressListAdapter(list);
        binding.crv.setAdapter(adapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_manage_addresslist;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ManageAddressListVM(this);
        return vm;
    }
}
