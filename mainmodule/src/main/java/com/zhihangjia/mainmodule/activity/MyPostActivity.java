package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityMyPostBinding;
import com.zhihangjia.mainmodule.databinding.ActivityPostBinding;
import com.zhihangjia.mainmodule.fragment.MerchantAllFragment;
import com.zhihangjia.mainmodule.fragment.MerchantEvaluateFragment;
import com.zhihangjia.mainmodule.fragment.MerchantMainFragment;
import com.zhihangjia.mainmodule.fragment.MyPostFragment;
import com.zhihangjia.mainmodule.fragment.MyPostReplyFragment;
import com.zhihangjia.mainmodule.viewmodel.MyPostVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 我的发帖
* @author chenbin
* @date 2018/11/20 17:52
* @version v3.2.0
*/
public class MyPostActivity extends BaseTitleActivity {
    private final String TAG = MyPostActivity.class.getSimpleName();
    private MyPostVM vm;
    private MyPostFragment myPostFragment;
    private MyPostReplyFragment myPostReplyFragment;
    private List<Fragment> list = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private ActivityMyPostBinding binding;

    @Override
    public String setTitle() {
        return "我的帖子";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        initTabsLayout();
    }

    private void initTabsLayout() {
        binding = (ActivityMyPostBinding) baseViewBinding;
        myPostFragment = new MyPostFragment();
        myPostReplyFragment = new MyPostReplyFragment();
        list.add(myPostFragment);
        list.add(myPostReplyFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, list);

        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);

        binding.tl.getTabAt(0).setText("发帖");
        binding.tl.getTabAt(1).setText("回帖");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_post;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MyPostVM(this);
        return vm;
    }
}
