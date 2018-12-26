package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.MessageCenterCB;
import com.zhihangjia.mainmodule.databinding.ActivityMessageCenterBinding;
import com.zhihangjia.mainmodule.databinding.ViewMessageTabBinding;
import com.zhihangjia.mainmodule.fragment.message.OrderMessageFragment;
import com.zhihangjia.mainmodule.fragment.message.SystemMessageFragment;
import com.zhihangjia.mainmodule.viewmodel.MessageCenterVM;

import java.util.ArrayList;
import java.util.List;

public class MessageCenterActivity extends BaseTitleActivity implements MessageCenterCB {
    private final String TAG = MessageCenterActivity.class.getSimpleName();
    private ActivityMessageCenterBinding binding;
    private MessageCenterVM vm;

    private List<Fragment> list = new ArrayList<>();
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewMessageTabBinding systemTabBinding;
    private ViewMessageTabBinding orderTabBinding;
    private boolean isFirstLoad = true;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MessageCenterVM(this);
        return vm;
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
        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentConfig.ENABLE_LOAD, true);
        systemMessageFragment.setArguments(bundle);
        list.add(systemMessageFragment);
        list.add(orderMessageFragment);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, list);
        binding.vp.setAdapter(fragmentPagerAdapter);

        binding.tl.setupWithViewPager(binding.vp);

        systemTabBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_message_tab, null, false);

        systemTabBinding.tvMessageName.setText("系统消息");
        binding.tl.getTabAt(0).setCustomView(systemTabBinding.getRoot());
        systemTabBinding.tvMessageName.setTextColor(Color.parseColor("#FFFF9A14"));


        orderTabBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_message_tab, null, false);
        orderTabBinding.vRed.setVisibility(View.VISIBLE);
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
                    if (isFirstLoad) {
                        ((OrderMessageFragment)list.get(1)).loadData();
                        isFirstLoad = false;
                    }
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

    @Override
    public void showNotice() {
        if (TextUtils.isEmpty(vm.messageUnread.getSys_message()) || "0".equals(vm.messageUnread.getSys_message())) {
            systemTabBinding.vRed.setVisibility(View.GONE);
        } else {
            systemTabBinding.vRed.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(vm.messageUnread.getOrder_message()) || "0".equals(vm.messageUnread.getOrder_message())) {
            orderTabBinding.vRed.setVisibility(View.GONE);
        } else {
            orderTabBinding.vRed.setVisibility(View.VISIBLE);
        }
    }
}
