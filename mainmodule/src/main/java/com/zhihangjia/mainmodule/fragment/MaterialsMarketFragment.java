package com.zhihangjia.mainmodule.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.AdvertisingRotationViewPagerAdapter;
import com.zhihangjia.mainmodule.adapter.MaterialsCategoryAdapter;
import com.zhihangjia.mainmodule.adapter.MaterialsMarketAdapter;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhihangjia.mainmodule.bean.MaterialsCategoryBean;
import com.zhihangjia.mainmodule.callback.MarketFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentMarketBinding;
import com.zhihangjia.mainmodule.databinding.ItemHotBrandBinding;
import com.zhihangjia.mainmodule.databinding.ItemMaterialsCrvheadBinding;
import com.zhihangjia.mainmodule.viewmodel.MarketFragmentVM;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.ArrayList;
import java.util.List;

import static android.databinding.DataBindingUtil.inflate;

/**
* @description 知行家首页-- 建材家居fragment
* @author chenbin
* @date 2018/11/13 15:53
* @version v3.2.0
*/
public class MaterialsMarketFragment extends BaseFragment implements MarketFragmentCB {
    private final String TAG = MaterialsMarketFragment.class.getSimpleName();
    private FragmentMarketBinding binding;
    private MarketFragmentVM vm;
    private MaterialsMarketAdapter adapter;
    private AdvertisingRotationViewPagerAdapter viewPagerAdapter;
    private MaterialsCategoryAdapter materialsCategoryAdapter;
    private int[] materialsIconRes = new int[]{R.drawable.air_conditioner,R.drawable.air_conditioner,
            R.drawable.air_conditioner,R.drawable.air_conditioner,R.drawable.air_conditioner,
            R.drawable.air_conditioner,R.drawable.air_conditioner,R.drawable.air_conditioner,
            R.drawable.air_conditioner,R.drawable.air_conditioner};
    private String[] materialsName = new String[]{"空调","冰箱","凳子","组合柜","空调","沙发","椅子","衣柜","餐桌","全部"};

    @Override
    public BaseVM initViewModel() {
        vm = new MarketFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_market;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMarketBinding) baseBinding;
        adapter = new MaterialsMarketAdapter(new ArrayList());
        binding.crv.setAdapter(adapter);
        //初始化首页头部
        ItemMaterialsCrvheadBinding itemMaterialsCrvheadBinding = inflate(getLayoutInflater(), R.layout.item_materials_crvhead, null, false);
        adapter.setHeaderView(itemMaterialsCrvheadBinding.getRoot());
        //初始化首页轮播图
        viewPagerAdapter = new AdvertisingRotationViewPagerAdapter(AdvertisingRotationViewPagerAdapter.MAIN_PAGER, new ArrayList<IndexBean.BannersBean>(), itemMaterialsCrvheadBinding.rpv);
        itemMaterialsCrvheadBinding.rpv.setAdapter(viewPagerAdapter);
        //模拟轮播图数据
        IndexBean.BannersBean bannersBean = new IndexBean.BannersBean();
        viewPagerAdapter.getAdvertisingRotations().add(bannersBean);
        bannersBean = new IndexBean.BannersBean();
        viewPagerAdapter.getAdvertisingRotations().add(bannersBean);
        bannersBean = new IndexBean.BannersBean();
        viewPagerAdapter.getAdvertisingRotations().add(bannersBean);
        viewPagerAdapter.notifyDataSetChanged();

        //建材家居分类
        List<MaterialsCategoryBean> list = new ArrayList<>();
        for (int i = 0; i < materialsIconRes.length;i++) {
            MaterialsCategoryBean materialsCategoryBean = new MaterialsCategoryBean();
            materialsCategoryBean.setResIconId(materialsIconRes[i]);
            materialsCategoryBean.setTitle(materialsName[i]);
            list.add(materialsCategoryBean);
        }
        itemMaterialsCrvheadBinding.crv.setLayoutManager(new GridLayoutManager(getContext(),5));
        materialsCategoryAdapter = new MaterialsCategoryAdapter(list);
        itemMaterialsCrvheadBinding.crv.setAdapter(materialsCategoryAdapter);

        //有品位模拟数据
        for (int i=0; i < 4; i++) {
            GlideImageView imageView = new GlideImageView(getContext());
            imageView.setRoundRadius(DensityUtil.dpToPx(getContext(),2));
            imageView.setRoundtype(GlideImageView.RoundAll);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dpToPx(getContext(),80),DensityUtil.dpToPx(getContext(),80));
            layoutParams.leftMargin = DensityUtil.dpToPx(getContext(),4);
            layoutParams.rightMargin = DensityUtil.dpToPx(getContext(),4);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(R.drawable.pic_kitchen);
            itemMaterialsCrvheadBinding.llHome.addView(imageView);
        }

        //模拟热门品牌数据
        for (int i=0; i < 4; i++) {
            ItemHotBrandBinding itemHotBrandBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_hot_brand,null,false);
            itemMaterialsCrvheadBinding.llBrand.addView(itemHotBrandBinding.getRoot());
        }

        //爆款推荐模块模拟数据
        MainBean mainBean = new MainBean();
        mainBean.setItemType(0);
        adapter.addData(mainBean);

        //推荐商家模块模拟数据
        mainBean = new MainBean();
        mainBean.setItemType(1);
        adapter.addData(mainBean);

        setListener();
    }

    private void setListener() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
