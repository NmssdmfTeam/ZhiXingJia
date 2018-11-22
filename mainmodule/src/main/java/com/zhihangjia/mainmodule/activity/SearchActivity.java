package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.SearchCB;
import com.zhihangjia.mainmodule.databinding.ActivitySearchBinding;
import com.zhihangjia.mainmodule.fragment.SearchFragment;
import com.zhihangjia.mainmodule.viewmodel.SearchFragmentVM;
import com.zhihangjia.mainmodule.viewmodel.SearchVM;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 * 历史记录保存在本地
 * 热门搜索来自接口
 */
public class SearchActivity extends BaseTitleActivity implements SearchCB{
    private final String TAG = SearchActivity.class.getSimpleName();
    private ActivitySearchBinding binding;

    private List<Fragment> list = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    SearchFragment materialsMerchantFragment = new SearchFragment();
    SearchFragment materialsMerchandiseFragment = new SearchFragment();
    SearchFragment informationCenterFragment = new SearchFragment();
    private SearchVM vm;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new SearchVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "搜索";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivitySearchBinding) baseViewBinding;

        setISlenderLineGone();
        initTabLayout();
        vm.getHistory();
    }

    private void initTabLayout(){
        list.add(materialsMerchantFragment);
        list.add(materialsMerchandiseFragment);
        list.add(informationCenterFragment);

        materialsMerchantFragment.setType(SearchFragmentVM.TYPE_MATERIALS_MERCHANT);
        materialsMerchandiseFragment.setType(SearchFragmentVM.TYPE_MATERIALS_MERCHANDISE);
        informationCenterFragment.setType(SearchFragmentVM.TYPE_INFORMATION_CENTER);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), SearchActivity.this, list);
        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);

        binding.tl.getTabAt(0).setText("建材商家");
        binding.tl.getTabAt(1).setText("建材商品");
        binding.tl.getTabAt(2).setText("信息中心");

        binding.vp.setOffscreenPageLimit(2);//因为vp预加载只有1个，导致第三个无法加载数据，所以设置预加载2个
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void setMaterialsMerchantHotHistory(List<String> list) {
        materialsMerchantFragment.initHotSearchHistory(list);
    }

    @Override
    public void setMaterialsMerchandiseHotHistory(List<String> list) {
        materialsMerchandiseFragment.initHotSearchHistory(list);
    }

    @Override
    public void setInformationCenterHotHistory(List<String> list) {
        informationCenterFragment.initHotSearchHistory(list);
    }
}
