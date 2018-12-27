package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
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
        bundle.putString("cat_id",vm.cat_id);
        newPublishFragment.setArguments(bundle);
        bundle = new Bundle();
        bundle.putString("types", "2");
        bundle.putString("cat_id",vm.cat_id);
        newReplyFragment.setArguments(bundle);
        bundle = new Bundle();
        bundle.putString("types", "3");
        bundle.putString("cat_id",vm.cat_id);
        hotNewsFragment.setArguments(bundle);
        fragments.add(newPublishFragment);
        fragments.add(newReplyFragment);
        fragments.add(hotNewsFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, fragments);
        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);


        binding.tl.getTabAt(0).setText("最新发布");
        binding.tl.getTabAt(1).setText("最新回复");
        binding.tl.getTabAt(2).setText("精华热帖");
        baseTitleBinding.tTitle.inflateMenu(R.menu.messagecenter);
        baseTitleBinding.iSlenderLine.setVisibility(View.GONE);
        setTitle(vm.name);
        setListener();
    }

    private void setListener() {
        baseTitleBinding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.publishMessage) {
                    if (TextUtils.isEmpty(PreferenceUtil.getString(PrefrenceConfig.TOKEN,""))) {
                        EventInfo eventInfo = new EventInfo(-1);
                        RxBus.getInstance().send(RxEvent.LoginEvent.RE_LOGIN, eventInfo);
                        return false;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentConfig.CAT_ID, vm.cat_id);
                    bundle.putString(IntentConfig.NAME, vm.name);
                    doIntent(PostActivity.class, bundle);
                }
                return false;
            }
        });
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
