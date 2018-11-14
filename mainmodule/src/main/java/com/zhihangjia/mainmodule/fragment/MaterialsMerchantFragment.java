package com.zhihangjia.mainmodule.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.FragmentMaterialsMerchantBinding;

/**
 * A simple {@link Fragment} subclass.
 * 建材商家
 */
public class MaterialsMerchantFragment extends BaseFragment {
    private final String TAG = MaterialsMerchantFragment.class.getSimpleName();
    private FragmentMaterialsMerchantBinding binding;

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_materials_merchant;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMaterialsMerchantBinding) baseBinding;

        for (int i = 0; i< 10; i++) {
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#FF666666"));
            textView.setBackgroundResource(R.drawable.shape_search_edittext);
            textView.setTextSize(12);
            textView.setText("第几个" + i*2);

            int paddingleft = DensityUtil.dpToPx(getActivity(), 12);
            int paddingtop = DensityUtil.dpToPx(getActivity(), 7);
            textView.setPadding(paddingleft, paddingtop, paddingleft, paddingtop);

            binding.tlSearchHistory.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        for (int i = 0; i< 10; i++) {
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#FF666666"));
            textView.setBackgroundResource(R.drawable.shape_search_edittext);
            textView.setTextSize(12);
            textView.setText("第几个" + i);

            int paddingleft = DensityUtil.dpToPx(getActivity(), 12);
            int paddingtop = DensityUtil.dpToPx(getActivity(), 7);
            textView.setPadding(paddingleft, paddingtop, paddingleft, paddingtop);

            binding.tlHotSearchHistory.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public String getTAG() {
        return TAG;
    }

}
