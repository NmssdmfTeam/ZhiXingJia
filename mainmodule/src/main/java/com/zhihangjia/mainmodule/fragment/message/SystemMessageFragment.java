package com.zhihangjia.mainmodule.fragment.message;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

public class SystemMessageFragment extends BaseFragment {
    private final String TAG = SystemMessageFragment.class.getSimpleName();
    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    public int setLayout() {
        return 0;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {

    }

    @Override
    public String getTAG() {
        return null;
    }
}
