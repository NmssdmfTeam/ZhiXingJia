package com.zhixingjia.personmodule.activity;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhixingjia.bean.personmodule.Address;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.adapter.ManageAddressListAdapter;
import com.zhixingjia.personmodule.callback.ManageAddressCB;
import com.zhixingjia.personmodule.databinding.ActivityManageAddresslistBinding;
import com.zhixingjia.personmodule.viewmodule.ManageAddressListVM;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址管理
 * Create by chenbin on 2018/11/20
 * <p>
 * <p>
 */
public class ManageAddressListActivity extends BaseTitleActivity implements ManageAddressCB {
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
        adapter = new ManageAddressListAdapter(new ArrayList<Address>(), vm.isSelect);
        binding.crv.setAdapter(adapter);
        setListener();
        vm.getAddressList(true);
    }

    private void setListener() {
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getAddressList(true);
            }

            @Override
            public void onLoadMore() {
                vm.getAddressList(false);
            }
        });
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(AddOrEditAddressActivity.class, null);
            }
        });
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

    @Override
    public void endFresh() {
        binding.crv.setRefreshing(false);
        adapter.notifyDataChangedAfterLoadMore(false);
    }

    @Override
    public void setData(List<Address> addresses, boolean isRefresh) {
        if (isRefresh) {
            binding.crv.setRefreshing(false);
        }
        adapter.notifyDataChangedAfterLoadMore(isRefresh, addresses);
    }

    @Override
    public void setAddress(Address address, int position) {
        adapter.getData().set(position, address);
        if ("1".equals(address.getIs_default()))
            adapter.getData().get(adapter.getDefaultPosition()).setIs_default("0");
        adapter.notifyItemChanged(adapter.getDefaultPosition());
        adapter.notifyItemChanged(position);
        adapter.setDefaultPosition(position);
    }
}
