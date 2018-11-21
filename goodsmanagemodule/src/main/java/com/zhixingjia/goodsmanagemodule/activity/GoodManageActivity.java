package com.zhixingjia.goodsmanagemodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.databinding.ActivityGoodManageBinding;
import com.zhixingjia.goodsmanagemodule.fragment.GoodManageFragment;

import java.util.ArrayList;
import java.util.List;

public class GoodManageActivity extends BaseActivity {

    private final String TAG = GoodManageActivity.class.getSimpleName();

    private List<Fragment> list = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private ActivityGoodManageBinding binding;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_good_manage;
    }

    @Override
    public BaseVM initViewModel() {
        return null;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityGoodManageBinding) baseBinding;
        GoodManageFragment sellFragment = new GoodManageFragment();
        GoodManageFragment inventoryFragment = new GoodManageFragment();
        list.add(sellFragment);
        list.add(inventoryFragment);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, list);
        binding.vp.setAdapter(adapter);

        binding.tl.setupWithViewPager(binding.vp);

        binding.tl.getTabAt(0).setText("出售中(4)");
        binding.tl.getTabAt(1).setText("仓库中(5)");

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
