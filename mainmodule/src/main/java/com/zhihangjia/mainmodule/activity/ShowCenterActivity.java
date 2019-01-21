package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityShowCenterBinding;
import com.zhihangjia.mainmodule.fragment.PromotionsActivityFragment;
import com.zhihangjia.mainmodule.viewmodel.ShowCenterVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 展示中心
* @author chenbin
* @date 2019/1/21 17:24
* @version v3.2.0
*/
public class ShowCenterActivity extends BaseTitleActivity {
    private final String TAG = ShowCenterActivity.class.getSimpleName();
    private ShowCenterVM vm;
    private ActivityShowCenterBinding binding;
    private FragmentPagerAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public String setTitle() {
        return "展示中心";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityShowCenterBinding) baseViewBinding;
        PromotionsActivityFragment promotionsActivityFragment = new PromotionsActivityFragment();
        fragments.add(promotionsActivityFragment);
        PromotionsActivityFragment promotionsActivityFragment2 = new PromotionsActivityFragment();
        fragments.add(promotionsActivityFragment2);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, fragments);

        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);
        binding.tl.getTabAt(0).setText("活动促销");
        binding.tl.getTabAt(1).setText("领券中心");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_show_center;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ShowCenterVM(this);
        return vm;
    }
}
