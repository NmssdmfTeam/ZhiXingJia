package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.SelectedCategoryAdapter;
import com.zhihangjia.mainmodule.callback.SelectCategoriesCB;
import com.zhihangjia.mainmodule.databinding.ActivityCategoriesSelectBinding;
import com.zhihangjia.mainmodule.viewmodel.SelectCategoryVM;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部分类
 */
public class SelectCategoriesActivity extends BaseTitleActivity implements SelectCategoriesCB {
    private final String TAG = SelectCategoriesActivity.class.getSimpleName();

    private ActivityCategoriesSelectBinding binding;
    private SelectCategoryVM vm;
    private SelectedCategoryAdapter adapter;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public String setTitle() {
        return "选者类别";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityCategoriesSelectBinding) baseViewBinding;
        adapter = new SelectedCategoryAdapter(new ArrayList<>());
        binding.crv.setAdapter(adapter);
        binding.crv.setLayoutManager(new GridLayoutManager(this, 4));
        binding.crv.setLoadMoreEnable(false);
        setListener();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_categories_select;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new SelectCategoryVM(this);
        return vm;
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
        binding.setVm(vm);
    }

    @Override
    public List<HouseBean.CateBean> getSelectedData() {
        List<HouseBean.CateBean> cateBeans = new ArrayList<>();
        for (HouseBean.CateBean cateBean : adapter.getData()) {
            if (cateBean.isSelect()) {
                cateBeans.add(cateBean);
            }
        }
        return cateBeans;
    }
}
