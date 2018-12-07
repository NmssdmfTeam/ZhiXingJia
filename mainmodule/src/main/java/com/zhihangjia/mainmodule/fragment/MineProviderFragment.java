package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.config.StringConfig;
import com.zhihangjia.mainmodule.R;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.MainActivity;
import com.zhihangjia.mainmodule.callback.MineProviderFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMineProviderBinding;
import com.zhihangjia.mainmodule.viewmodel.MineProviderFragmentVM;
import com.zhixingjia.bean.mainmodule.MessageUnread;

/**
* @description 知行家首页-- 建材家居fragment
* @author chenbin
* @date 2018/11/13 15:53
* @version v3.2.0
*/
public class MineProviderFragment extends BaseFragment implements MineProviderFragmentCB {
    private final String TAG = MineProviderFragment.class.getSimpleName();
    private FragmentMineProviderBinding binding;
    private MineProviderFragmentVM vm;

    @Override
    public BaseVM initViewModel() {
        vm = new MineProviderFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_mine_provider;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMineProviderBinding) baseBinding;
        binding.msfl.setSwipeableChildren(R.id.sv_customer_my);
        vm.getUserInfo();
        setListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.setData(null);
        vm.getMessageUnread();
    }

    private void setListener() {
        binding.tvChanggeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.changeIdentify(StringConfig.BUYER);
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

    }

    @Override
    public void showNotice(MessageUnread messageUnread) {
        if (TextUtils.isEmpty(messageUnread.getAll_message()) || "0".equals(messageUnread.getAll_message())) {
            binding.ivMessageNotice.setVisibility(View.GONE);
        } else {
            binding.ivMessageNotice.setVisibility(View.VISIBLE);
        }
    }
}
