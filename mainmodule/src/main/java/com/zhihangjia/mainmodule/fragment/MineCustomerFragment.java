package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MainActivity;
import com.zhihangjia.mainmodule.databinding.FragmentMineCustomerBinding;
import com.zhihangjia.mainmodule.viewmodel.MineCustomerFragmentVM;

/**
* @description 我的买家版
* @author chenbin
* @date 2018/11/20 13:25
* @version v3.2.0
*/
public class MineCustomerFragment extends BaseFragment {
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
        binding.setVm(vm);
        binding.msfl.setSwipeableChildren(R.id.sv_customer_my);
        setListener();
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
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
