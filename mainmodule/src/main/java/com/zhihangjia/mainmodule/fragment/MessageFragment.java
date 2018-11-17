package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.fragment.BaseTitleFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.AdvertisingRotationViewPagerAdapter;
import com.zhihangjia.mainmodule.adapter.MessageCategoryViewPagerAdapter;
import com.zhihangjia.mainmodule.databinding.FragmentMessageBinding;
import com.zhihangjia.mainmodule.viewmodel.MessageVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 知行家信息中心
* @author chenbin
* @date 2018/11/13 15:53
* @version v3.2.0
*/
public class MessageFragment extends BaseTitleFragment {
    private MessageVM vm;
    private String TAG = MessageFragment.class.getSimpleName();
    private FragmentMessageBinding fragmentMessageBinding;
    private MessageCategoryViewPagerAdapter viewPagerAdapter;

    @Override
    public String setTitle() {
        return "信息中心";
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

        //初始化首页轮播图
        List<Base> list = new ArrayList<>();
        for (int i=0; i < 2; i++) {
            list.add(new Base());
        }
        viewPagerAdapter = new MessageCategoryViewPagerAdapter(AdvertisingRotationViewPagerAdapter.MAIN_PAGER, list, fragmentMessageBinding.rpv);
        fragmentMessageBinding.rpv.setAdapter(viewPagerAdapter);
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MessageVM(this);
        return vm;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
