package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityMessageCenterModuleBinding;
import com.zhihangjia.mainmodule.fragment.MessageCenterModuleFragment;
import com.zhihangjia.mainmodule.viewmodel.MessageCenterModuleVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by chenbin on 2018/11/19
 * <p>
 * <p>
 */
public class MessageCenterModuleActivity extends BaseTitleActivity {
    private final String TAG = MessageCenterModuleActivity.class.getSimpleName();
    private MessageCenterModuleVM vm;
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private ActivityMessageCenterModuleBinding binding;

    @Override
    public String setTitle() {
        return "杂七杂八";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityMessageCenterModuleBinding) baseViewBinding;
        //初始化热点新闻
        MessageCenterModuleFragment newReplyFragment = new MessageCenterModuleFragment();
        MessageCenterModuleFragment newPublishFragment = new MessageCenterModuleFragment();
        MessageCenterModuleFragment hotNewsFragment = new MessageCenterModuleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("types", "1");
        bundle.putString("cat_id","1");
        newPublishFragment.setArguments(bundle);
        bundle = new Bundle();
        bundle.putString("types", "2");
        newReplyFragment.setArguments(bundle);
        bundle = new Bundle();
        bundle.putString("types", "3");
        hotNewsFragment.setArguments(bundle);
        fragments.add(newReplyFragment);
        fragments.add(newPublishFragment);
        fragments.add(hotNewsFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, fragments);
        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);

        binding.tl.getTabAt(0).setText("最新回复");
        binding.tl.getTabAt(1).setText("最新发布");
        binding.tl.getTabAt(2).setText("精华热帖");
        baseTitleBinding.tTitle.inflateMenu(R.menu.messagecenter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_message_center_module;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MessageCenterModuleVM(this);
        return vm;
    }
}
