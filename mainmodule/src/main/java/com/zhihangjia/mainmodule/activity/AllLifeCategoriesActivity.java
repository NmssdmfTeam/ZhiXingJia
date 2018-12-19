package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.AllLifeCategoryAdapter;
import com.zhihangjia.mainmodule.adapter.CategoryAdapter;
import com.zhihangjia.mainmodule.callback.AllCategoriesCB;
import com.zhihangjia.mainmodule.databinding.ActivityAllCategoriesBinding;
import com.zhihangjia.mainmodule.viewmodel.AllCategoriesVM;
import com.zhihangjia.mainmodule.viewmodel.AllLifeCategoriesVM;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 生活服务全部分类
 */
public class AllLifeCategoriesActivity extends BaseActivity implements AllCategoriesCB {
    private final String TAG = AllLifeCategoriesActivity.class.getSimpleName();

    private ActivityAllCategoriesBinding binding;
    private AllLifeCategoriesVM vm;
    private AllLifeCategoryAdapter adapter;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_all_life_categories;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new AllLifeCategoriesVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityAllCategoriesBinding) baseBinding;
        adapter = new AllLifeCategoryAdapter(new ArrayList<>());
        binding.crv.setAdapter(adapter);
        binding.crv.setLayoutManager(new GridLayoutManager(this, 4));
        binding.crv.setLoadMoreEnable(false);
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.setVm(vm);
        setListener();
    }

    private void setListener() {
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getLifeCate();
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
