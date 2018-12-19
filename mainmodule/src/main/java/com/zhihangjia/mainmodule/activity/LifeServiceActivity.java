package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.view.TagView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.AdvertisingRotationViewPagerAdapter;
import com.zhihangjia.mainmodule.adapter.LifeServiceAdapter;
import com.zhihangjia.mainmodule.callback.LifeServiceCB;
import com.zhihangjia.mainmodule.databinding.ActivityLifeServiceBinding;
import com.zhihangjia.mainmodule.databinding.ItemPostTagBinding;
import com.zhihangjia.mainmodule.viewmodel.LifeServiceVM;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.bean.mainmodule.LifeService;

import java.util.ArrayList;
import java.util.List;

/**
 * 生活服务
 */
public class LifeServiceActivity extends BaseTitleActivity implements LifeServiceCB {
    private final String TAG = LifeServiceActivity.class.getSimpleName();

    private ActivityLifeServiceBinding binding;
    private LifeServiceVM vm;
    private LifeServiceAdapter adapter;
    private AdvertisingRotationViewPagerAdapter viewPagerAdapter;
    private List<ItemPostTagBinding> itemPostTagBindings = new ArrayList<>();

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new LifeServiceVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "宜兴紫砂";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityLifeServiceBinding) baseViewBinding;
        vm.getData(true);
        adapter = new LifeServiceAdapter(new ArrayList<>());
        binding.crv.setAdapter(adapter);
        //模拟广告数据
        List<Banner.CommomBanner> bannersBeans = new ArrayList<>();
        //初始化轮播广告
        viewPagerAdapter = new AdvertisingRotationViewPagerAdapter(bannersBeans,binding.rpv);
        binding.rpv.setAdapter(viewPagerAdapter);
        Drawable iconPanelOpen = getResources().getDrawable(R.drawable.icon_panel_open);
        iconPanelOpen.setBounds(0, 0, iconPanelOpen.getMinimumWidth(), iconPanelOpen.getMinimumHeight());
        baseTitleBinding.tvTitle.setCompoundDrawables(null,null,iconPanelOpen, null);
        setListener();
        vm.getLifeCate();
    }

    private void setCateView(List<HouseBean.CateBean> cateBeans) {
        int i = 0;
        if (cateBeans != null) {
            //初始化生活家具类型选择
            for (HouseBean.CateBean cateBean : cateBeans) {
                //设置类别
                final ItemPostTagBinding itemPostTagBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_post_tag, null, false);
                itemPostTagBinding.tvTag.setText(cateBean.getCate_name());
                itemPostTagBinding.tvTag.setMode(TagView.SINGLEMODE);
                itemPostTagBinding.tvTag.setTagId(cateBean.getCate_id());
                itemPostTagBinding.tvTag.setOnClickListener(v -> {
                    for (ItemPostTagBinding itemPostTag : itemPostTagBindings) {
                        itemPostTag.tvTag.setSelected(false);
                    }
                    vm.setCateId(itemPostTagBinding.tvTag.getTagId());
                    itemPostTagBinding.tvTag.setSelected(true);
                    closePanel();
                    baseTitleBinding.tvTitle.setText(itemPostTagBinding.tvTag.getText());
                    vm.getData(true);
                });
                if (vm.getCateId().equals(cateBean.getCate_id()))
                    itemPostTagBinding.tvTag.setSelected(true);
                itemPostTagBindings.add(itemPostTagBinding);
                binding.tagLayout.addView(itemPostTagBinding.getRoot());
                i++;
            }
        }
    }

    private void setListener() {
        baseTitleBinding.tvTitle.setOnClickListener(v -> {
            if (binding.svTag.getVisibility() == View.GONE) {
                binding.svTag.setVisibility(View.VISIBLE);
                binding.vBlackBackgroud.setVisibility(View.VISIBLE);
            } else {
                binding.svTag.setVisibility(View.GONE);
                binding.vBlackBackgroud.setVisibility(View.GONE);
            }
        });
        binding.vBlackBackgroud.setOnClickListener(v -> closePanel());
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getData(true);
            }

            @Override
            public void onLoadMore() {
                vm.getData(false);
            }
        });
    }

    private void closePanel() {
        binding.svTag.setVisibility(View.GONE);
        binding.vBlackBackgroud.setVisibility(View.GONE);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_life_service;
    }

    @Override
    public String getPage() {
        return adapter.getData().get(adapter.getData().size() - 1).getInfo_id();
    }

    @Override
    public void setData(List<LifeService> lifeServices, boolean isRefresh) {
        if (isRefresh) {
            binding.crv.setRefreshing(false);
        }
        adapter.notifyDataChangedAfterLoadMore(isRefresh, lifeServices);
    }

    @Override
    public void setBanner(Banner banner) {
        viewPagerAdapter.getAdvertisingRotations().clear();
        if (banner.getBanner_top() != null)
            viewPagerAdapter.getAdvertisingRotations().addAll(banner.getBanner_top());
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCategoryData(List<HouseBean.CateBean> categoryData) {
        setCateView(categoryData);
    }
}
