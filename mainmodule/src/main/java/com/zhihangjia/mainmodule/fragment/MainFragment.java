package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.FragmentMainBinding;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

/**
* @description 知行家首页fragment
* @author chenbin
* @date 2018/11/13 15:53
* @version v3.2.0
*/
public class MainFragment extends BaseFragment {
    private final String TAG = MainFragment.class.getSimpleName();
    private FragmentMainBinding binding;

    @Override
    public BaseVM initViewModel() {
        return new BaseVM(this) {
        };
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
