package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.view.TagView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.AdvertisingRotationViewPagerAdapter;
import com.zhihangjia.mainmodule.adapter.LifeServiceAdapter;
import com.zhihangjia.mainmodule.callback.LifeServiceCB;
import com.zhihangjia.mainmodule.databinding.ActivityLifeServiceBinding;
import com.zhihangjia.mainmodule.databinding.ItemPostTagBinding;
import com.zhihangjia.mainmodule.viewmodel.LifeServiceVM;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.ArrayList;
import java.util.List;

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
        vm.getData();
        adapter = new LifeServiceAdapter(vm.getList());
        binding.crv.setAdapter(adapter);
        //模拟广告数据
        List<IndexBean.BannersBean> bannersBeans = new ArrayList<>();
        bannersBeans.add(new IndexBean.BannersBean());
        bannersBeans.add(new IndexBean.BannersBean());
        bannersBeans.add(new IndexBean.BannersBean());
        //初始化轮播广告
        viewPagerAdapter = new AdvertisingRotationViewPagerAdapter(bannersBeans,binding.rpv);
        binding.rpv.setAdapter(viewPagerAdapter);
        Drawable iconPanelOpen = getResources().getDrawable(R.drawable.icon_panel_open);
        iconPanelOpen.setBounds(0, 0, iconPanelOpen.getMinimumWidth(), iconPanelOpen.getMinimumHeight());
        baseTitleBinding.tvTitle.setCompoundDrawables(null,null,iconPanelOpen, null);

        for (int i = 0; i < 6; i++) {
            //设置类别
            final ItemPostTagBinding itemPostTagBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_post_tag,null,false);
            itemPostTagBinding.tvTag.setText("宜兴紫砂"+i);
            itemPostTagBinding.tvTag.setMode(TagView.SINGLEMODE);
            itemPostTagBinding.tvTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (ItemPostTagBinding itemPostTag:itemPostTagBindings) {
                        itemPostTag.tvTag.setSelected(false);
                    }
                    itemPostTagBinding.tvTag.setSelected(true);
                    closePanel();
                    baseTitleBinding.tvTitle.setText(itemPostTagBinding.tvTag.getText());
                }
            });
            if (i == 0)
                itemPostTagBinding.tvTag.setSelected(true);
            itemPostTagBindings.add(itemPostTagBinding);
            binding.tagLayout.addView(itemPostTagBinding.getRoot());
            i++;
        }
        setListener();
    }

    private void setListener() {
        baseTitleBinding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.svTag.getVisibility() == View.GONE) {
                    binding.svTag.setVisibility(View.VISIBLE);
                    binding.vBlackBackgroud.setVisibility(View.VISIBLE);
                } else {
                    binding.svTag.setVisibility(View.GONE);
                    binding.vBlackBackgroud.setVisibility(View.GONE);
                }
            }
        });
        binding.vBlackBackgroud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePanel();
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
}
