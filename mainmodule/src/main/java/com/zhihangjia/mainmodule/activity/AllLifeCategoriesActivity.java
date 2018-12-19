package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.AllLifeCategoryAdapter;
import com.zhihangjia.mainmodule.adapter.CategoryAdapter;
import com.zhihangjia.mainmodule.callback.AllCategoriesCB;
import com.zhihangjia.mainmodule.databinding.ActivityAllLifeCategoriesBinding;
import com.zhihangjia.mainmodule.viewmodel.AllCategoriesVM;
import com.zhihangjia.mainmodule.viewmodel.AllLifeCategoriesVM;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 生活服务全部分类
 */
public class AllLifeCategoriesActivity extends BaseTitleActivity implements AllCategoriesCB {
    private final String TAG = AllLifeCategoriesActivity.class.getSimpleName();

    private ActivityAllLifeCategoriesBinding binding;
    private AllLifeCategoriesVM vm;
    private AllLifeCategoryAdapter adapter;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public String setTitle() {
        return "生活服务";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityAllLifeCategoriesBinding) baseViewBinding;
        adapter = new AllLifeCategoryAdapter(new ArrayList<>());
        binding.crv.setAdapter(adapter);
        binding.crv.setLayoutManager(new GridLayoutManager(this, 4));
        binding.crv.setLoadMoreEnable(false);
        binding.setVm(vm);
        setListener();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_all_life_categories;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new AllLifeCategoriesVM(this);
        return vm;
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
