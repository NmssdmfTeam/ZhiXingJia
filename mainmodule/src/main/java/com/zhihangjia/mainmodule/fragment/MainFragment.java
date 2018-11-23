package com.zhihangjia.mainmodule.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MainActivity;
import com.zhihangjia.mainmodule.activity.SearchActivity;
import com.zhihangjia.mainmodule.activity.YXHeadLineActivity;
import com.zhihangjia.mainmodule.activity.MessageCenterActivity;
import com.zhihangjia.mainmodule.adapter.AdvertisingRotationViewPagerAdapter;
import com.zhihangjia.mainmodule.adapter.MainAdapter;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhihangjia.mainmodule.callback.MainFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMainBinding;
import com.zhihangjia.mainmodule.databinding.ItemMainCrvheadBinding;
import com.zhihangjia.mainmodule.databinding.ItemViewflipperBinding;
import com.zhihangjia.mainmodule.viewmodel.MainFragmentVM;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 知行家首页fragment
 * @date 2018/11/13 15:53
 */
public class MainFragment extends BaseFragment implements MainFragmentCB {
    private final String TAG = MainFragment.class.getSimpleName();
    private FragmentMainBinding binding;
    ItemMainCrvheadBinding itemMainCrvheadBinding;
    private MainFragmentVM vm;
    private MainAdapter adapter;
    private AdvertisingRotationViewPagerAdapter viewPagerAdapter;
    private ViewFlipper viewFlipper;
    private int firstY = 0;

    @Override
    public BaseVM initViewModel() {
        vm = new MainFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMainBinding) baseBinding;
        adapter = new MainAdapter(new ArrayList());
        itemMainCrvheadBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_main_crvhead, null, false);
        itemMainCrvheadBinding.setVm(vm);
        //初始化首页头部
        adapter.setHeaderView(itemMainCrvheadBinding.getRoot());

        //初始化首页轮播图
        viewPagerAdapter = new AdvertisingRotationViewPagerAdapter(new ArrayList<IndexBean.BannersBean>(), itemMainCrvheadBinding.rpv);
        itemMainCrvheadBinding.rpv.setAdapter(viewPagerAdapter);
        binding.crv.setAdapter(adapter);
        binding.crv.setLoadMoreEnable(false);
        //初始化首页头条
        viewFlipper = itemMainCrvheadBinding.headlineViewflipper;

        binding.crv.setRefreshing(true);
        vm.getIndex(true);
        setListener();
    }

    public void setHeadlineView(List<IndexBean.ArticleBean> headlines) {
        viewFlipper.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < headlines.size(); i = i + 2) {
            LinearLayout linearLayout = new LinearLayout(viewFlipper.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(layoutParams);
            ItemViewflipperBinding itemViewflipperBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_viewflipper, null, false);
            itemViewflipperBinding.tvTitle.setText(headlines.get(i).getTitle());
            linearLayout.addView(itemViewflipperBinding.getRoot());
            View view = new View(getContext());
            LinearLayout.LayoutParams blankviewparams = new LinearLayout.LayoutParams(0, 0);
            blankviewparams.weight = 1;
            view.setLayoutParams(blankviewparams);
            linearLayout.addView(view);
            if (i + 1 < headlines.size()) {
                ItemViewflipperBinding itemViewflipperBindingSecond = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_viewflipper, null, false);
                itemViewflipperBindingSecond.tvTitle.setText(headlines.get(i + 1).getTitle());
                linearLayout.addView(itemViewflipperBindingSecond.getRoot());
            }
            viewFlipper.addView(linearLayout);
        }
        //进入动画
        viewFlipper.setInAnimation(viewFlipper.getContext(), R.anim.headline_in);
        //退出动画
        viewFlipper.setOutAnimation(viewFlipper.getContext(), R.anim.headline_out);
        //动画间隔
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(YXHeadLineActivity.class,null);
            }
        });
    }

    private void setListener() {
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getIndex(false);
            }

            @Override
            public void onLoadMore() {

            }
        });
        ImageView ivNotice = binding.getRoot().findViewById(R.id.ivNotice);
        ivNotice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                doIntent(MessageCenterActivity.class,null);
            }
        });

        binding.iIndexTitle.vSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(SearchActivity.class, null);
            }
        });
    }

    @Override
    public String getTAG() {
        return TAG;
    }


    @Override
    public void setRollPagerView(List<IndexBean.BannersBean> bannersBeans) {
        viewPagerAdapter.getAdvertisingRotations().clear();
        viewPagerAdapter.getAdvertisingRotations().addAll(bannersBeans);
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setFillerView(List<IndexBean.ArticleBean> articleBeans) {
        setHeadlineView(articleBeans);
    }

    @Override
    public void setBannerFixed(List<IndexBean.BannerFixedBean> bannerFixedBeans) {
        if (bannerFixedBeans != null) {
            for (IndexBean.BannerFixedBean bannerFixedBean : bannerFixedBeans) {
                if ("left".equals(bannerFixedBean.getModel_name())) {
                    GlideUtil.load(itemMainCrvheadBinding.ivAdvertisementFirst,bannerFixedBean.getImg_url());
                } else if ("right_up".equals(bannerFixedBean.getModel_name())) {
                    GlideUtil.load(itemMainCrvheadBinding.ivAdvertisementSecond,bannerFixedBean.getImg_url());
                } else if ("right_down".equals(bannerFixedBean.getModel_name())) {
                    GlideUtil.load(itemMainCrvheadBinding.ivAdvertisementThird,bannerFixedBean.getImg_url());
                }
            }
        }
    }

    @Override
    public void setExcellentSeller(MainBean sellerBeans) {
        adapter.getData().clear();
        if (sellerBeans != null) {
            adapter.addData(sellerBeans);
        }
    }

    @Override
    public void setCommodity(MainBean commodity) {
        if (commodity != null) {
            adapter.addData(commodity);
        }
        //宜兴生活服务模拟数据
        MainBean mainBean = new MainBean();
        mainBean.setItemType(2);
        adapter.addData(mainBean);
    }

    @Override
    public void setForum(MainBean forum) {
        if (forum != null) {
            adapter.addData(forum);
        }
    }

    @Override
    public void setRefreshend() {
        binding.crv.setRefreshing(false);
    }

    @Override
    public void toOtherFragment(int index) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setCurrentTabsIndex(index);
        }
    }
}
