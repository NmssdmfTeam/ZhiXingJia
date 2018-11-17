package com.zhihangjia.mainmodule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MerchandiseDetailFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMerchandiseDetailBinding;
import com.zhihangjia.mainmodule.viewmodel.MerchandiseDetailFragmentVM;
import com.zhihangjia.mainmodule.window.ChooseShopCouponWindow;
import com.zhihangjia.mainmodule.window.ChooseSpecificationWindow;


/**
 * A simple {@link Fragment} subclass.
 */
public class MerchandiseDetailFragment extends BaseFragment implements MerchandiseDetailFragmentCB {

    private final String TAG = MerchandiseDetailFragment.class.getSimpleName();
    private MerchandiseDetailFragmentVM vm;

    private FragmentMerchandiseDetailBinding binding;

    private ChooseShopCouponWindow chooseShopCouponWindow;
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
        if (chooseShopCouponWindow == null) {
            chooseShopCouponWindow = new ChooseShopCouponWindow(getActivity());
        }
        chooseShopCouponWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showChooseSpecificationWindow() {
        if (chooseSpecificationWindow == null) {
            chooseSpecificationWindow = new ChooseSpecificationWindow(getActivity());
        }
        chooseSpecificationWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }
}
