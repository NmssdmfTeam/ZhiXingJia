package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.CategoryAdapter;
import com.zhihangjia.mainmodule.callback.AllCategoriesCB;
import com.zhihangjia.mainmodule.databinding.ActivityAllCategoriesBinding;
import com.zhihangjia.mainmodule.viewmodel.AllCategoriesVM;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部分类
 */
public class AllCategoriesActivity extends BaseActivity implements AllCategoriesCB {
    private final String TAG = AllCategoriesActivity.class.getSimpleName();

    private ActivityAllCategoriesBinding binding;
    private AllCategoriesVM vm;
    private CategoryAdapter adapter;

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
        adapter = new CategoryAdapter(new ArrayList<>());
        binding.crv.setAdapter(adapter);
        binding.crv.setLayoutManager(new GridLayoutManager(this, 4));
        binding.crv.setLoadMoreEnable(false);
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.setVm(vm);
//        binding.setSearch.setOnEditorActionListener((v, actionId, event) -> {
//            JLog.i(TAG, "setOnEditorActionListener action:" + actionId);
//            // 如果是search action，或者为指定的action都执行搜索,有些手机actionSearch没有,所以使用ENTER键判断
//            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                // 因为onEditorAction在键盘按下和按上时都会出发，所以需要过滤一个
//                if (event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    return true;
//                }
//                vm.doSearch();
//            }
//            return true;
//        });
        setListener();
    }

    private void setListener() {
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getHouseCate();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }


    @Override
    public void setData(List<HouseBean.CateBean> cateBeans) {
        binding.crv.setRefreshing(false);
        adapter.setNewData(cateBeans);
    }
}
