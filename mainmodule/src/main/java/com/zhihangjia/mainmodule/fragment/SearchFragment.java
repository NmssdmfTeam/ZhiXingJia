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
import com.nmssdmf.commonlib.util.KeyBoardUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.SearchFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentSearchBinding;
import com.zhihangjia.mainmodule.viewmodel.SearchFragmentVM;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 建材商家
 */
public class SearchFragment extends BaseFragment implements SearchFragmentCB {
    private final String TAG = SearchFragment.class.getSimpleName();

    private FragmentSearchBinding binding;
    private SearchFragmentVM vm;

    @Override
    public BaseVM initViewModel() {
        vm = new SearchFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentSearchBinding) baseBinding;
        binding.setVm(vm);
        initSearchHistory();
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
    }

    private void initSearchHistory() {
        binding.tlSearchHistory.removeAllViews();
        int size = vm.getHistorys().size() - 1;
        for (int i = size; i >= 0; i--) {
            final TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#FF666666"));
            textView.setBackgroundResource(R.drawable.shape_search_edittext);
            textView.setTextSize(12);
            textView.setText(vm.getHistorys().get(i));

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

    public void initHotSearchHistory(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            final TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#FF666666"));
            textView.setBackgroundResource(R.drawable.shape_search_edittext);
            textView.setTextSize(12);
            textView.setText(list.get(i));

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

    @Override
    public void refreshHistory() {
        initSearchHistory();
    }

    /**
     * 该方法在fragment被activity加载之后使用
     *
     * @param type
     */
    public void setType(String type) {
        if (vm != null) {
            vm.setType(type);
            vm.getHistroyData();
        } else {
            JLog.d(TAG, "vm == null");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        closeKeyBoard();
    }

    @Override
    public void closeKeyBoard() {
        KeyBoardUtil.closeKeyWords(binding.etSearch);
    }
}
