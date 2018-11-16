package com.zhihangjia.mainmodule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;


/**
 * A simple {@link Fragment} subclass.
 */
public class MerchandiseDetailFragment extends BaseFragment {

    private final String TAG = MerchandiseDetailFragment.class.getSimpleName();

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_merchandise_detail;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {

    }

    @Override
    public String getTAG() {
        return TAG;
    }

}
