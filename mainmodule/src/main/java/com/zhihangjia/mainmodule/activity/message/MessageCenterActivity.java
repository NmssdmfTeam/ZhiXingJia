package com.zhihangjia.mainmodule.activity.message;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;

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

        final ViewMessageTabBinding systemTabBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_message_tab, null, false);

        systemTabBinding.tvMessageName.setText("系统消息");
        binding.tl.getTabAt(0).setCustomView(systemTabBinding.getRoot());
        systemTabBinding.tvMessageName.setTextColor(Color.parseColor("#FFFF9A14"));


        final ViewMessageTabBinding orderTabBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_message_tab, null, false);
        orderTabBinding.tvMessageName.setText("订单消息");
        binding.tl.getTabAt(1).setCustomView(orderTabBinding.getRoot());
        orderTabBinding.tvMessageName.setTextColor(Color.parseColor("#666666"));

        binding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    systemTabBinding.tvMessageName.setTextColor(Color.parseColor("#FFFF9A14"));
                    orderTabBinding.tvMessageName.setTextColor(Color.parseColor("#666666"));
                } else {
                    orderTabBinding.tvMessageName.setTextColor(Color.parseColor("#FFFF9A14"));
                    systemTabBinding.tvMessageName.setTextColor(Color.parseColor("#666666"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_message_center;
    }
}
