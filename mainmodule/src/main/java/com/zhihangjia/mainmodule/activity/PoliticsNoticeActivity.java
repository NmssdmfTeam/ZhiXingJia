package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityPoliticsNoticeBinding;
import com.zhihangjia.mainmodule.fragment.FocusNewsFragment;
import com.zhihangjia.mainmodule.viewmodel.PoliticsNoticeVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 政务公告
* @author chenbin
* @date 2018/11/16 16:27
* @version v3.2.0
*/
public class PoliticsNoticeActivity extends BaseTitleActivity {
    private ActivityPoliticsNoticeBinding binding;
    private String TAG = PoliticsNoticeActivity.class.getSimpleName();
    private PoliticsNoticeVM vm;

    private FocusNewsFragment focusNewsFragment;
    private FocusNewsFragment basicDynamicFragment;
    private FocusNewsFragment noticeFragment;
    private List<Fragment> list = new ArrayList<>();

    private FragmentPagerAdapter adapter;

    @Override
    public String setTitle() {
        return "政务公告";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityPoliticsNoticeBinding) baseViewBinding;

        focusNewsFragment = new FocusNewsFragment();
        basicDynamicFragment = new FocusNewsFragment();
        noticeFragment = new FocusNewsFragment();
        list.add(focusNewsFragment);
        list.add(basicDynamicFragment);
        list.add(noticeFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, list);

        binding.vp.setAdapter(adapter);
        binding.vp.setOffscreenPageLimit(2);
        binding.tl.setupWithViewPager(binding.vp);

        binding.tl.getTabAt(0).setText("要闻动态");
        binding.tl.getTabAt(1).setText("基层动态");
        binding.tl.getTabAt(2).setText("公告公示");

        focusNewsFragment.setType(1);
        basicDynamicFragment.setType(2);
        noticeFragment.setType(3);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_politics_notice;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new PoliticsNoticeVM(this);
        return vm;
    }
}
