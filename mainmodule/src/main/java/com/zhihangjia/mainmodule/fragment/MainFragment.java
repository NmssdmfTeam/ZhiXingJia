package com.zhihangjia.mainmodule.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adpater.AdvertisingRotationViewPagerAdapter;
import com.zhihangjia.mainmodule.adpater.MainAdapter;
import com.zhihangjia.mainmodule.bean.IndexAdvertise;
import com.zhihangjia.mainmodule.databinding.FragmentMainBinding;
import com.zhihangjia.mainmodule.databinding.ItemMainCrvheadBinding;
import com.zhihangjia.mainmodule.viewmodel.MainVM;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 知行家首页fragment
 * @date 2018/11/13 15:53
 */
public class MainFragment extends BaseFragment {
    private final String TAG = MainFragment.class.getSimpleName();
    private FragmentMainBinding binding;
    private MainVM vm;
    private MainAdapter adapter;
    private AdvertisingRotationViewPagerAdapter viewPagerAdapter;

    @Override
    public BaseVM initViewModel() {
        return new BaseVM(this) {
        };
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMainBinding) baseBinding;
        adapter = new MainAdapter(new ArrayList());
        ItemMainCrvheadBinding itemMainCrvheadBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_main_crvhead,null,false);
        adapter.setHeaderView(itemMainCrvheadBinding.getRoot());
        float width = DensityUtil.getScreenWidth(getContext()) - DensityUtil.dpToPx(getContext(),16)*2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) width, (int) width * 120 / 343);
        itemMainCrvheadBinding.rpv.setLayoutParams(params);
        List<IndexAdvertise> indexAdvertises = new ArrayList<>();
        indexAdvertises.add(new IndexAdvertise());
        indexAdvertises.add(new IndexAdvertise());
        indexAdvertises.add(new IndexAdvertise());
        viewPagerAdapter = new AdvertisingRotationViewPagerAdapter(AdvertisingRotationViewPagerAdapter.MAIN_PAGER, indexAdvertises, itemMainCrvheadBinding.rpv);
        itemMainCrvheadBinding.rpv.setAdapter(viewPagerAdapter);
        binding.crv.setAdapter(adapter);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
