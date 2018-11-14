package com.zhihangjia.mainmodule.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.MaterialsMerchantFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMaterialsMerchantBinding;
import com.zhihangjia.mainmodule.viewmodel.MaterialsMerchantFragmentVM;

/**
 * A simple {@link Fragment} subclass.
 * 建材商家
 */
public class MaterialsMerchantFragment extends BaseFragment implements MaterialsMerchantFragmentCB {
    private final String TAG = MaterialsMerchantFragment.class.getSimpleName();

    private FragmentMaterialsMerchantBinding binding;
    private MaterialsMerchantFragmentVM vm;

    @Override
    public BaseVM initViewModel() {
        vm = new MaterialsMerchantFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_materials_merchant;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMaterialsMerchantBinding) baseBinding;

        initSearchHistory();

        initHotSearchHistory();

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                JLog.i(TAG, "setOnEditorActionListener action:" + actionId);
                // 如果是search action，或者为指定的action都执行搜索,有些手机actionSearch没有,所以使用ENTER键判断
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // 因为onEditorAction在键盘按下和按上时都会出发，所以需要过滤一个
                    if (event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
                        return true;
                    }

                    vm.doSearch();
                }
                return true;
            }
        });

        vm.getHistory();
    }

    private void initSearchHistory(){
        for (int i = 0; i < vm.getHistorys().size(); i++) {
            final TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#FF666666"));
            textView.setBackgroundResource(R.drawable.shape_search_edittext);
            textView.setTextSize(12);
            textView.setText("第几个" + i * 2);

            int paddingleft = DensityUtil.dpToPx(getActivity(), 12);
            int paddingtop = DensityUtil.dpToPx(getActivity(), 7);
            textView.setPadding(paddingleft, paddingtop, paddingleft, paddingtop);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vm.keyword.set(textView.getText().toString());
                    vm.doSearch();
                }
            });

            binding.tlSearchHistory.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    private void initHotSearchHistory() {
        for (int i = 0; i < vm.getHotHistorys().size(); i++) {
            final TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#FF666666"));
            textView.setBackgroundResource(R.drawable.shape_search_edittext);
            textView.setTextSize(12);
            textView.setText("第几个" + i);

            int paddingleft = DensityUtil.dpToPx(getActivity(), 12);
            int paddingtop = DensityUtil.dpToPx(getActivity(), 7);
            textView.setPadding(paddingleft, paddingtop, paddingleft, paddingtop);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vm.keyword.set(textView.getText().toString());
                    vm.doSearch();
                }
            });

            binding.tlHotSearchHistory.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void clearHistory() {
        binding.tlSearchHistory.removeAllViews();
    }
}
