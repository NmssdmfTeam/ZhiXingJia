package com.zhihangjia.mainmodule.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.callback.MerchandiseDetailFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMerchandiseDetailBinding;
import com.zhihangjia.mainmodule.viewmodel.MerchandiseDetailFragmentVM;
import com.zhihangjia.mainmodule.window.GetShopCouponWindow;
import com.zhihangjia.mainmodule.window.ChooseSpecificationWindow;


/**
 * A simple {@link Fragment} subclass.
 */
public class MerchandiseDetailFragment extends BaseFragment implements MerchandiseDetailFragmentCB {

    private final String TAG = MerchandiseDetailFragment.class.getSimpleName();
    private MerchandiseDetailFragmentVM vm;

    private FragmentMerchandiseDetailBinding binding;

    private GetShopCouponWindow getShopCouponWindow;
    private ChooseSpecificationWindow chooseSpecificationWindow;
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
    public void showChooseCouponWindow() {
        if (getShopCouponWindow == null) {
            getShopCouponWindow = new GetShopCouponWindow(getActivity());
        }
        getShopCouponWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showChooseSpecificationWindow() {
        if (chooseSpecificationWindow == null) {
            chooseSpecificationWindow = new ChooseSpecificationWindow(getActivity());
        }
        chooseSpecificationWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void gotoCommentDetail() {
        if (getActivity() instanceof MerchandiseDetailActivity) {
            MerchandiseDetailActivity merchandiseDetailActivity = (MerchandiseDetailActivity) getActivity();
            merchandiseDetailActivity.switchToCommentFragment();
        }
    }
}
