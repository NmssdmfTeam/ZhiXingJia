package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.OrderListCB;
import com.zhihangjia.mainmodule.databinding.ActivityOrderListBinding;
import com.zhihangjia.mainmodule.fragment.OrderListFragment;
import com.zhihangjia.mainmodule.fragment.OrderListWaitForPayFragment;
import com.zhihangjia.mainmodule.viewmodel.OrderListVM;

import java.util.ArrayList;
import java.util.List;

/**
 * 采购商，订单列表
 */
public class OrderListPurchaserActivity extends BaseActivity implements OrderListCB {
    private final String TAG = OrderListPurchaserActivity.class.getSimpleName();
    private ActivityOrderListBinding binding;

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private OrderListVM vm;

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
        vm = new OrderListVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityOrderListBinding) baseBinding;

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        OrderListFragment allFragment = new OrderListFragment();
        OrderListWaitForPayFragment waitPayFragment = new OrderListWaitForPayFragment();
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

        binding.vp.setOffscreenPageLimit(4);

        binding.tl.setupWithViewPager(binding.vp);

        binding.tl.getTabAt(0).setText("全部");
        binding.tl.getTabAt(1).setText("待付款");
        binding.tl.getTabAt(2).setText("待发货");
        binding.tl.getTabAt(3).setText("待收货");
        binding.tl.getTabAt(4).setText("待评论");

        allFragment.setInfo("buyer", 0);
        waitPayFragment.setInfo();
        waitDeliverFragment.setInfo("buyer", 2);
        waitReceiveFragment.setInfo("buyer", 3);
        waitCommentFragment.setInfo("buyer", 4);

        vm.getData();

        binding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //
            }

            @Override
            public void onPageSelected(int position) {
                setCurrent(position);
                Fragment fragment = fragments.get(position);
                if (fragment instanceof OrderListWaitForPayFragment) {
                    OrderListWaitForPayFragment forPayFragment = (OrderListWaitForPayFragment) fragment;
                    if (forPayFragment.isResumed()) {
                        forPayFragment.initData();
                    }
                }
                if (fragment instanceof  OrderListFragment) {
                    OrderListFragment orderListFragment = (OrderListFragment) fragment;
                    if (orderListFragment.isResumed()) {
                        orderListFragment.initData();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //
            }
        });
    }

    @Override
    public void setCurrentTab(int position) {
        //需要在设置adapter之后才有效
        binding.vp.setCurrentItem(position);
        setCurrent(position);
    }

    public void setCurrent(int position) {
        for (int i = 0; i < fragments.size(); i++) {
            if (i == 1) {
                ((OrderListWaitForPayFragment) fragments.get(i)).getVm().setCurrent(i == position);
            } else {
                ((OrderListFragment) fragments.get(i)).getVm().setCurrent(i == position);
            }
        }

    }
}
