package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.MyCouponCardCB;
import com.zhihangjia.mainmodule.fragment.MyCouponCardFragmentFragment;
import com.zhihangjia.mainmodule.viewmodel.MyCouponCardVM;
import com.zhixingjia.bean.mainmodule.CouponCardTicketSum;
import com.zhihangjia.mainmodule.databinding.ActivityMyCouponCardBinding;
import java.util.ArrayList;
import java.util.List;

public class MyCouponCardActivity extends BaseTitleActivity implements MyCouponCardCB {
    private final String TAG = MyCouponCardActivity.class.getSimpleName();
    private MyCouponCardVM vm;
    private List<MyCouponCardFragmentFragment> fragments = new ArrayList<>();
    private ActivityMyCouponCardBinding binding;
    private FragmentPagerAdapter adapter;

    @Override
    public String setTitle() {
        return "我的卡券";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityMyCouponCardBinding) baseViewBinding;
        Bundle bundle = new Bundle();
        MyCouponCardFragmentFragment noUsedMyCouponCardFragmentFragment = new MyCouponCardFragmentFragment();
        fragments.add(noUsedMyCouponCardFragmentFragment);
        bundle.putInt(IntentConfig.TYPE, 0);
        noUsedMyCouponCardFragmentFragment.setArguments(bundle);
        MyCouponCardFragmentFragment usedMyCouponCardFragmentFragment = new MyCouponCardFragmentFragment();
        fragments.add(usedMyCouponCardFragmentFragment);
        bundle = new Bundle();
        bundle.putInt(IntentConfig.TYPE, 1);
        usedMyCouponCardFragmentFragment.setArguments(bundle);
        MyCouponCardFragmentFragment inValudateMyCouponCardFragmentFragment = new MyCouponCardFragmentFragment();
        fragments.add(inValudateMyCouponCardFragmentFragment);
        bundle = new Bundle();
        bundle.putInt(IntentConfig.TYPE, 2);
        inValudateMyCouponCardFragmentFragment.setArguments(bundle);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, fragments);

        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);
        binding.tl.getTabAt(0).setText("未使用");
        binding.tl.getTabAt(1).setText("已使用");
        binding.tl.getTabAt(2).setText("已失效");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_coupon_card;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MyCouponCardVM(this);
        return vm;
    }

    @Override
    public void setCouponTicketCount(CouponCardTicketSum sum) {
        binding.tl.getTabAt(0).setText("未使用"+"("+sum.getNotused_sum()+")");
        binding.tl.getTabAt(1).setText("已使用"+"("+sum.getUsed_sum()+")");
        binding.tl.getTabAt(2).setText("已失效"+"("+sum.getFailure_sum()+")");
    }
}
