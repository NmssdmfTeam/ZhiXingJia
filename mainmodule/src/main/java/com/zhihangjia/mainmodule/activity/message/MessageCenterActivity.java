package com.zhihangjia.mainmodule.activity.message;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityMessageCenterBinding;
import com.zhihangjia.mainmodule.databinding.ViewMessageTabBinding;
import com.zhihangjia.mainmodule.fragment.message.OrderMessageFragment;
import com.zhihangjia.mainmodule.fragment.message.SystemMessageFragment;

import java.util.ArrayList;
import java.util.List;

public class MessageCenterActivity extends BaseTitleActivity {
    private final String TAG = MessageCenterActivity.class.getSimpleName();
    private ActivityMessageCenterBinding binding;

    private List<Fragment> list = new ArrayList<>();
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    public String setTitle() {
        return "消息";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityMessageCenterBinding) baseViewBinding;
        setISlenderLineGone();
        initTabLayout();
    }

    private void initTabLayout(){
        SystemMessageFragment systemMessageFragment = new SystemMessageFragment();
        OrderMessageFragment orderMessageFragment = new OrderMessageFragment();
        list.add(systemMessageFragment);
        list.add(orderMessageFragment);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, list);
        binding.vp.setAdapter(fragmentPagerAdapter);

        binding.tl.setupWithViewPager(binding.vp);

        ViewMessageTabBinding systemTabBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_message_tab, null, false);

        systemTabBinding.tvMessageName.setText("系统消息");
        binding.tl.getTabAt(0).setCustomView(systemTabBinding.getRoot());

        ViewMessageTabBinding orderTabBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_message_tab, null, false);
        orderTabBinding.tvMessageName.setText("订单消息");
        binding.tl.getTabAt(1).setCustomView(orderTabBinding.getRoot());
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_message_center;
    }
}
