package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.util.CommonUtils;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MainActivity;
import com.zhihangjia.mainmodule.callback.MineCustomerFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMineCustomerBinding;
import com.zhihangjia.mainmodule.viewmodel.MineCustomerFragmentVM;

/**
* @description 我的买家版
* @author chenbin
* @date 2018/11/20 13:25
* @version v3.2.0
*/
public class MineCustomerFragment extends BaseFragment implements MineCustomerFragmentCB {
    private final String TAG = MineCustomerFragment.class.getSimpleName();
    private FragmentMineCustomerBinding binding;
    private MineCustomerFragmentVM vm;

    @Override
    public BaseVM initViewModel() {
        vm = new MineCustomerFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_mine_customer;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMineCustomerBinding) baseBinding;
        binding.msfl.setSwipeableChildren(R.id.sv_customer_my);
        setListener();
        vm.getUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.setData(null);
    }

    private void setListener() {
        binding.tvChanggeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.changeIdentify(StringConfig.PROVIDER);
                }
            }
        });
        binding.msfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vm.getUserInfo();
            }
        });
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void bindVM() {
        binding.setVm(vm);
    }

    @Override
    public void endFresh() {
        binding.msfl.setRefreshing(false);
    }

    @Override
    public void initView() {
        binding.tvAprovelState.setText(vm.userinfo.get().getVerify_status());
    }

    @Override
    public void phoneCall(String phoneNumber) {
        CommonUtils.callPhone(getActivity(), phoneNumber);
    }
}
