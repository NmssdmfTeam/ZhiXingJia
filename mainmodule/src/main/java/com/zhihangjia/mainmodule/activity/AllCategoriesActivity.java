package com.zhihangjia.mainmodule.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.AllCategoriesCB;
import com.zhihangjia.mainmodule.databinding.ActivityAllCategoriesBinding;
import com.zhihangjia.mainmodule.viewmodel.AllCategoriesVM;

/**
 * 全部分类
 */
public class AllCategoriesActivity extends BaseActivity implements AllCategoriesCB {
    private final String TAG = AllCategoriesActivity.class.getSimpleName();

    private ActivityAllCategoriesBinding binding;
    private AllCategoriesVM vm;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_all_categories;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new AllCategoriesVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityAllCategoriesBinding) baseBinding;

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.setSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        vm.getData();
        initTaglayout();
    }

    private void initTaglayout() {
        for (int i = 0; i < vm.getMainCategoryList().size(); i++) {
            final TextView textView = new TextView(this);
            textView.setWidth(DensityUtil.dpToPx(this, 74));
            textView.setHeight(DensityUtil.dpToPx(this, 26));
            binding.tl.addView(textView);
            textView.setText(vm.getMainCategoryList().get(i));
            textView.setGravity(Gravity.CENTER);
            if (i == vm.getMainCategoryIndex()) {
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundResource(R.drawable.shape_categories_selected);
            } else {
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.parseColor("#FF666666"));
            }
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundResource(R.drawable.shape_categories_selected);

                    TextView oldTextView = (TextView) binding.tl.getChildAt(vm.getMainCategoryIndex());
                    oldTextView.setTextColor(Color.parseColor("#FF666666"));
                    oldTextView.setBackgroundColor(Color.WHITE);

                    vm.setMainCategoryIndex(finalI);
                    vm.changeMainCategory();
                }
            });
        }
    }
}
