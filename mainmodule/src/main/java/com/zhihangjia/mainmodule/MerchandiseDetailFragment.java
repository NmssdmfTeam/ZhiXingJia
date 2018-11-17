package com.zhihangjia.mainmodule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.callback.MerchandiseDetailFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMerchandiseDetailBinding;
import com.zhihangjia.mainmodule.viewmodel.MerchandiseDetailFragmentVM;


/**
 * A simple {@link Fragment} subclass.
 */
public class MerchandiseDetailFragment extends BaseFragment implements MerchandiseDetailFragmentCB {

    private final String TAG = MerchandiseDetailFragment.class.getSimpleName();
    private MerchandiseDetailFragmentVM vm;

    private FragmentMerchandiseDetailBinding binding;
    @Override
    public BaseVM initViewModel() {
        vm = new MerchandiseDetailFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_merchandise_detail;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMerchandiseDetailBinding) baseBinding;
        binding.setVm(vm);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void onBack() {
        getActivity().onBackPressed();
    }

    @Override
    public void gotoCommentDetail() {
        if (getActivity() instanceof MerchandiseDetailActivity) {
            MerchandiseDetailActivity merchandiseDetailActivity = (MerchandiseDetailActivity) getActivity();
            merchandiseDetailActivity.switchToCommentFragment();
        }
    }
}
