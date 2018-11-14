package com.zhihangjia.mainmodule.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.AdvertisingRotationViewPagerAdapter;
import com.zhihangjia.mainmodule.adapter.MainAdapter;
import com.zhihangjia.mainmodule.bean.Headline;
import com.zhihangjia.mainmodule.bean.IndexAdvertise;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhihangjia.mainmodule.databinding.FragmentMainBinding;
import com.zhihangjia.mainmodule.databinding.ItemMainCrvheadBinding;
import com.zhihangjia.mainmodule.databinding.ItemViewflipperBinding;
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
    private ViewFlipper viewFlipper;

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
        //初始化首页头部
        adapter.setHeaderView(itemMainCrvheadBinding.getRoot());

        //初始化首页轮播图
        List<IndexAdvertise> indexAdvertises = new ArrayList<>();
        indexAdvertises.add(new IndexAdvertise());
        indexAdvertises.add(new IndexAdvertise());
        indexAdvertises.add(new IndexAdvertise());
        viewPagerAdapter = new AdvertisingRotationViewPagerAdapter(AdvertisingRotationViewPagerAdapter.MAIN_PAGER, indexAdvertises, itemMainCrvheadBinding.rpv);
        itemMainCrvheadBinding.rpv.setAdapter(viewPagerAdapter);
        binding.crv.setAdapter(adapter);

        //初始化首页头条
        viewFlipper = itemMainCrvheadBinding.headlineViewflipper;
        List headlines = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Headline headline = new Headline();
            headline.setTitle("测试"+i);
            headlines.add(headline);
        }
        setHeadlineView(headlines);

        //模拟推荐广告数据
        MainBean mainBean = new MainBean();
        mainBean.setItemType(1);
        adapter.addData(mainBean);

        //宜兴生活服务模拟数据
        mainBean = new MainBean();
        mainBean.setItemType(2);
        adapter.addData(mainBean);

        //消息中心模拟数据
        mainBean = new MainBean();
        mainBean.setItemType(3);
        adapter.addData(mainBean);
    }

    public void setHeadlineView(List<Headline> headlines) {
        viewFlipper.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < headlines.size(); i = i + 2) {
            LinearLayout linearLayout = new LinearLayout(viewFlipper.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(layoutParams);
            ItemViewflipperBinding itemViewflipperBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_viewflipper,null,false);
            itemViewflipperBinding.tvTitle.setText(headlines.get(i).getTitle());
            linearLayout.addView(itemViewflipperBinding.getRoot());
            View view = new View(getContext());
            LinearLayout.LayoutParams blankviewparams = new LinearLayout.LayoutParams(0, 0);
            blankviewparams.weight = 1;
            view.setLayoutParams(blankviewparams);
            linearLayout.addView(view);
            if (i + 1 < headlines.size()) {
                ItemViewflipperBinding itemViewflipperBindingSecond = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_viewflipper,null,false);
                itemViewflipperBindingSecond.tvTitle.setText(headlines.get(i+1).getTitle());
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
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                intent.setClass(activity, WebViewActivity.class);
//                bundle.putString(IntentConfig.URL, Config.getHtmlPrefix() + UrlConfig.HEADLINE_LIST);
//                intent.putExtras(bundle);
//                startActivity(intent);
            }
        });
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
