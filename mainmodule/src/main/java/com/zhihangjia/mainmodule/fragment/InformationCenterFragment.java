package com.zhihangjia.mainmodule.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationCenterFragment extends BaseFragment {

    private final String TAG = InformationCenterFragment.class.getSimpleName();

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_information_center;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {

    }

    @Override
    public String getTAG() {
        return TAG;
    }

}
