package com.zhixingjia.goodsmanagemodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.KeyBoardUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.callback.GoodManageCB;
import com.zhixingjia.goodsmanagemodule.databinding.ActivityGoodManageBinding;
import com.zhixingjia.goodsmanagemodule.fragment.GoodManageFragment;
import com.zhixingjia.goodsmanagemodule.viewmodel.GoodManageFragmentVM;
import com.zhixingjia.goodsmanagemodule.viewmodel.GoodManageVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 商品管理
* @author chenbin
* @date 2018/12/14 15:11
* @version v3.2.0
*/
public class GoodManageActivity extends BaseActivity implements GoodManageCB {
    private final String TAG = GoodManageActivity.class.getSimpleName();

    private List<Fragment> list = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private ActivityGoodManageBinding binding;
    private GoodManageVM vm;
    private GoodManageFragment sellFragment;
    private GoodManageFragment inventoryFragment;

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
        vm = new GoodManageVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityGoodManageBinding) baseBinding;
        binding.setVm(vm);
        sellFragment = new GoodManageFragment();
        inventoryFragment = new GoodManageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.TYPE, GoodManageFragmentVM.GOODMANAGE_ONOFFER);
        sellFragment.setArguments(bundle);

        //初始化menu
        binding.toolbar.inflateMenu(R.menu.good_manage_search);

        bundle = new Bundle();
        bundle.putInt(IntentConfig.TYPE, GoodManageFragmentVM.GOODMANAGE_OUTOFFER);
        inventoryFragment.setArguments(bundle);
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

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doIntent(AddOrEditProductActivity.class, null);
            }
        });
        vm.getCommodityNumber();
        setListener();
    }

    private void setListener() {
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.good_manage_search) {
                    binding.toolbar.getMenu().getItem(0).setVisible(false);
                    binding.stv.setVisibility(View.VISIBLE);
                    binding.tvTitle.setVisibility(View.GONE);
                }
                return false;
            }
        });

        binding.stv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                JLog.i(TAG, "setOnEditorActionListener action:" + actionId);
                // 如果是search action，或者为指定的action都执行搜索,有些手机actionSearch没有,所以使用ENTER键判断
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // 因为onEditorAction在键盘按下和按上时都会出发，所以需要过滤一个
                    if (event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
                        return true;
                    }
                    if (sellFragment != null) {
                        sellFragment.setKeyword(vm.keyword.get());
                    }
                    if (inventoryFragment != null) {
                        inventoryFragment.setKeyword(vm.keyword.get());
                    }
                    KeyBoardUtil.closeKeyWords(binding.stv);
                }
                return true;
            }
        });
    }

    @Override
    public void setNumber() {
        binding.tl.getTabAt(0).setText("出售中("+vm.sale_sum+")");
        binding.tl.getTabAt(1).setText("仓库中("+vm.depot_sum+")");
    }
}
