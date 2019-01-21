package com.zhihangjia.mainmodule.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.fragment.BaseTitleFragment;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.BbsTopCommentActivity;
import com.zhihangjia.mainmodule.activity.PostActivity;
import com.zhihangjia.mainmodule.adapter.MessageCategoryViewPagerAdapter;
import com.zhihangjia.mainmodule.bean.MessageCategory;
import com.zhihangjia.mainmodule.callback.IndexMessageCB;
import com.zhihangjia.mainmodule.databinding.FragmentMessageBinding;
import com.zhihangjia.mainmodule.databinding.ItemViewflipperBinding;
import com.zhihangjia.mainmodule.viewmodel.MessageVM;
import com.zhixingjia.bean.mainmodule.BbsCategory;
import com.zhixingjia.bean.mainmodule.BbsInfoList;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.ArrayList;
import java.util.List;

/**
* @description 知行家信息中心
* @author chenbin
* @date 2018/11/13 15:53
* @version v3.2.0
*/
public class MessageFragment extends BaseTitleFragment implements IndexMessageCB {
    private MessageVM vm;
    private String TAG = MessageFragment.class.getSimpleName();
    private FragmentMessageBinding fragmentMessageBinding;
    private MessageCategoryViewPagerAdapter viewPagerAdapter;
    private ViewFlipper viewFlipper;
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    @Override
    public String setTitle() {
        return "百姓信息";
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        baseTitleBinding.vStatusBar.setVisibility(View.VISIBLE);
        baseTitleBinding.tTitle.inflateMenu(R.menu.messagecenter);
        hideNavigation();
        fragmentMessageBinding = (FragmentMessageBinding) baseViewBinding;
        fragmentMessageBinding.setVm(vm);
        //初始化首页轮播图
        viewPagerAdapter = new MessageCategoryViewPagerAdapter(new ArrayList<MessageCategory>(), fragmentMessageBinding.rpv);
        fragmentMessageBinding.rpv.setAdapter(viewPagerAdapter);
        fragmentMessageBinding.rpv.pause();

        viewFlipper = fragmentMessageBinding.headlineViewflipper;
        //初始化热点新闻
        DailyHotNewsFragment dailyHotNewsFragment = new DailyHotNewsFragment();
        DailyHotNewsFragment newPublishFragment = new DailyHotNewsFragment();
        DailyHotNewsFragment lastReplyFragment = new DailyHotNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.TYPE, 0);
        dailyHotNewsFragment.setArguments(bundle);
        bundle = new Bundle();
        bundle.putInt(IntentConfig.TYPE, 1);
        newPublishFragment.setArguments(bundle);
        bundle = new Bundle();
        bundle.putInt(IntentConfig.TYPE, 2);
        lastReplyFragment.setArguments(bundle);
        fragments.add(dailyHotNewsFragment);
        fragments.add(newPublishFragment);
        fragments.add(lastReplyFragment);
        adapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), fragments);
        fragmentMessageBinding.vp.setAdapter(adapter);


        fragmentMessageBinding.tl.setupWithViewPager(fragmentMessageBinding.vp);

        fragmentMessageBinding.tl.getTabAt(0).setText("24小时热点");
        fragmentMessageBinding.tl.getTabAt(1).setText("最新发布");
        fragmentMessageBinding.tl.getTabAt(2).setText("最后回复");
        fragmentMessageBinding.vp.setOffscreenPageLimit(3);
        vm.getMessageCat();
        vm.getBbsSticks();
        setListener();
    }

    private void setListener() {
        fragmentMessageBinding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        baseTitleBinding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.publishMessage) {
                    if (TextUtils.isEmpty(PreferenceUtil.getString(PrefrenceConfig.TOKEN,""))) {
                        EventInfo eventInfo = new EventInfo(-1);
                        RxBus.getInstance().send(RxEvent.LoginEvent.RE_LOGIN, eventInfo);
                        return false;
                    }
                    doIntent(PostActivity.class,null);
                }
                return false;
            }
        });

        fragmentMessageBinding.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {
                    fragmentMessageBinding.swrl.setEnabled(true);
                } else {
                    fragmentMessageBinding.swrl.setEnabled(false);
                }
            }
        });
        fragmentMessageBinding.swrl.setOnRefreshListener(() -> {
            vm.getMessageCat();
            vm.getBbsSticks();
            int postion = fragmentMessageBinding.tl.getSelectedTabPosition();
            DailyHotNewsFragment dailyHotNewsFragment = (DailyHotNewsFragment) fragments.get(postion);
            dailyHotNewsFragment.freshData();
        });
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MessageVM(this);
        return vm;
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
                doIntent(BbsTopCommentActivity.class,null);
            }
        });
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void setMessageCategory(List<BbsCategory> bbsCategories) {
        fragmentMessageBinding.swrl.setRefreshing(false);
        List<MessageCategory> messageCategories = new ArrayList<>();
        if (bbsCategories.size() <= 8) {
            MessageCategory messageCategory = new MessageCategory();
            messageCategory.setCategories(bbsCategories);
            messageCategories.add(messageCategory);
            fragmentMessageBinding.rpv.setHintViewVisiblity(View.INVISIBLE);
        } else {
            for (int i=0; i < bbsCategories.size(); i+=8) {
                MessageCategory messageCategory = new MessageCategory();
                if (i+8 >= bbsCategories.size()) {
                    messageCategory.setCategories(bbsCategories.subList(i,bbsCategories.size()));
                } else {
                    messageCategory.setCategories(bbsCategories.subList(i,i+8));
                }
                messageCategories.add(messageCategory);
            }
            fragmentMessageBinding.rpv.setHintViewVisiblity(View.VISIBLE);
        }
        viewPagerAdapter.setMessageCategory(messageCategories);
        viewPagerAdapter.notifyDataSetChanged();
        fragmentMessageBinding.rpv.pause();
    }

    @Override
    public void setCommentPost(List<BbsInfoList> bbsCategories) {
        fragmentMessageBinding.swrl.setRefreshing(false);
        //初始化首页头条
        List<IndexBean.ArticleBean> articleBeans = new ArrayList<>();
        for (int i=0; i < bbsCategories.size(); i++) {
            IndexBean.ArticleBean articleBean = new IndexBean.ArticleBean();
            articleBean.setTitle(bbsCategories.get(i).getTitle());
            articleBeans.add(articleBean);
        }
        setHeadlineView(articleBeans);
    }
}
