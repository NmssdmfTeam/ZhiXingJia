package com.zhihangjia.mainmodule.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhihangjia.mainmodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantEvaluateFragment extends Fragment {


    public MerchantEvaluateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_merchant_evaluate, container, false);
    }

}
