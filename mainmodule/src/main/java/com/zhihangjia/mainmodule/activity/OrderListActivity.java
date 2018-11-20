package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ActivityOrderListBinding;
import com.zhihangjia.mainmodule.fragment.OrderListFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends BaseActivity {
    private final String TAG = OrderListActivity.class.getSimpleName();
    private ActivityOrderListBinding binding;

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_order_list;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityOrderListBinding) baseBinding;

        OrderListFragment allFragment = new OrderListFragment();
        OrderListFragment waitPayFragment = new OrderListFragment();
        OrderListFragment waitDeliverFragment = new OrderListFragment();
        OrderListFragment waitReceiveFragment = new OrderListFragment();
        OrderListFragment waitCommentFragment = new OrderListFragment();

        fragments.add(allFragment);
        fragments.add(waitPayFragment);
        fragments.add(waitDeliverFragment);
        fragments.add(waitReceiveFragment);
        fragments.add(waitCommentFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, fragments);
        binding.vp.setAdapter(adapter);

        binding.tl.setupWithViewPager(binding.vp);
    }
}
